package com.example.dao;

import com.example.model.Seat;
import com.example.model.SeatType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    // private static final String USER_NAME = "root";
    // private static final String PASSWORD = "kj003852@";
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/"; // DBMS 서버의 주소
    // private static final String DATABASE = "studycafe";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/studyCafeDB", "root", "1234");
    }

    public List<Seat> findAll() {
        final String selectQuery = "SELECT * FROM seat";
        List<Seat> seats = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                int seatId = resultSet.getInt("seatId");
                String seatName = resultSet.getString("seatName");
                SeatType seatType = SeatType.valueOf(resultSet.getString("seatType"));
                int seatNumber = resultSet.getInt("seatNumber");
                String seatPassword = resultSet.getString("seatPassword");
                int maxPeople = resultSet.getInt("maxPeople");

                seats.add(new Seat(seatId, seatName, seatType, seatNumber, seatPassword, maxPeople));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public Seat findById(int seatId) {
        final String selectQuery = "SELECT * FROM seat WHERE seatId = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {

            pstmt.setInt(1, seatId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Seat(
                            rs.getInt("seatId"),
                            rs.getString("seatName"),
                            SeatType.valueOf(rs.getString("seatType")),
                            rs.getInt("seatNumber"),
                            rs.getString("seatPassword"),
                            rs.getInt("maxPeople")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Seat seat) {
        final String insertQuery = "INSERT INTO seat (seatName, seatType, seatNumber, seatPassword, maxPeople) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

            pstmt.setString(1, seat.getSeatName());
            pstmt.setString(2, seat.getSeatType().name());
            pstmt.setInt(3, seat.getSeatNumber());
            pstmt.setString(4, seat.getSeatPassword());
            pstmt.setInt(5, seat.getMaxPeople());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Seat seat) {
        final String updateQuery = "UPDATE seat SET seatName = ?, seatType = ?, seatNumber = ?, seatPassword = ?, maxPeople = ? WHERE seatId = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {

            pstmt.setString(1, seat.getSeatName());
            pstmt.setString(2, seat.getSeatType().name());
            pstmt.setInt(3, seat.getSeatNumber());
            pstmt.setString(4, seat.getSeatPassword());
            pstmt.setInt(5, seat.getMaxPeople());
            pstmt.setInt(6, seat.getSeatId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int seatId) {
        final String deleteQuery = "DELETE FROM seat WHERE seatId = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, seatId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
