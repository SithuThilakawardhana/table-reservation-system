<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Reservation" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Reservation Details</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <script src="https://kit.fontawesome.com/36a000fd02.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="allComponents/style.css">
        <script>
            // Hide success and error messages after 2 seconds
            function hideMessage() {
                const successBox = document.getElementById('successBox');
                const errorBox = document.getElementById('errorBox');
                if (successBox) {
                    setTimeout(() => {
                        successBox.style.display = 'none';
                    }, 2000);
                }
                if (errorBox) {
                    setTimeout(() => {
                        errorBox.style.display = 'none';
                    }, 2000);
                }
            }
        </script>
    </head>
    <body onload="hideMessage()">

        <!-- Include navigation bar -->
        <%@include file="allComponents/navBar.jsp" %>

        <h3 class="text-center mt-4">Reservation Details</h3>

        <div class="container-custom mt-3">
            <% String successMessage = (String) session.getAttribute("successMessage"); %>
            <% String errorMessage = (String) session.getAttribute("errorMessage"); %>

            <!-- Success or Error Message Display -->
            <% if (successMessage != null) { %>
            <div id="successBox" class="alert alert-success" role="alert">
                <%= successMessage %>
            </div>
            <% session.removeAttribute("successMessage"); %>
            <% } %>
            <% if (errorMessage != null) { %>
            <div id="errorBox" class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
            <% session.removeAttribute("errorMessage"); %>
            <% } %>

            <!-- Date Range Search Form -->
            <% String action = request.getParameter("action"); %>
            <% if (action != null && action.equals("viewAllReservations")) { %>
            <form action="reservations" method="get" class="mb-3">
                <div class="text-center">
                    <button class="btn btn-primary btn-md" type="submit">Search by Date Range</button>
                </div>
                <input type="hidden" name="action" value="searchByDateRange">
                <div class="d-flex justify-content-center">
                    <div class="form-group mb-1 me-1">
                        <input type="date" class="form-control" name="startDate" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date(Calendar.getInstance().getTimeInMillis())) %>" required>
                    </div>
                    <div class="form-group mb-1">
                        <input type="date" class="form-control" name="endDate" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date(Calendar.getInstance().getTimeInMillis())) %>" required>
                    </div>
                </div>
                
            </form>
            <% } %>

            <!-- Date Navigation -->
            <% if (action == null || !action.equals("viewAllReservations")) { %>
            <form action="reservations" method="get" class="mb-3">
                <input type="hidden" name="action" value="viewAdminPanel">
                <input type="hidden" name="date" value="<%= request.getAttribute("date") != null ? request.getAttribute("date") : new SimpleDateFormat("yyyy-MM-dd").format(new Date(Calendar.getInstance().getTimeInMillis())) %>">
                <div class="d-flex justify-content-center align-items-center gap-3">
                    <button class="btn btn-primary" type="submit" name="navigation" value="previous">
                        <i class="fas fa-arrow-left"></i>
                    </button>
                    <span class="mx-3" style="font-size: 1.2em;">
                        <%= request.getAttribute("date") != null ? request.getAttribute("date") : new SimpleDateFormat("yyyy-MM-dd").format(new Date(Calendar.getInstance().getTimeInMillis())) %>
                    </span>
                    <button class="btn btn-primary" type="submit" name="navigation" value="next">
                        <i class="fas fa-arrow-right"></i>
                    </button>
                </div>
            </form>
            <% } %>

            <!-- Reservations Table -->
            <div class="table-responsive">
                <table id="reservationsTable" class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>#</th>
                            <th>Res ID</th>
                            <th>Title</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Pax</th>
                            <th>Remarks</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reservation" items="${reservations}">
                            <tr class="${reservation.isActive ? 'table-success' : 'table-danger'}">
                                <td>${reservation.id}</td>
                                <td>${reservation.resId}</td>
                                <td>${reservation.title}</td>
                                <td>${reservation.firstName}</td>
                                <td>${reservation.lastName}</td>
                                <td>${reservation.phone}</td>
                                <td>${reservation.date}</td>
                                <td>${reservation.time}</td>
                                <td>${reservation.pax}</td>
                                <td>${reservation.remarks}</td>
                                <td>
                                    <div class="d-flex justify-content-around">
                                        <a class="btn btn-primary btn-sm" href="reservations?action=edit&id=${reservation.id}" title="Edit">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                        </a>
                                        <a class="btn btn-warning btn-sm" href="reservations?action=cancel&id=${reservation.id}" title="Cancel">
                                            <i class="fa-regular fa-rectangle-xmark"></i>
                                        </a>
                                        <a class="btn btn-danger btn-sm" href="reservations?action=delete&id=${reservation.id}" title="Delete">
                                            <i class="fa-solid fa-trash"></i>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- DataTables Scripts -->
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.print.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
        <script src="allComponents/script.js"></script>
    </body>
</html>

