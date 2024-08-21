package com.example.user.data;

import com.example.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User getUserByUid(int userUid) {
        String query = "SELECT * FROM user WHERE userUid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userUid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("userUid"),
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("phone"),
                        rs.getInt("resttime"),
                        rs.getInt("point")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserTime(int userUid, int time) {
        String query = "UPDATE user SET resttime = resttime - ? WHERE userUid = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, time);
            pstmt.setInt(2, userUid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

