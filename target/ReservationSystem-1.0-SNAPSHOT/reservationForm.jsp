<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Edit Reservation</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="allComponents/style.css">
    </head>
    <body>

        <%@include file="allComponents/navBar.jsp" %>

        <div class="container-custom">
            <h3 class="mt-4 text-center">EDIT RESERVATION</h3>
            <form action="reservations" method="post" class="mt-3">
                <input type="hidden" name="id" value="${reservation.id}"/>
                <input type="hidden" name="action" value="update"/>

                <div class="form-group mb-3">
                    <label for="resId">Reservation ID:</label>
                    <input type="number" class="form-control" name="resId" id="resId" value="${reservation.resId}" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="title">Title:</label>
                    <select class="form-control" name="title" id="title" required>
                        <option value="Mr" <c:if test="${reservation.title == 'Mr'}">selected</c:if>>Mr</option>
                        <option value="Mrs" <c:if test="${reservation.title == 'Mrs'}">selected</c:if>>Mrs</option>
                        <option value="Miss" <c:if test="${reservation.title == 'Miss'}">selected</c:if>>Miss</option>
                        <option value="Dr" <c:if test="${reservation.title == 'Dr'}">selected</c:if>>Dr</option>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label for="firstName">First Name:</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" value="${reservation.firstName}" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="lastName">Last Name:</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" value="${reservation.lastName}" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="phone">Phone:</label>
                    <input type="text" class="form-control" name="phone" id="phone" value="${reservation.phone}" pattern="^\+?[0-9]{10,15}$" title="Phone number must include country code (e.g., +94764471007)" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="date">Date:</label>
                    <!-- Use JSTL fmt tag to format date for input -->
                    <input type="date" class="form-control" name="date" id="date" value="<fmt:formatDate value='${reservation.date}' pattern='yyyy-MM-dd'/>" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="time">Time:</label>
                    <!-- Use JSTL fmt tag to format time for input -->
                    <input type="time" class="form-control" name="time" id="time" value="<fmt:formatDate value='${reservation.time}' pattern='HH:mm'/>" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="pax">Pax:</label>
                    <input type="number" class="form-control" name="pax" id="pax" value="${reservation.pax}" required/>
                </div>

                <div class="form-group mb-3">
                    <label for="remarks">Remarks:</label>
                    <textarea class="form-control" name="remarks" id="remarks">${reservation.remarks}</textarea>
                </div>

                <button type="submit" class="btn btn-primary">Update</button>
                <a href="reservations?action=viewAdminPanel" class="btn btn-danger mt-2">Cancel</a>
            </form>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>


