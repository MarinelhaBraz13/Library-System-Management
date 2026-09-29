<%@ page import="java.util.List" %>
<%@ page import="com.library.model.BorrowRecord" %>
<%@ page import="com.library.model.User" %>

<%
    List<BorrowRecord> records =
            (List<BorrowRecord>) request.getAttribute("records");

    User user =
            (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    boolean isAdmin =
            "ADMIN".equals(user.getRole());

    String selectedFilter =
            (String) request.getAttribute("selectedFilter");

    if (selectedFilter == null) {
        selectedFilter = "";
    }

    String success =
            request.getParameter("success");
%>

<!DOCTYPE html>
<html>

<head>

    <title>Borrow Records - Library Management</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">


    <!-- ========================================== -->
    <!-- NAVBAR -->
    <!-- ========================================== -->

    <nav class="navbar navbar-dark bg-primary">

        <div class="container">

            <a class="navbar-brand"
               href="dashboard.jsp">

                Library Management System

            </a>

            <a href="logout"
               class="btn btn-light">

                Logout

            </a>

        </div>

    </nav>


    <!-- ========================================== -->
    <!-- MAIN CONTENT -->
    <!-- ========================================== -->

    <div class="container mt-5">

        <div class="card shadow">

            <div class="card-body">


                <!-- ========================================== -->
                <!-- SUCCESS MESSAGE -->
                <!-- ========================================== -->

                <% if ("returned".equals(success)) { %>

                    <div
                        class="alert alert-success alert-dismissible fade show"
                        role="alert">

                        <strong>Book returned successfully!</strong>

                        <button
                            type="button"
                            class="btn-close"
                            data-bs-dismiss="alert">
                        </button>

                    </div>

                <% } %>


                <!-- ========================================== -->
                <!-- PAGE HEADER -->
                <!-- ========================================== -->

                <div class="d-flex justify-content-between align-items-center mb-4">

                    <div>

                        <% if (isAdmin) { %>

                            <h2>
                                All Borrow Records
                            </h2>

                            <p class="text-muted">
                                View all students' borrowing records.
                            </p>

                        <% } else { %>

                            <h2>
                                 My Borrow Records
                            </h2>

                            <p class="text-muted">
                                View your borrowed and returned books.
                            </p>

                        <% } %>

                    </div>


                    <div>

                        <a href="books"
                           class="btn btn-primary">

                             View Books

                        </a>


                        <a href="dashboard.jsp"
                           class="btn btn-secondary">

                            Dashboard

                        </a>

                    </div>

                </div>


                <!-- ========================================== -->
                <!-- ADMIN DATE FILTER -->
                <!-- ========================================== -->

                <% if (isAdmin) { %>

                    <form action="borrow-records"
                          method="get"
                          class="row g-2 mb-4">

                        <div class="col-md-4">

                            <label class="form-label">
                                Filter by Date
                            </label>

                            <select
                                name="filter"
                                class="form-select">

                                <option
                                    value=""
                                    <%= "".equals(selectedFilter)
                                        ? "selected"
                                        : "" %>>

                                    All Dates

                                </option>


                                <option
                                    value="7"
                                    <%= "7".equals(selectedFilter)
                                        ? "selected"
                                        : "" %>>

                                    Last 7 Days

                                </option>


                                <option
                                    value="30"
                                    <%= "30".equals(selectedFilter)
                                        ? "selected"
                                        : "" %>>

                                    Last 30 Days

                                </option>


                                <option
                                    value="90"
                                    <%= "90".equals(selectedFilter)
                                        ? "selected"
                                        : "" %>>

                                    Last 90 Days

                                </option>

                            </select>

                        </div>


                        <div class="col-md-2 d-flex align-items-end">

                            <button
                                type="submit"
                                class="btn btn-primary">

                                Filter

                            </button>

                        </div>

                    </form>

                <% } %>


                <!-- ========================================== -->
                <!-- BORROW RECORDS TABLE -->
                <!-- ========================================== -->

                <div class="table-responsive">

                    <table
                        class="table table-bordered table-striped">

                        <thead class="table-primary">

                            <tr>

                                <th>ID</th>

                                <% if (isAdmin) { %>

                                    <th>Username</th>

                                <% } %>

                                <th>Book Title</th>

                                <th>Borrow Date</th>

                                <th>Return Date</th>

                                <th>Status</th>

                                <th>Action</th>

                            </tr>

                        </thead>


                        <tbody>

                        <%

                            if (records != null && !records.isEmpty()) {

                                for (BorrowRecord record : records) {

                        %>

                            <tr>

                                <td>
                                    <%= record.getId() %>
                                </td>


                                <% if (isAdmin) { %>

                                    <td>
                                        <%= record.getUsername() %>
                                    </td>

                                <% } %>


                                <td>
                                    <%= record.getBookTitle() %>
                                </td>


                                <td>
                                    <%= record.getBorrowDate() %>
                                </td>


                                <td>

                                    <%= record.getReturnDate() == null
                                        ? "-"
                                        : record.getReturnDate() %>

                                </td>


                                <td>

                                    <% if ("BORROWED".equals(record.getStatus())) { %>

                                        <span class="badge bg-warning text-dark">

                                            BORROWED

                                        </span>

                                    <% } else { %>

                                        <span class="badge bg-success">RETURNED</span>

                                    <% } %>

                                </td>
                                <td>

                                    <% if ("BORROWED".equals(record.getStatus())) { %>

                                        <a href="return-book?id=<%= record.getId() %>"
                                            class="btn btn-warning btn-sm"
                                            onclick="return confirm('Do you want to return this book?');"> Return</a>

                                    <% } else { %>

                                        <button class="btn btn-secondary btn-sm"
                                            disabled>Returned</button>
                                    <% } %>
                                </td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td
                                    colspan="<%= isAdmin ? 7 : 6 %>"
                                    class="text-center">
                                    No borrow records found.
                                </td>
                            </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- ========================================== -->
    <!-- BOOTSTRAP JAVASCRIPT -->
    <!-- ========================================== -->

    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
    </script>

</body>

</html>