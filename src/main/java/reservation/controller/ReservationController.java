package reservation.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.PriceType;
import common.SeatType;
import controller.Controller;
import reservation.model.Reservation;
import reservation.service.ReservationService;
import seat.model.Seat;
import seat.service.SeatService;
import user.model.User;
import user.service.UserService;

@WebServlet("/reservation")
public class ReservationController implements Controller {

    private ReservationService reservationService;
    private SeatService seatService;
    private UserService userService;

    public ReservationController(ReservationService reservationService, SeatService seatService, UserService userService) {
        this.reservationService = reservationService;
        this.seatService = seatService;
        this.userService = userService;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    	
    	String selectionUrl = "/WEB-INF/seatSelection.jsp";

    	
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("loginForm.html"); // 로그인 페이지로 리다이렉트
            return;
        }

        int userUid = user.getUserUid();

        // 좌석 선택
        int selectedSeatId = Integer.parseInt(request.getParameter("seatId"));
        Seat seat = seatService.selectSeat(selectedSeatId);

        if (seat == null) {
            request.setAttribute("message", "해당 좌석은 이용할 수 없습니다.");
            request.getRequestDispatcher(selectionUrl).forward(request, response);
            return;
        }

        // 단체석일 경우 인원 수 확인
        if (seat.getSeatType() == SeatType.GROUP) {
            int groupSize = Integer.parseInt(request.getParameter("groupSize"));
            boolean isGroupSeatValid = seatService.validateGroupSeat(seat, groupSize);

            if (!isGroupSeatValid) {
                request.setAttribute("message", "단체석 예약에 실패했습니다.");
                request.getRequestDispatcher(selectionUrl).forward(request, response);
                return;
            }
        }

        // 사용 시간 선택 및 가격 타입 결정
        String selectedTime = request.getParameter("time");
        PriceType selectedPriceType = PriceType.fromTime(selectedTime);

        if (!userService.hasEnoughTime(userUid, selectedPriceType)) {
            request.setAttribute("message", "보유 시간이 부족합니다. 다시 선택해 주세요.");
            request.getRequestDispatcher(selectionUrl).forward(request, response);
            return;
        }

        // 예약 생성
        Reservation reservation = reservationService.prepareReservation(userUid, seat.getSeatId(), userService.getTimeInHours(selectedPriceType));
        reservationService.createReservation(reservation);
        userService.deductTime(userUid, selectedPriceType);

        // 성공 메시지와 함께 페이지로 리다이렉트
        request.setAttribute("message", "예약이 완료되었습니다.");
        request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
    }
}
