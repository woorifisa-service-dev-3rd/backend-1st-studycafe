package com.example.seat.data;

import com.example.common.SeatType;
import com.example.seat.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatDAO {

    private Connection connection;

    public SeatDAO(Connection connection) {
        this.connection = connection;
    }

    public Seat getSeatById(int seatId) {
        String query = "SELECT * FROM seat WHERE seatId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, seatId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Seat.builder()
                        .seatId(rs.getInt("seatId"))
                        .seatName(rs.getString("seatName"))
                        .seatType(SeatType.valueOf(rs.getString("seatType")))
                        .seatNumber(rs.getInt("seatNumber"))
                        .seatPassword(rs.getInt("seatPassword"))
                        .maxPeople(rs.getInt("maxPeople"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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