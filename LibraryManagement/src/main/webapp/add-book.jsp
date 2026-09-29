<%@ page import="com.library.model.User" %>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    if (!"ADMIN".equals(user.getRole())) {

        response.sendRedirect("books");
        return;
    }
%>
<!DOCTYPE html>
<html>

<head>

    <title>Add Book - Library Management</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">

    <nav class="navbar navbar-dark bg-primary">

        <div class="container">

            <a class="navbar-brand" href="dashboard.jsp">
                Library Management System
            </a>

            <a href="logout" class="btn btn-light">
                Logout
            </a>

        </div>

    </nav>


    <div class="container mt-5">

        <div class="row justify-content-center">

            <div class="col-md-6">

                <div class="card shadow">

                    <div class="card-body">

                        <h2 class="mb-4">
                            + Add New Book
                        </h2>


                        <form action="add-book" method="post">

                            <!-- Book Title -->

                            <div class="mb-3">

                                <label class="form-label">
                                    Book Title
                                </label>

                                <input
                                    type="text"
                                    name="title"
                                    class="form-control"
                                    placeholder="Enter book title"
                                    required>

                            </div>


                            <!-- Author -->

                            <div class="mb-3">

                                <label class="form-label">
                                    Author
                                </label>

                                <input
                                    type="text"
                                    name="author"
                                    class="form-control"
                                    placeholder="Enter author name"
                                    required>

                            </div>


                            <!-- Category -->

                            <div class="mb-3">

                                <label class="form-label">
                                    Category
                                </label>

                                <input
                                    type="text"
                                    name="category"
                                    class="form-control"
                                    placeholder="Example: Programming"
                                    required>

                            </div>


                            <!-- Quantity -->

                            <div class="mb-3">

                                <label class="form-label">
                                    Quantity
                                </label>

                                <input
                                    type="number"
                                    name="quantity"
                                    class="form-control"
                                    min="1"
                                    placeholder="Enter quantity"
                                    required>

                            </div>


                            <div class="d-flex gap-2">

                                <button
                                    type="submit"
                                    class="btn btn-success">

                                    Add Book

                                </button>


                                <a
                                    href="books"
                                    class="btn btn-secondary">

                                    Cancel

                                </a>

                            </div>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>

</body>

</html>