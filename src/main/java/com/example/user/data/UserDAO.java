package com.example.user.data;

import com.example.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                        rs.getInt("password"),
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

    // 복사1
    public List<User> LoginValidation(String id, int password) throws SQLException {
        List<User> user = new ArrayList<User>();

        // connection, pstmt, resultset
        final String selectQuery = "SELECT userUid, name, phone, resttime, point, id, password FROM user where id=? and password=?";
        PreparedStatement pstmt = connection.prepareStatement(selectQuery);
        pstmt.setString(1, id);
        pstmt.setInt(2, password);
        ResultSet resultSet = pstmt.executeQuery();

        try(pstmt; resultSet;){
            if(resultSet.next()) {
                int getUserUid = resultSet.getInt("userUid");
                String getName = resultSet.getString("name");
                String getPhone = resultSet.getString("phone");
                int getResttime = resultSet.getInt("resttime");
                int getPoint = resultSet.getInt("point");
                String getId = resultSet.getString("id");
                int getPassword = resultSet.getInt("password");

                user.add(new User(getUserUid, getPassword, getName, getId, getPhone, getResttime, getPoint));
            }

            return user;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 복사끝1

    // 복사2
    public void updateRestTime(User user, int updatedTime, int updatedPoint) throws SQLException {
        final String chargeQuery = "UPDATE user SET resttime = ?, point = ?  WHERE userUid = ?";
        PreparedStatement pstmt = connection.prepareStatement(chargeQuery);
        pstmt.setInt(1, updatedTime);
        pstmt.setInt(2, updatedPoint);
        pstmt.setInt(3, user.getUserUid());
        pstmt.executeUpdate();
        pstmt.close();
        connection.close();
    }
    // 복사끝2
}

