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
@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        // ==========================================
        // Get current session
        // ==========================================
        HttpSession session =
                request.getSession(false);
        // ==========================================
        // Check if user is logged in
        // ==========================================
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
        // ==========================================
        // Only ADMIN can add books
        // ==========================================
        if (!"ADMIN".equals(user.getRole())) {
            response.sendRedirect("books");
            return;
        }
        // ==========================================
        // Get form data
        // ==========================================
        String title =
                request.getParameter("title");
        String author =
                request.getParameter("author");
        String category =
                request.getParameter("category");
        int quantity =
                Integer.parseInt(
                    request.getParameter("quantity")
                );
        // ==========================================
        // SQL
        // ==========================================
        String sql =
                "INSERT INTO books " +
                "(title, author, category, quantity, available) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (
            Connection conn =
                    DBConnection.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {
            // ==========================================
            // Set values
            // ==========================================
            ps.setString(1,title);
            ps.setString(2,author);
            ps.setString(3,category);
            ps.setInt(4,quantity);
            ps.setInt(5,quantity);
            
            // ==========================================
            // Insert book
            // ==========================================

            ps.executeUpdate();
            
            // ==========================================
            // Back to books page with success message
            // ==========================================

            response.sendRedirect(
                    "books?success=added"
            );
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "Error adding book: "
                + e.getMessage()
            );
        }
    }
}