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

@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // CHECK LOGIN
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }


        // ==========================================
        // GET LOGGED-IN USER
        // ==========================================

        User user =
                (User) session.getAttribute("user");


        // ==========================================
        // ONLY ADMIN CAN DELETE
        // ==========================================

        if (!"ADMIN".equals(user.getRole())) {

            response.sendRedirect("books");
            return;
        }


        // ==========================================
        // GET BOOK ID
        // ==========================================

        int id;

        try {

            id = Integer.parseInt(
                    request.getParameter("id")
            );

        } catch (Exception e) {

            request.setAttribute(
                "message",
                "Invalid book ID."
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
        // DATABASE CONNECTION
        // ==========================================

        try (
            Connection conn =
                    DBConnection.getConnection()
        ) {


            // ==========================================
            // CHECK BORROW RECORDS
            // ==========================================

            String checkSql =
                    "SELECT COUNT(*) " +
                    "FROM borrow_records " +
                    "WHERE book_id=?";

            PreparedStatement checkPs =
                    conn.prepareStatement(checkSql);

            checkPs.setInt(1, id);

            ResultSet rs =
                    checkPs.executeQuery();


            if (rs.next()) {

                int count =
                        rs.getInt(1);

                if (count > 0) {

                    request.setAttribute(
                        "message",
                        "This book cannot be deleted because it has borrowing records."
                    );

                    request.getRequestDispatcher(
                        "error.jsp"
                    ).forward(
                        request,
                        response
                    );

                    return;
                }
            }


            // ==========================================
            // DELETE BOOK
            // ==========================================

            String deleteSql =
                    "DELETE FROM books " +
                    "WHERE id=?";

            PreparedStatement deletePs =
                    conn.prepareStatement(deleteSql);

            deletePs.setInt(1, id);

            deletePs.executeUpdate();


            // ==========================================
            // SUCCESS
            // ==========================================

            response.sendRedirect(
                    "books?success=deleted"
            );


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "message",
                "Error deleting book: "
                + e.getMessage()
            );

            request.getRequestDispatcher(
                "error.jsp"
            ).forward(
                request,
                response
            );
        }
    }
}