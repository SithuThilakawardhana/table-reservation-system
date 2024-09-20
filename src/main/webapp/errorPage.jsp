<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="alert alert-danger text-center">
                    <h2 class="mb-4">An Error Occurred</h2>
                    <p>
                        <strong>Error Message:</strong><br>
                        <%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "An unknown error occurred." %>
                    </p>
                    <a href="reservations?action=viewAdminPanel" class="btn btn-primary mt-3">Go Back to Admin Panel</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

