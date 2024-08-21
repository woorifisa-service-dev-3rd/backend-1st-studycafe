package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "kj003852@";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"; // DBMS 서버의 주소
    private static final String DATABASE = "studycafe";

    public static void main(String[] args) {
        // 2. DBMS와의 커넥션 열기
        try {
            // JDBC 4.0 버전 이후로 모든 드라이버들은 클래스패스에서 자동으로 로딩됨
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL + DATABASE, USER_NAME, PASSWORD);
            System.out.println(connection);
            // 3. 쿼리 실행을 위한 준비

            // 쿼리문 전달 역할 수행하는 객체 - Statement
            Statement statement = connection.createStatement();

            final String dropTableQuery = "DROP TABLE IF EXISTS todo";

            // 4. 실제 쿼리 수행
            boolean result = statement.execute(dropTableQuery); // 쿼리 수행 진행시켜
            System.out.println(result);

            // 5. DB와의 커넥션 닫아주는 처리
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}