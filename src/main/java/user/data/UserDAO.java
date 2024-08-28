package user.data;

import user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
    	
    }
    
    public UserDAO(Connection conn) {
        this.connection = conn;
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
            pstmt.setInt(1, time*60);
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
        String selectQuery = "SELECT userUid, name, phone, resttime, point, id, password FROM user where id=? and password=?";
        System.out.println(selectQuery);
        PreparedStatement pstmt = connection.prepareStatement(selectQuery);
        System.out.println(pstmt);
        pstmt.setString(1, id);
        pstmt.setInt(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        
        
        System.out.println(resultSet);

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
                System.out.println(user);
            }

            return user;
        } catch(SQLException e) {
        	System.out.println("여기서 에러~");
            e.printStackTrace();
        }
        return user;
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
    }
    // 복사끝2
}

