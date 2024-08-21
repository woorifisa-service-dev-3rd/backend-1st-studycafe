package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.dao.UserDAO;
import com.example.model.User;

public class UserService {
	
	static UserDAO userDAO = new UserDAO();

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
    		
    		
			loginResult = (List<User>) userDAO.LoginValidation(inputId, inputPassword);
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
	
	public static void chargeService(User user) throws SQLException, NumberFormatException, IOException {
		HashMap<Object, Object> map = new HashMap<>();
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "5");
		map.put(5, "10");
		map.put(6, "50");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("충전하실 시간을 선택해주세요");
		System.out.println("1. 1시간(3000원)    2. 2시간(5000원)     3. 3시간(6000원)");
		System.out.println("4. 5시간(7000원)    5. 10시간(10000원)   6. 50시간(45000원)");
		int selectedOption = Integer.parseInt(br.readLine());
		
		int plusTime = Integer.parseInt((String) map.get(selectedOption));
		int restTime = user.getResttime();
		int updatedTime = restTime + plusTime*60;
		int currentPoint = user.getPoint();
		int updatedPoint = plusTime*10 + currentPoint;
    	userDAO.updateRestTime(user, updatedTime, updatedPoint);
    	user.setResttime(updatedTime);
    	user.setPoint(updatedPoint);
    	System.out.println("충전이 완료되었습니다!");
	}


}
