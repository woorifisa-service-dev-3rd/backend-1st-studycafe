package com.example;

import java.io.IOException;
import java.sql.SQLException;
import com.example.model.User;
import com.example.service.UserService;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
    	User user;
    	user = UserService.login();
    }
}