<%@ page import="com.library.model.User" %>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String message =
            (String) request.getAttribute("message");

    if (message == null) {
        message = "Something went wrong.";
    }
%>

<!DOCTYPE html>
<html>

<head>

    <title>Library Management - Error</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">

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


    <div class="container mt-5">

        <div class="row justify-content-center">

            <div class="col-md-6">

                <div class="card shadow">

                    <div class="card-body text-center">

                        <h2 class="text-danger">
                             Error
                        </h2>

                        <p class="mt-4">
                            <%= message %>
                        </p>


                        <div class="mt-4">

                            <a href="books"
                               class="btn btn-primary">

                                Back to Books

                            </a>

                            <a href="borrow-records"
                               class="btn btn-secondary">

                                Borrow Records

                            </a>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</body>

</html>
