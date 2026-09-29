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

@WebServlet("/return-book")
public class ReturnServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get current session
        HttpSession session =
                request.getSession(false);

        // Check login
        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user =
                (User) session.getAttribute("user");

        int borrowId;

        try {

            borrowId = Integer.parseInt(
                    request.getParameter("id")
            );

        } catch (Exception e) {

            response.sendRedirect("borrow-records");
            return;
        }

        Connection conn = null;

        try {

            conn = DBConnection.getConnection();

            conn.setAutoCommit(false);


            // Find borrow record
            String findSql =
                    "SELECT book_id, user_id, status " +
                    "FROM borrow_records " +
                    "WHERE id=?";

            PreparedStatement findPs =
                    conn.prepareStatement(findSql);

            findPs.setInt(1, borrowId);

            ResultSet rs =
                    findPs.executeQuery();


            if (rs.next()) {

                int bookId =
                        rs.getInt("book_id");

                int recordUserId =
                        rs.getInt("user_id");

                String status =
                        rs.getString("status");


                // ==========================================
                // Check permission
                // ==========================================

                // Student can only return their own book.
                // Admin can return any book.

                if (!"ADMIN".equals(user.getRole()) &&
                    user.getId() != recordUserId) {

                    conn.rollback();
                    conn.commit();

                    response.sendRedirect(
                            "borrow-records?success=returned"
                   );

                    return;
                }


                // Check if already returned

                if ("BORROWED".equals(status)) {


                    // Update borrow record

                    String returnSql =
                            "UPDATE borrow_records " +
                            "SET return_date=CURDATE(), " +
                            "status='RETURNED' " +
                            "WHERE id=?";

                    PreparedStatement returnPs =
                            conn.prepareStatement(returnSql);

                    returnPs.setInt(1, borrowId);

                    returnPs.executeUpdate();


                    // Increase available quantity

                    String updateSql =
                            "UPDATE books " +
                            "SET available = available + 1 " +
                            "WHERE id=?";

                    PreparedStatement updatePs =
                            conn.prepareStatement(updateSql);

                    updatePs.setInt(1, bookId);

                    updatePs.executeUpdate();


                    // Save changes

                    conn.commit();


                    // Go back to borrow records

                    response.sendRedirect(
                            "borrow-records"
                    );


                } else {

                    conn.rollback();

                    request.setAttribute(
                    	    "message",
                    	    "This book has already been returned."
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
                	    "The borrow record could not be found."
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

            request.setAttribute(
            	    "message",
            	    "An error occurred while returning the book."
            	);

            	request.getRequestDispatcher(
            	    "error.jsp"
            	).forward(
            	    request,
            	    response
            	);

            	return;


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