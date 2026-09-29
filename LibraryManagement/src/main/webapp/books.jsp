<%@ page import="java.util.List" %>
<%@ page import="com.library.model.Book" %>
<%@ page import="com.library.model.User" %>
<%
    List<Book> books =
            (List<Book>) request.getAttribute("books");
    User user =
            (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    boolean isAdmin =
            "ADMIN".equals(user.getRole());
    String success =
            request.getParameter("success");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Books - Library Management</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="dashboard.jsp">Library Management System</a>
				<div class="d-flex align-items-center gap-2">
					<span class="text-white"> <%= user.getFullname() %> </span>
                <a href="logout" class="btn btn-light"> Logout </a>
                </div>
                </div>
    </nav>
    <div class="container mt-5">
        <div class="card shadow">
            <div class="card-body">
                <!-- ========================================== -->
                <!-- SUCCESS MESSAGES -->
                <!-- ========================================== -->
                <% if ("added".equals(success)) { %>
                    <div
                        class="alert alert-success alert-dismissible fade show"
                        role="alert">
                        <strong>Book added successfully!</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert">
                        </button>
                    </div>
                <% } else if ("edited".equals(success)) { %>
                    <div class="alert alert-success alert-dismissible fade show"
                        role="alert"> <strong>Book updated successfully!</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert">
                        </button>
                    </div>
                <% } else if ("deleted".equals(success)) { %>
                    <div class="alert alert-success alert-dismissible fade show"  role="alert">
                         <strong>Book deleted successfully!</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert">
                        </button>
                    </div>
                <% } else if ("borrowed".equals(success)) { %>
                    <div class="alert alert-success alert-dismissible fade show" role="alert"> <strong>Book borrowed successfully!</strong>

                        <button type="button" class="btn-close" data-bs-dismiss="alert"> </button>
                    </div>
                <% } %>
                <!-- ========================================== -->
                <!-- PAGE HEADER -->
                <!-- ========================================== -->

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>
                         Library Books
                    </h2>
                    <div>
                        <% if (isAdmin) { %>
                            <a href="add-book.jsp" class="btn btn-success"> + Add Book </a>
                        <% } %> 
                        <a href="borrow-records" class="btn btn-info">  Borrow Records </a>
                        <a href="dashboard.jsp" class="btn btn-secondary"> Dashboard </a>
					</div>
                </div>

                <!-- ========================================== -->
                <!-- SEARCH -->
                <!-- ========================================== -->

                <form action="books" method="get" class="mb-4">
                    <div class="input-group">
                    <input type="text" name="search" class="form-control" placeholder="Search by ID, title, author, or category" value="<%= request.getParameter("search") != null ? request.getParameter("search"): "" %>">

                        <button type="submit" class="btn btn-primary"> Search  </button>
                        <a href="books" class="btn btn-secondary"> Clear </a>
					 </div> 
					 </form>
                <!-- ========================================== -->
                <!-- BOOK TABLE -->
                <!-- ========================================== -->

                <div class="table-responsive">
                    <table  class="table table-bordered table-striped">
                        <thead class="table-primary">
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Category</th>
                                <th>Quantity</th>
                                <th>Available</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            if (books != null && !books.isEmpty()) {
                                for (Book book : books) {
                        %>
                            <tr>
                                <td>
                                    <%= book.getId() %>
                                </td>
                                <td>
                                    <%= book.getTitle() %>
                                </td>
                                <td>
                                    <%= book.getAuthor() %>
                                </td>
                                <td>
                                    <%= book.getCategory() %>
                                </td>
                                <td>
                                    <%= book.getQuantity() %>
                                </td>
                                <td>
                                    <%= book.getAvailable() %>
                                </td>
                                <td>
                                    <!-- ADMIN ACTIONS -->
                                    <% if (isAdmin) { %>
                                        <a href="edit-book?id=<%= book.getId() %>" class="btn btn-warning btn-sm"> Edit </a>
                                        <a href="delete-book?id=<%= book.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this book?');"> Delete</a>
                                    <% } %>
                                    <!-- BORROW ACTION -->
                                    <% if (book.getAvailable() > 0) { %>
                                        <a href="borrow-book?id=<%= book.getId() %>" class="btn btn-success btn-sm" onclick="return confirm('Do you want to borrow this book?');"> Borrow </a>
                                    <% } else { %>
                                        <button class="btn btn-secondary btn-sm" disabled> Not Available </button>
                                    <% } %>
                                </td>
                            </tr>
                        <%
                                }

                            } else {
                        %>
                            <tr>
                                <td colspan="7" class="text-center"> No books found.</td>
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
    <!-- Bootstrap JavaScript -->
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
    </script>
</body>
</html>