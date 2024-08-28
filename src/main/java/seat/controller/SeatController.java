package seat.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import seat.service.SeatService;

@WebServlet("/seat")
public class SeatController implements Controller {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // 좌석 선택 처리 로직 추가 가능
        request.getRequestDispatcher("/seatSelection.jsp").forward(request, response);
    }
}
