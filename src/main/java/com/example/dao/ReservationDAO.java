package com.example.dao;

import com.example.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_arcade_mays";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "cloudslt@123";

    public ReservationDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load the MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    // A method to get a connection, checks if the connection is closed and reopens if necessary
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int resId = resultSet.getInt("resId");
                String title = resultSet.getString("title");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                Date date = resultSet.getDate("date");
                Time time = resultSet.getTime("time");
                int pax = resultSet.getInt("pax");
                String remarks = resultSet.getString("remarks");
                boolean isActive = resultSet.getBoolean("is_active");

                reservations.add(new Reservation(id, resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving reservations: " + e.getMessage(), e);
        }
        return reservations;
    }

    public Reservation getReservationById(int id) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        Reservation reservation = null;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int resId = resultSet.getInt("resId");
                    String title = resultSet.getString("title");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phone = resultSet.getString("phone");
                    Date date = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");
                    int pax = resultSet.getInt("pax");
                    String remarks = resultSet.getString("remarks");
                    boolean isActive = resultSet.getBoolean("is_active");

                    reservation = new Reservation(id, resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving reservation by ID: " + e.getMessage(), e);
        }
        return reservation;
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (resId, title, firstName, lastName, phone, date, time, pax, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservation.getResId());
            statement.setString(2, reservation.getTitle());
            statement.setString(3, reservation.getFirstName());
            statement.setString(4, reservation.getLastName());
            statement.setString(5, reservation.getPhone());
            statement.setDate(6, reservation.getDate());
            statement.setTime(7, reservation.getTime());
            statement.setInt(8, reservation.getPax());
            statement.setString(9, reservation.getRemarks());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding reservation: " + e.getMessage(), e);
        }
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET resId = ?, title = ?, firstName = ?, lastName = ?, phone = ?, date = ?, time = ?, pax = ?, remarks = ? WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservation.getResId());
            statement.setString(2, reservation.getTitle());
            statement.setString(3, reservation.getFirstName());
            statement.setString(4, reservation.getLastName());
            statement.setString(5, reservation.getPhone());
            statement.setDate(6, reservation.getDate());
            statement.setTime(7, reservation.getTime());
            statement.setInt(8, reservation.getPax());
            statement.setString(9, reservation.getRemarks());
            statement.setInt(10, reservation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating reservation: " + e.getMessage(), e);
        }
    }

    public void deleteReservation(int id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting reservation: " + e.getMessage(), e);
        }
    }

    // Add a method to cancel a reservation by updating its status
    public void cancelReservation(int id) throws SQLException {
        String sql = "UPDATE reservations SET is_active = 0 WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Add a method to search reservations by date and keyword
    public List<Reservation> searchReservationsByDateAndKeyword(Date date, String keyword) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE date = ? AND (firstName LIKE ? OR lastName LIKE ? OR remarks LIKE ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, date);
            String searchKeyword = "%" + keyword + "%";
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);
            statement.setString(4, searchKeyword);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int resId = resultSet.getInt("resId");
                    String title = resultSet.getString("title");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phone = resultSet.getString("phone");
                    Date retrievedDate = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");
                    int pax = resultSet.getInt("pax");
                    String remarks = resultSet.getString("remarks");
                    boolean isActive = resultSet.getBoolean("is_active");

                    reservations.add(new Reservation(id, resId, title, firstName, lastName, phone, retrievedDate, time, pax, remarks, isActive));
                }
            }
        }
        return reservations;
    }

    // Add a method to get reservations by a specific date
    public List<Reservation> getReservationsByDate(Date date) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE date = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int resId = resultSet.getInt("resId");
                    String title = resultSet.getString("title");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phone = resultSet.getString("phone");
                    Date retrievedDate = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");
                    int pax = resultSet.getInt("pax");
                    String remarks = resultSet.getString("remarks");
                    boolean isActive = resultSet.getBoolean("is_active");

                    reservations.add(new Reservation(id, resId, title, firstName, lastName, phone, retrievedDate, time, pax, remarks, isActive));
                }
            }
        }
        return reservations;
    }

    // Add a method to search reservations by a keyword
    public List<Reservation> searchReservations(String keyword) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE firstName LIKE ? OR lastName LIKE ? OR phone LIKE ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int resId = resultSet.getInt("resId");
                    String title = resultSet.getString("title");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phone = resultSet.getString("phone");
                    Date date = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");
                    int pax = resultSet.getInt("pax");
                    String remarks = resultSet.getString("remarks");
                    boolean isActive = resultSet.getBoolean("is_active");

                    reservations.add(new Reservation(id, resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive));
                }
            }
        }
        return reservations;
    }

    // Add a method to search reservations by a date range
    public List<Reservation> searchReservationsByDateRange(Date startDate, Date endDate) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE date BETWEEN ? AND ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int resId = resultSet.getInt("resId");
                    String title = resultSet.getString("title");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String phone = resultSet.getString("phone");
                    Date date = resultSet.getDate("date");
                    Time time = resultSet.getTime("time");
                    int pax = resultSet.getInt("pax");
                    String remarks = resultSet.getString("remarks");
                    boolean isActive = resultSet.getBoolean("is_active");

                    reservations.add(new Reservation(id, resId, title, firstName, lastName, phone, date, time, pax, remarks, isActive));
                }
            }
        }
        return reservations;
    }

    // Add a method to get customer details (distinct customers based on name and phone)
    public List<Reservation> getCustomerDetails() throws SQLException {
        List<Reservation> customerDetails = new ArrayList<>();
        String sql = "SELECT DISTINCT title, firstName, lastName, phone, is_active FROM reservations ORDER BY firstName ASC";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String title = rs.getString("title");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phone = rs.getString("phone");
                boolean isActive = rs.getBoolean("is_active");

                Reservation customer = new Reservation(0, title, firstName, lastName, phone, isActive);
                customerDetails.add(customer);
            }
        }
        return customerDetails;
    }

}
