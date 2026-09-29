package com.library.servlet;

import com.library.dao.UserDAO;
import com.library.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        User user = userDAO.login(username, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());

            response.sendRedirect("dashboard.jsp");

        } else {

            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}