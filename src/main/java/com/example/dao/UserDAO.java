package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.User;

/**
 * dao: 데이터베이스와의 상호작용 처리, data access object
 */

public class UserDAO {
	// USER_NAME, PASSWORD, DB_URL, DATABASE
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "1234";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE = "studycafedb";
	
	public List<User> LoginValidation(String id, int password) throws SQLException {
		List<User> user = new ArrayList<User>();
		
		// connection, pstmt, resultset
		final String selectQuery = "SELECT userUid, name, phone, resttime, point, id, password FROM user where id=? and password=?";
		Connection connection = DriverManager.getConnection(DB_URL + DATABASE, USER_NAME, PASSWORD);
		PreparedStatement pstmt = connection.prepareStatement(selectQuery);
		pstmt.setString(1, id);
		pstmt.setInt(2, password);
		ResultSet resultSet = pstmt.executeQuery();
	
		try(connection; pstmt; resultSet;){
			if(resultSet.next()) {
				int getUserUid = resultSet.getInt("userUid"); 
				String getName = resultSet.getString("name");
				String getPhone = resultSet.getString("phone");
				int getResttime = resultSet.getInt("resttime");
				int getPoint = resultSet.getInt("point");
				String getId = resultSet.getString("id");
				int getPassword = resultSet.getInt("password");
				
				user.add(new User(getUserUid, getName, getPhone, getResttime, getPoint, getId, getPassword));
			}
			
			return user;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
