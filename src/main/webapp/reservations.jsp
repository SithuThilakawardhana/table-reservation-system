<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Add Reservation</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="allComponents/style.css">

    </head>
    <body>

        <%@include file="allComponents/navBar.jsp" %>

        <div class="container-custom">
            <h3 class="mt-4">Add Reservation</h3>
            <form action="reservations" method="post" class="mt-3">
                <input type="hidden" name="action" value="add"/>

                <div class="form-group mb-3">
                    <label for="resId">Reservation ID:</label>
                    <input type="number" class="form-control" name="resId" id="resId" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="title">Title:</label>
                    <select class="form-control" name="title" id="title" required>
                        <option value="Mr">Mr.</option>
                        <option value="Mrs">Mrs.</option>
                        <option value="Miss">Miss.</option>
                        <option value="Dr">Dr.</option>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="firstName">First Name:</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="lastName">Last Name:</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="phone">Phone:</label>
                    <input type="text" class="form-control" name="phone" id="phone" pattern="^\+?[0-9]{10,15}$" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="date">Date:</label>
                    <input type="date" class="form-control" name="date" id="date" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="time">Time:</label>
                    <input type="time" class="form-control" name="time" id="time" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="pax">Pax:</label>
                    <input type="number" class="form-control" name="pax" id="pax" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="remarks">Remarks:</label>
                    <textarea class="form-control" name="remarks" id="remarks"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Add Reservation</button>
                <a href="reservations?action=viewAdminPanel" class="btn btn-danger mt-2">Cancel</a>
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>

        <script>
            document.addEventListener('DOMContentLoaded', (event) => {
                const today = new Date().toISOString().split('T')[0];  // Get current date in YYYY-MM-DD format
                document.getElementById('date').value = today;  // Set the value of the date input field
            });
        </script>


    </body>
</html>
