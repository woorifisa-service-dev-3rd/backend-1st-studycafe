package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/*")
public class FrontController extends HttpServlet{
	
	// 세부 URI 주소, 개별 컨트롤러
	private Map<String, Controller> controllerMap = new HashMap();
	
	// Front controller에 개별 controller uri 인스턴스 맵핑
	public FrontController() {
		controllerMap.put("/auth", new AuthController());
		// Seat 관련 controller
		// Reservation 관련 controller
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공통 인코딩
		req.setCharacterEncoding("UTF-8");
		
		// 사용자의 요청 uri 확인
		String path = req.getPathInfo();
		System.out.println("URI: " + path);
		
		// uri별 개별 컨트롤러 불러오기
		Controller controller = controllerMap.get(path);
		
		// 가져온 컨트롤러에 요청 처리 호출
		if (controller != null) {
			try {
				controller.process(req, resp);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
				System.out.println("controller: error");
			}
		} else {
			System.out.println("controller: null");
		}
		
	}

}
