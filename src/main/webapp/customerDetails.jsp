<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@page import="com.example.model.Reservation" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Customer Details</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" href="allComponents/style.css">
    </head>
    <body>
        
        <%@include file="allComponents/navBar.jsp" %>

        <h3 class="text-center mt-4">CUSTOMER DETAILS</h1>

        <div class="container-custom">
            <%--<form action="reservations" method="get" class="mb-3" style="width: 500px">
                <input type="hidden" name="action" value="viewCustomerDetails">
                <div class="input-group">
                    <input type="text" class="form-control" id="keyword" name="keyword" placeholder="Search by name or phone">
                    <button class="btn btn-primary" type="submit">Search</button>
                </div>
            </form>

            <form action="reservations" method="get" class="mb-3">
                <input type="hidden" name="action" value="generateCustomerDetailsPdf">
                <button type="submit" class="btn btn-success">Generate Customer Details Report</button>
            </form>--%>

            <div class="table-responsive">
                <table id="customerTable" class="table table-bordered">
                    <thead class="table-light">
                        <tr>
                            <th>Title</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reservation" items="${reservations}">
                            <tr>
                                <td>${reservation.title}</td>
                                <td>${reservation.firstName}</td>
                                <td>${reservation.lastName}</td>
                                <td>${reservation.phone}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.print.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
        <script src="allComponents/script.js"></script>
    </body>
</html>


