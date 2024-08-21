package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.UserDAO;
import com.example.model.User;

public class UserService {

	public static User login() throws IOException, SQLException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	List<User> loginResult = new ArrayList<User>();
    	while(loginResult.size() == 0) {
    		System.out.println("아이디와 비밀번호를 입력해주세요.");
    		System.out.println("아이디: ");
    		String inputId = br.readLine();
    		int inputPassword = -1;
    		while (inputPassword == -1) {    		
    			System.out.println("비밀번호: ");
    			try {    		
    				inputPassword = Integer.parseInt(br.readLine());
    			} catch (NumberFormatException e) {
    				System.out.println("올바른 형식의 비밀번호를 입력해주세요.");
    				continue;
    			}
    			break;
    		}
    		
    		UserDAO login = new UserDAO();
			loginResult = (List<User>) login.LoginValidation(inputId, inputPassword);
			if (loginResult.size() == 0) {
				System.out.println("아이디 혹은 비밀번호가 잘못되었습니다.");
				System.out.println("다시 입력하시겠습니까? (y/n)");
				String answer = br.readLine().trim();
				System.out.println();
				if (answer.equals("n")) {
					System.exit(0);
				}
				continue;
			}
			break;
    	}
    	return loginResult.get(0);
	}


}
