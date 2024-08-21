package com.example.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static Connection connection = null;

    // 데이터베이스 연결을 생성하는 메서드
    public static Connection getConnection(String propertiesFilePath) {
        if (connection == null) {
            try {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream(propertiesFilePath);
                prop.load(fis);

                final String USER_NAME = prop.getProperty("USER_NAME");
                final String PASSWORD = prop.getProperty("PASSWORD");
                final String DB_URL = prop.getProperty("DB_URL");
                final String DATABASE = prop.getProperty("DATABASE");

                connection = DriverManager.getConnection(DB_URL + DATABASE, USER_NAME, PASSWORD);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // 데이터베이스 연결을 닫는 메서드
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
