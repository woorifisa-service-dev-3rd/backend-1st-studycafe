package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.data.UserDAO;
import user.model.User;
import util.DBUtil;

@WebServlet("/auth")
public class AuthController implements Controller{
	
	private UserDAO userDao;

	public AuthController() {
		super();
		Connection connection = DBUtil.getConnection();
		this.userDao = new UserDAO(connection);
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// 인코딩
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println(request.getMethod());
		
		// 로그인 및 로그아웃 - POST, GET
		// UserDAO에서 사용할 메서드: LoginValidation		
		if (request.getMethod().equals("POST")) {
			System.out.println("로그인 절차 수행");
			
			String id = request.getParameter("id");
			int password = Integer.parseInt(request.getParameter("password"));
			System.out.println(id + ", " + password);
			
			List<User> users = userDao.LoginValidation(id, password);
			
			// 유효성 검사
			if (id.isEmpty() || password == 0) {
				out.print("아이디 혹은 비밀번호를 입력하세요.");
				return;
			}
			
			
			// 로그인 성공/실패
			HttpSession session = request.getSession();
			if (users != null) {
				session.setAttribute("user", users);
				System.out.println("로그인 성공");
			} else {
				System.out.println("user: " + users);
				System.out.println("로그인 실패");
			}
			
			// 로그인 상태 확인(재로그인 여부)
			if (session.isNew() || session.getAttribute("id")==null) {
				session.setAttribute("id", id);
				out.print("로그인 완료");
			} else {
				out.print("현재 로그인 상태입니다.");
			}
			out.close();
			
			
		} else if (request.getMethod().equals("GET")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			out.print("로그아웃 완료");
			
		}
			
	}
	
}
