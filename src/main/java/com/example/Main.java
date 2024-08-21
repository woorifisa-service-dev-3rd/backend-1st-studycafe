package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import com.example.model.User;
import com.example.service.UserService;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
    	User user;
    	user = UserService.login();
    	while (true) {
    		int selectedService;
    		System.out.println("이용하실 서비스를 선택해주세요");
    		System.out.println("1. 좌석 선택   2. 시간 충전");
    		selectedService = Integer.parseInt(br.readLine());
    		if (selectedService == 2) {
    			UserService.chargeService(user);
    			continue;
    		}
    	}
    }
}