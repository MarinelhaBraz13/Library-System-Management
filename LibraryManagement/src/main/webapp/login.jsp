<!DOCTYPE html>
<html>
<head>

    <title>Library Login</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

</head>

<body class="bg-light">

    <div class="container">

        <div class="row justify-content-center mt-5">

            <div class="col-md-5">

                <div class="card shadow">

                    <div class="card-body p-4">

                        <h2 class="text-center mb-4">
                            Library Management System
                        </h2>

                        <h5 class="text-center mb-4">
                            Login
                        </h5>

                        <% 
                            String error = request.getParameter("error");

                            if ("invalid".equals(error)) {
                        %>

                            <div class="alert alert-danger">
                                Invalid username or password!
                            </div>

                        <%
                            }
                        %>

                        <form action="login" method="post">

                            <div class="mb-3">

                                <label class="form-label">
                                    Username
                                </label>

                                <input
                                    type="text"
                                    name="username"
                                    class="form-control"
                                    placeholder="Enter username"
                                    required>

                            </div>

                            <div class="mb-3">

                                <label class="form-label">
                                    Password
                                </label>

                                <input
                                    type="password"
                                    name="password"
                                    class="form-control"
                                    placeholder="Enter password"
                                    required>

                            </div>

                            <button
                                type="submit"
                                class="btn btn-primary w-100">

                                Login

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>

</body>
</html>