package com.example.reservation.data;

import com.example.reservation.model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

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


    public boolean isSeatAvailable(int seatId) {

        String query = "SELECT COUNT(*) FROM seat WHERE seatId = ? AND seatId NOT IN (SELECT seatId FROM reservation)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, seatId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
