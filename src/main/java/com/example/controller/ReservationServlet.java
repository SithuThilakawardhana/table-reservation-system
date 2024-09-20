package com.example.controller;

import com.example.dao.ReservationDAO;
import com.example.model.Reservation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/reservations")
public class ReservationServlet extends HttpServlet {

    private ReservationDAO reservationDAO;

    @Override
    public void init() {
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String keyword = request.getParameter("keyword");
        String dateStr = request.getParameter("date");
        String navigation = request.getParameter("navigation");
        HttpSession session = request.getSession();

        try {
            if (action == null) {
                action = "viewAllReservations"; // Default action if none specified
            }

            switch (action) {
                case "edit":
                    handleEditAction(request, response, session);
                    break;
                case "delete":
                    handleDeleteAction(request, response, session);
                    break;
                case "cancel":
                    handleCancelAction(request, response, session);
                    break;
                case "viewAdminPanel":
                    handleViewAdminPanelAction(request, response, keyword, dateStr, navigation);
                    break;
                case "viewAllReservations":
                    handleViewAllReservationsAction(request, response, keyword);
                    break;
                case "searchByDateRange":
                    handleSearchByDateRangeAction(request, response);
                    break;
                case "generatePdf":
                    generatePdfReport(reservationDAO.getAllReservations(), response, null, null);
                    break;
                case "generatePdfByDateRange":
                    handleGeneratePdfByDateRangeAction(request, response);
                    break;
                case "viewCustomerDetails":
                    handleViewCustomerDetailsAction(request, response, keyword);
                    break;
                case "generateCustomerDetailsPdf":
                    generateCustomerDetailsPdfReport(reservationDAO.getCustomerDetails(), response);
                    break;
                default:
                    handleViewAllReservationsAction(request, response, keyword);
                    break;
            }

        } catch (SQLException | NumberFormatException e) {
            session.setAttribute("errorMessage", "Error: " + e.getMessage());
            response.sendRedirect("ErrorPage.jsp");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("ErrorPage.jsp");
        }
    }

