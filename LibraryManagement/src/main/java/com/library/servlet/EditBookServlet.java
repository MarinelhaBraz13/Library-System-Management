package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.dao.DBConnection;
import com.library.model.Book;
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

@WebServlet("/edit-book")
public class EditBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // ==========================================
    // OPEN EDIT PAGE
    // ==========================================

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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


        // Only ADMIN can edit
        if (!"ADMIN".equals(user.getRole())) {

            response.sendRedirect("books");
            return;
        }


        int id;

        try {

            id = Integer.parseInt(
                    request.getParameter("id")
            );

        } catch (Exception e) {

            response.sendRedirect("books");
            return;
        }


        // Find book
        BookDAO bookDAO =
                new BookDAO();

        Book book =
                bookDAO.getBookById(id);


        if (book == null) {

            response.sendRedirect("books");
            return;
        }


        request.setAttribute(
                "book",
                book
        );


        request.getRequestDispatcher(
                "edit-book.jsp"
        ).forward(
                request,
                response
        );
    }


    // ==========================================
    // UPDATE BOOK
    // ==========================================

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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


        // Only ADMIN can edit
        if (!"ADMIN".equals(user.getRole())) {

            response.sendRedirect("books");
            return;
        }


        int id;
        int quantity;


        try {

            id = Integer.parseInt(
                    request.getParameter("id")
            );

            quantity = Integer.parseInt(
                    request.getParameter("quantity")
            );

        } catch (Exception e) {

            response.getWriter().println(
                "Invalid book information."
            );

            return;
        }


        String title =
                request.getParameter("title");

        String author =
                request.getParameter("author");

        String category =
                request.getParameter("category");


        // Quantity cannot be negative
        if (quantity < 0) {

            response.getWriter().println(
                "Quantity cannot be negative."
            );

            return;
        }


        Connection conn = null;


        try {

            conn =
                    DBConnection.getConnection();

            conn.setAutoCommit(false);


            // ==========================================
            // Get current book information
            // ==========================================

            String findSql =
                    "SELECT quantity, available " +
                    "FROM books " +
                    "WHERE id=?";

            PreparedStatement findPs =
                    conn.prepareStatement(findSql);

            findPs.setInt(1, id);

            var rs =
                    findPs.executeQuery();


            if (!rs.next()) {

                conn.rollback();

                response.sendRedirect("books");
                return;
            }


            int oldQuantity =
                    rs.getInt("quantity");

            int oldAvailable =
                    rs.getInt("available");


            // Calculate how many books are currently borrowed
            int borrowed =
                    oldQuantity - oldAvailable;


            // Calculate new available quantity
            int newAvailable =
                    quantity - borrowed;


            // New quantity cannot be smaller
            // than the number of borrowed books
            if (newAvailable < 0) {

                conn.rollback();

                response.getWriter().println(
                    "Quantity cannot be less than " +
                    "the number of borrowed books."
                );

                return;
            }


            // ==========================================
            // Update book
            // ==========================================

            String updateSql =
                    "UPDATE books SET " +
                    "title=?, " +
                    "author=?, " +
                    "category=?, " +
                    "quantity=?, " +
                    "available=? " +
                    "WHERE id=?";


            PreparedStatement updatePs =
                    conn.prepareStatement(updateSql);


            updatePs.setString(1, title);

            updatePs.setString(2, author);

            updatePs.setString(3, category);

            updatePs.setInt(4, quantity);

            updatePs.setInt(5, newAvailable);

            updatePs.setInt(6, id);


            updatePs.executeUpdate();


            // Save changes
            conn.commit();


            // Back to Books page with success message
            response.sendRedirect(
                    "books?success=edited"
            );


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
                "Error updating book: "
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