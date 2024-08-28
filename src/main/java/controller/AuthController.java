package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.data.UserDAO;
import user.model.User;

@WebServlet("/auth")
public class AuthController implements Controller{
	
	private UserDAO userDao;

	public AuthController() {
		super();
		this.userDao = new UserDAO();
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// action 파라미터 -> 로그인/로그아웃 처리 구분
		String action = request.getParameter("action");
		
		if ("login".equals(action)) {
			handleLogin(request, response);
		} else if ("logout".equals(action)) {
			handleLogout(request, response);
		}
		
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// UserDAO에서 사용할 메서드: LoginValidation		
		// 1. 브라우저에서 전달받은 입력값 추출
		String id = request.getParameter("id");
		int password = Integer.parseInt(request.getParameter("password"));
		
		// 2. 로그인 메서드
		User user = (User) userDao.LoginValidation(id, password);
		response.sendRedirect("index.html");
		
		// 3. 로그인/로그아웃 처리
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);			
		} else {
			System.out.println("로그인 실패");
			response.sendRedirect("loginForm.html");
		}
	}

	private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate(); // 세션 끊기
		}
		
		response.sendRedirect("index.html");
		
	}
	
}
