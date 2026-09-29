package com.library.servlet;
import com.library.dao.BorrowRecordDAO;
import com.library.model.BorrowRecord;
import com.library.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@WebServlet("/borrow-records")
public class BorrowRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        // Check login
        HttpSession session =
                request.getSession(false);
        if (session == null ||
            session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        // Get logged-in user
        User user =
                (User) session.getAttribute("user");
        BorrowRecordDAO dao =
                new BorrowRecordDAO();
        List<BorrowRecord> records;
        // Get selected date filter
        String filter =
                request.getParameter("filter");
        // ==========================================
        // ADMIN
        // ==========================================

        if ("ADMIN".equals(user.getRole())) {

            if ("7".equals(filter)) {

                records =
                        dao.getBorrowRecordsByDays(7);

            } else if ("30".equals(filter)) {

                records =
                        dao.getBorrowRecordsByDays(30);

            } else if ("90".equals(filter)) {

                records =
                        dao.getBorrowRecordsByDays(90);

            } else {

                records =
                        dao.getAllBorrowRecords();
            }
    // ==========================================
        // STUDENT
        // ==========================================
        } else {
            records =
                    dao.getBorrowRecordsByUserId(
                            user.getId()
                    );
        }
        // Send records to JSP
        request.setAttribute(
                "records",
                records
        );
        // Send selected filter to JSP
        request.setAttribute(
                "selectedFilter",
                filter
        );
        // Open borrow records page
        request.getRequestDispatcher(
                "borrow-records.jsp"
        ).forward(
                request,
                response
        );
    }
}
