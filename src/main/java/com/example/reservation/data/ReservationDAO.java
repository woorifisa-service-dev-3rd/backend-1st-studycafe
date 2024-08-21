package com.example.reservation.data;

import com.example.reservation.model.Reservation;

import java.sql.*;

public class ReservationDAO {

    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public void createReservation(Reservation reservation) {

        String query = "INSERT INTO reservation (startTime, endTime, date, seatId, userUid) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setTime(1, Time.valueOf(reservation.getStartTime()));
            pstmt.setTime(2, Time.valueOf(reservation.getEndTime()));
            pstmt.setDate(3, Date.valueOf(reservation.getDate()));
            pstmt.setInt(4, reservation.getSeatId());
            pstmt.setInt(5, reservation.getUserUid());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