    private void handleEditAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationDAO.getReservationById(id);
        if (reservation != null) {
            request.setAttribute("reservation", reservation);
            request.getRequestDispatcher("/reservationForm.jsp").forward(request, response);
        } else {
            session.setAttribute("errorMessage", "Reservation not found.");
            response.sendRedirect("ErrorPage.jsp");
        }
    }

    private void handleDeleteAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        reservationDAO.deleteReservation(id);
        session.setAttribute("successMessage", "Reservation deleted successfully");
        response.sendRedirect("reservations?action=viewAdminPanel");
    }

    private void handleCancelAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        reservationDAO.cancelReservation(id);
        session.setAttribute("successMessage", "Reservation canceled successfully");
        response.sendRedirect("reservations?action=viewAdminPanel");
    }

    private void handleViewAdminPanelAction(HttpServletRequest request, HttpServletResponse response, String keyword, String dateStr, String navigation) throws ServletException, IOException, SQLException {
        Date date = dateStr != null ? Date.valueOf(dateStr) : new Date(System.currentTimeMillis());
        if ("next".equals(navigation)) {
            date = new Date(date.getTime() + (1000 * 60 * 60 * 24));
        } else if ("previous".equals(navigation)) {
            date = new Date(date.getTime() - (1000 * 60 * 60 * 24));
        }
        List<Reservation> reservations = (keyword != null && !keyword.trim().isEmpty())
                ? reservationDAO.searchReservationsByDateAndKeyword(date, keyword)
                : reservationDAO.getReservationsByDate(date);
        request.setAttribute("date", date.toString());
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/adminPanel.jsp").forward(request, response);
    }

    private void handleViewAllReservationsAction(HttpServletRequest request, HttpServletResponse response, String keyword) throws ServletException, IOException, SQLException {
        List<Reservation> reservations = (keyword != null && !keyword.trim().isEmpty())
                ? reservationDAO.searchReservations(keyword) : reservationDAO.getAllReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/adminPanel.jsp").forward(request, response);
    }

    private void handleSearchByDateRangeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        if (startDateString != null && endDateString != null) {
            Date startDate = Date.valueOf(startDateString);
            Date endDate = Date.valueOf(endDateString);
            List<Reservation> reservations = reservationDAO.searchReservationsByDateRange(startDate, endDate);
            request.setAttribute("reservations", reservations);
        }
        request.getRequestDispatcher("/adminPanel.jsp").forward(request, response);
    }

    private void handleGeneratePdfByDateRangeAction(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        if (startDateString != null && endDateString != null) {
            Date startDate = Date.valueOf(startDateString);
            Date endDate = Date.valueOf(endDateString);
            List<Reservation> reservations = reservationDAO.searchReservationsByDateRange(startDate, endDate);
            generatePdfReport(reservations, response, startDate, endDate);
        }
    }

    private void handleViewCustomerDetailsAction(HttpServletRequest request, HttpServletResponse response, String keyword) throws ServletException, IOException, SQLException {
        List<Reservation> reservations = (keyword != null && !keyword.trim().isEmpty())
                ? reservationDAO.searchReservations(keyword) : reservationDAO.getCustomerDetails();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/customerDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("add".equals(action)) {
                handleAddAction(request, response, session);
            } else if ("update".equals(action)) {
                handleUpdateAction(request, response, session);
            }
        } catch (SQLException | NumberFormatException e) {
            session.setAttribute("errorMessage", "Error: " + e.getMessage());
            response.sendRedirect("ErrorPage.jsp");
        } catch (ParseException e) {
            session.setAttribute("errorMessage", "Invalid time format: " + e.getMessage());
            response.sendRedirect("ErrorPage.jsp");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("ErrorPage.jsp");
        }
    }

    private void handleAddAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException, ParseException {
        int resId = Integer.parseInt(request.getParameter("resId"));
        String title = request.getParameter("title");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("time");
        int pax = Integer.parseInt(request.getParameter("pax"));
        String remarks = request.getParameter("remarks");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        // Parse date and time
        Date date = Date.valueOf(dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Time time = new Time(sdf.parse(timeString).getTime());

        // Create new reservation object
        Reservation reservation = new Reservation(resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive);

        // Add reservation to the database
        reservationDAO.addReservation(reservation);

        session.setAttribute("successMessage", "Reservation added successfully");
        response.sendRedirect("reservations?action=viewAdminPanel");
    }

    private void handleUpdateAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, IOException, ParseException {
        // Similar to add action, but with update logic
        int id = Integer.parseInt(request.getParameter("id"));
        int resId = Integer.parseInt(request.getParameter("resId"));
        String title = request.getParameter("title");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("time");
        int pax = Integer.parseInt(request.getParameter("pax"));
        String remarks = request.getParameter("remarks");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        // Parse date and time
        Date date = Date.valueOf(dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Time time = new Time(sdf.parse(timeString).getTime());

        // Create updated reservation object
        Reservation reservation = new Reservation(id, resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive);

        // Update the reservation in the database
        reservationDAO.updateReservation(reservation);

        session.setAttribute("successMessage", "Reservation updated successfully");
        response.sendRedirect("reservations?action=viewAdminPanel");
    }

    // PDF generation methods are unchanged
    private void generatePdfReport(List<Reservation> reservations, HttpServletResponse response, Date startDate, Date endDate) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            String titleText;
            if (startDate != null && endDate != null) {
                titleText = String.format("May's Table Reservations from %s to %s", startDate.toString(), endDate.toString());
            } else {
                titleText = "May's Table All Reservations";
            }
            Paragraph title = new Paragraph(titleText, fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Empty line

            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);

            float[] columnWidths = {0.75f, 1f, 1f, 2f, 2f, 2.75f, 2.5f, 2f, 1f, 3f};
            table.setWidths(columnWidths);

            PdfPCell cell = new PdfPCell(new Paragraph("#"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Res ID"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Title"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("First Name"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Last Name"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Phone"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Date"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Time"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Pax"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Remarks"));
            table.addCell(cell);

            for (Reservation reservation : reservations) {
                table.addCell(String.valueOf(reservation.getId()));
                table.addCell(String.valueOf(reservation.getResId()));
                table.addCell(reservation.getTitle());
                table.addCell(reservation.getFirstName());
                table.addCell(reservation.getLastName());
                table.addCell(String.valueOf(reservation.getPhone()));
                table.addCell(reservation.getDate().toString());
                table.addCell(reservation.getTime().toString());
                table.addCell(String.valueOf(reservation.getPax()));
                table.addCell(reservation.getRemarks());
            }

            document.add(table);
            document.close();

            response.setHeader("Content-Disposition", "attachment; filename=reservation_report.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }

    private void generateCustomerDetailsPdfReport(List<Reservation> reservations, HttpServletResponse response) throws IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            String titleText = "May's Table Customer Details";
            Paragraph title = new Paragraph(titleText, fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Empty line

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            float[] columnWidths = {1f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell cell = new PdfPCell(new Paragraph("Title"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("First Name"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Last Name"));
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Phone"));
            table.addCell(cell);

            for (Reservation reservation : reservations) {
                table.addCell(reservation.getTitle());
                table.addCell(reservation.getFirstName());
                table.addCell(reservation.getLastName());
                table.addCell(String.valueOf(reservation.getPhone()));
            }

            document.add(table);
            document.close();

            response.setHeader("Content-Disposition", "attachment; filename=customer_details_report.pdf");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }
}

