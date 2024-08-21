package com.example;

import com.example.common.PriceType;
import com.example.common.SeatType;
import com.example.reservation.data.ReservationDAO;
import com.example.reservation.model.Reservation;
import com.example.reservation.service.ReservationService;
import com.example.seat.data.SeatDAO;
import com.example.seat.model.Seat;
import com.example.seat.service.SeatService;
import com.example.user.data.UserDAO;
import com.example.user.service.UserService;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Connection conn = DBUtil.getConnection("src/main/resources/jdbc.properties");

        // DAO, Service 초기화
        SeatDAO seatDAO = new SeatDAO(conn);
        UserDAO userDAO = new UserDAO(conn);
        ReservationDAO reservationDAO = new ReservationDAO(conn);

        SeatService seatService = new SeatService(seatDAO);
        UserService userService = new UserService(userDAO);
        ReservationService reservationService = new ReservationService(reservationDAO);


        List<Seat> seats = seatDAO.generateSeats();
        seatDAO.displaySeatLayout(seats, reservationDAO);


        // 사용자 UID 입력
        System.out.println("사용자 UID를 입력하세요:");
        int userUid = scanner.nextInt();

        // 좌석 선택
        Seat seat = null;
        while (seat == null) {
            System.out.println("이용할 좌석 번호를 입력하세요:");
            int selectedSeatId = scanner.nextInt();
            seat = seatService.selectSeat(selectedSeatId);
            System.out.println(seat);
        }

        // 단체석일 경우 인원 수 입력
        boolean isGroupSeatValid = true;
        if (seat.getSeatType() == SeatType.GROUP) {
            while (true) {
                System.out.println("인원 수를 입력하세요:");
                int groupSize = scanner.nextInt();
                isGroupSeatValid = seatService.validateGroupSeat(seat, groupSize);
                if (isGroupSeatValid) {
                    break;
                }else if(!isGroupSeatValid){
                    System.out.println("단체석 예약에 실패했습니다.");
                    return;
                }
            }
        }

        // 사용 시간 선택 및 가격 타입 결정
        PriceType selectedPriceType = null;
        while (true) {
            System.out.println("몇 시간을 이용하시겠습니까? (1시간/2시간/3시간)");
            String selectedTime = scanner.next();
            try {
                selectedPriceType = PriceType.fromTime(selectedTime.trim());

                if (userService.hasEnoughTime(userUid, selectedPriceType)) {
                    System.out.println(selectedPriceType.getTime() + " 선택되었습니다. 가격은 " + selectedPriceType.getPrice() + "원 입니다.");
                    break;
                } else {
                    System.out.println("보유 시간이 부족합니다. 다시 선택해 주세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("잘못된 시간 선택입니다. 다시 선택해 주세요.");
            }
        }

        // 예약 생성
        Reservation reservation = reservationService.prepareReservation(userUid, seat.getSeatId(), userService.getTimeInHours(selectedPriceType));
        reservationService.createReservation(reservation);

        userService.deductTime(userUid, selectedPriceType);

        System.out.println("예약이 완료되었습니다.");



        // DB 연결 닫기
        scanner.close();
        DBUtil.closeConnection();

    }
}