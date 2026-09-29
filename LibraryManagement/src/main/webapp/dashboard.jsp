<%@ page import="com.library.model.User" %>

<%
    User user =
            (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <title>Library Dashboard</title>

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

            <span class="navbar-brand">
                Library Management System
            </span>


            <div class="d-flex align-items-center gap-2">


                <!-- NOTIFICATION BUTTON -->

                <button
                    type="button"
                    class="btn btn-light"
                    title="Notifications"
                    onclick="showNotification()"> Notification</button>


                <!-- MESSAGE BUTTON -->

                <button
                    type="button"
                    class="btn btn-light"
                    title="Messages"
                    onclick="showMessage()">
Message</button>


                <!-- USER NAME -->

                <span class="text-white ms-2">

                     <%= user.getFullname() %>

                </span>


                <!-- LOGOUT -->

                <a href="logout"
                   class="btn btn-light ms-2">

                    Logout

                </a>

            </div>

        </div>

    </nav>


    <!-- ========================================== -->
    <!-- MAIN CONTENT -->
    <!-- ========================================== -->

    <div class="container mt-5">


        <!-- ========================================== -->
        <!-- PROFILE CARD -->
        <!-- ========================================== -->

        <div class="card shadow mb-4">

            <div class="card-body">

                <h3>

                    Welcome,
                    <%= user.getFullname() %>!

                </h3>


                <hr>


                <div class="row">


                    <!-- FULL NAME -->

                    <div class="col-md-4">

                        <p class="mb-1 text-muted">
                            Full Name
                        </p>

                        <h5>

                            <%= user.getFullname() %>

                        </h5>

                    </div>


                    <!-- USERNAME -->

                    <div class="col-md-4">

                        <p class="mb-1 text-muted">
                            Username
                        </p>

                        <h5>

                             <%= user.getUsername() %>

                        </h5>

                    </div>


                    <!-- ROLE -->

                    <div class="col-md-4">

                        <p class="mb-1 text-muted">
                            Role
                        </p>

                        <h5>

                            <% if ("ADMIN".equals(user.getRole())) { %>

                                <span class="badge bg-danger">
                                    ADMIN
                                </span>

                            <% } else { %>

                                <span class="badge bg-success">
                                    STUDENT
                                </span>

                            <% } %>

                        </h5>

                    </div>

                </div>

            </div>

        </div>


        <!-- ========================================== -->
        <!-- LIBRARY DASHBOARD -->
        <!-- ========================================== -->

        <div class="card shadow">

            <div class="card-body">

                <h4>
                    Library Dashboard
                </h4>


                <p class="text-muted">

                    Select an option below to manage the library.

                </p>


                <div class="row mt-4">


                    <!-- BOOKS -->

                    <div class="col-md-3 mb-3">

                        <div class="card text-center h-100">

                            <div class="card-body">
                                <h5>
                                    Books
                                </h5>

                                <p>
                                    View all library books.
                                </p>

                                <a
                                    href="books"
                                    class="btn btn-primary">

                                    View Books

                                </a>

                            </div>

                        </div>

                    </div>


                    <!-- BORROW -->

                    <div class="col-md-3 mb-3">

                        <div class="card text-center h-100">

                            <div class="card-body">

                                <h5>
                                    Borrow
                                </h5>

                                <p>
                                    Borrow an available book.
                                </p>

                                <a
                                    href="books"
                                    class="btn btn-success">

                                    Borrow Book

                                </a>

                            </div>

                        </div>

                    </div>


                    <!-- RETURN -->

                    <div class="col-md-3 mb-3">

                        <div class="card text-center h-100">

                            <div class="card-body">

                                <h5>
                                    Return
                                </h5>

                                <p>
                                    Return a borrowed book.
                                </p>

                                <a
                                    href="borrow-records"
                                    class="btn btn-warning">

                                    Return Book

                                </a>

                            </div>

                        </div>

                    </div>


                    <!-- RECORDS -->

                    <div class="col-md-3 mb-3">

                        <div class="card text-center h-100">

                            <div class="card-body">
                            <h5>
                                    Records
                                </h5>

                                <p>
                                    View borrowing history.
                                </p>

                                <a
                                    href="borrow-records"
                                    class="btn btn-info">

                                    Borrow Records

                                </a>

                            </div>

                        </div>

                    </div>


                </div>

            </div>

        </div>

    </div>


    <!-- ========================================== -->
    <!-- JAVASCRIPT -->
    <!-- ========================================== -->

    <script>

        function showNotification() {

            alert(
                "Notification\n\n" +
                "Welcome to the Library Management System!"
            );

        }


        function showMessage() {

            alert(
                "Message\n\n" +
                "You are logged in successfully."
            );

        }

    </script>


</body>

</html>