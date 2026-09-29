package com.library.servlet;
import com.library.dao.BookDAO;
import com.library.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO();
        // Get search text from the search box
        String search = request.getParameter("search");
        List<Book> books;
        // If search is empty, show all books
        if (search == null || search.trim().isEmpty()) {
            books = bookDAO.getAllBooks();
        } else {
            // If user searches, search by title or author
            books = bookDAO.searchBooks(search);
        }
        request.setAttribute("books", books);
        request.getRequestDispatcher("books.jsp")
               .forward(request, response);
    }
}