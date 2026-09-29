package com.library.servlet;
import com.library.dao.DBConnection;
import com.library.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/borrow-book")
public class BorrowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // Check login
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }
        // ==========================================
        // Get logged-in user
        // ==========================================

        User user =
                (User) session.getAttribute("user");

        int userId =
                user.getId();

        // ==========================================
        // Get book ID
        // ==========================================

        int bookId;

        try {

            bookId = Integer.parseInt(
                    request.getParameter("id")
            );

        } catch (Exception e) {

            response.sendRedirect("books");
            return;
        }

        Connection conn = null;
        try {
            conn =
                    DBConnection.getConnection();

            conn.setAutoCommit(false);

            // ==========================================
            // Check if user already borrowed this book
            // ==========================================

            String duplicateSql =
                    "SELECT id " +
                    "FROM borrow_records " +
                    "WHERE user_id=? " +
                    "AND book_id=? " +
                    "AND status='BORROWED'";
            PreparedStatement duplicatePs =
                    conn.prepareStatement(duplicateSql);

            duplicatePs.setInt(1, userId);

            duplicatePs.setInt(2, bookId);

            ResultSet duplicateRs = duplicatePs.executeQuery();
            if (duplicateRs.next()) {
                conn.rollback();

                request.setAttribute(
                	    "message",
                	    "You have already borrowed this book. Please return it before borrowing it again."
                	);
                	request.getRequestDispatcher(
                	    "error.jsp"
                	).forward(
                	    request,
                	    response
                	);
                return;
            }

            // ==========================================
            // Check book availability
            // ==========================================

            String checkSql =
                    "SELECT available " +
                    "FROM books " +
                    "WHERE id=?";
            PreparedStatement checkPs =
                    conn.prepareStatement(checkSql);
            checkPs.setInt(1, bookId);
            ResultSet rs =
                    checkPs.executeQuery();
            if (rs.next()) { int available =
                        rs.getInt("available");
                if (available > 0) {
                    // ==========================================
                    // Create borrow record
                    // ==========================================

                    String borrowSql =
                            "INSERT INTO borrow_records " +
                            "(user_id, book_id, borrow_date, status) " +
                            "VALUES (?, ?, CURDATE(), 'BORROWED')";
                    PreparedStatement borrowPs =
                            conn.prepareStatement(borrowSql);
                    borrowPs.setInt(1, userId);
                    borrowPs.setInt(2, bookId);
                    borrowPs.executeUpdate();

                    // ==========================================
                    // Decrease available books
                    // ==========================================

                    String updateSql =
                            "UPDATE books " +
                            "SET available = available - 1 " +
                            "WHERE id=?";
                    PreparedStatement updatePs =
                            conn.prepareStatement(updateSql);
                    updatePs.setInt(1, bookId);
                    updatePs.executeUpdate();
                    // ==========================================
                    // Save transaction
                    // ==========================================
                    conn.commit();
                    response.sendRedirect("books?success=borrowed");
                } else {
                    conn.rollback();
                    request.setAttribute(
                    	    "message",
                    	    "This book is currently not available."
                    	);
                    	request.getRequestDispatcher(
                    	    "error.jsp"
                    	).forward(
                    	    request,
                    	    response
                    	);
                    	return;
                }
            } else {
                conn.rollback();
                request.setAttribute(
                	    "message",
                	    "The selected book could not be found."
                	);
                	request.getRequestDispatcher(
                	    "error.jsp"
                	).forward(
                	    request,
                	    response
                	);
                	return;
            }
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackError) {
                rollbackError.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().println(
                "Error borrowing book: "
                + e.getMessage()
            );
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception closeError) {
                closeError.printStackTrace();
            }
        }
    }
}
