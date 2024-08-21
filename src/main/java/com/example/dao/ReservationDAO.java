package com.example.dao;

import com.example.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/studyCafeDB", "root", "1234");
    }

    public List<Reservation> findAll() {
        final String selectQuery = "SELECT * FROM reservation";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                int resId = resultSet.getInt("resId");
                Time startTime = resultSet.getTime("startTime");
                Time endTime = resultSet.getTime("endTime");
                Date date = resultSet.getDate("date");

                reservations.add(new Reservation(
                        resId,
                        startTime.toLocalTime(),
                        endTime.toLocalTime(),
                        date.toLocalDate(),
                        resultSet.getInt("seatId"),
                        resultSet.getInt("userUid")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public Reservation findById(int resId) {
        final String selectQuery = "SELECT * FROM reservation WHERE resId = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {

            pstmt.setInt(1, resId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("resId"),
                            rs.getTime("startTime").toLocalTime(),
                            rs.getTime("endTime").toLocalTime(),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("seatId"),
                            rs.getInt("userUid")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Reservation reservation) {
        final String insertQuery = "INSERT INTO reservation (startTime, endTime, date, seatId, userUid) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

            pstmt.setTime(1, Time.valueOf(reservation.getStartTime()));
            pstmt.setTime(2, Time.valueOf(reservation.getEndTime()));
            pstmt.setDate(3, Date.valueOf(reservation.getDate()));
            pstmt.setInt(4, reservation.getSeatId());
            pstmt.setInt(5, reservation.getUserUid());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservation.setResId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int resId) {
        final String deleteQuery = "DELETE FROM reservation WHERE resId = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, resId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}