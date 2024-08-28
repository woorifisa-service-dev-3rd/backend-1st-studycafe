package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import reservation.controller.ReservationController;
import reservation.data.ReservationDAO;
import reservation.service.ReservationService;
import seat.controller.SeatController;
import seat.data.SeatDAO;
import seat.service.SeatService;
import user.data.UserDAO;
import user.service.UserService;
import util.DBUtil;

@WebServlet(urlPatterns = "/front-controller/*")
public class FrontController extends HttpServlet {

    private Map<String, Controller> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        Connection conn = DBUtil.getConnection();
        SeatService seatService = new SeatService(new SeatDAO(conn));
        UserService userService = new UserService(new UserDAO(conn));
        ReservationService reservationService = new ReservationService(new ReservationDAO(conn));

        controllerMap.put("/seat", new SeatController(seatService));
        controllerMap.put("/reservation", new ReservationController(reservationService, seatService, userService));
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
