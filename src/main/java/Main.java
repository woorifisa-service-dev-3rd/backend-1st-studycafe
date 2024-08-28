import common.PriceType;
import common.SeatType;
import reservation.data.ReservationDAO;
import reservation.model.Reservation;
import reservation.service.ReservationService;
import seat.data.SeatDAO;
import seat.model.Seat;
import seat.service.SeatService;
import user.data.UserDAO;
import user.model.User;
import user.service.UserService;
import util.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Scanner scanner = new Scanner(System.in);
        Connection conn = DBUtil.getConnection();
        System.out.println("conn" + conn);
        // DAO, Service 초기화
        SeatDAO seatDAO = new SeatDAO(conn);
        UserDAO userDAO = new UserDAO(conn);
        ReservationDAO reservationDAO = new ReservationDAO(conn);

        SeatService seatService = new SeatService(seatDAO);
        UserService userService = new UserService(userDAO);
        ReservationService reservationService = new ReservationService(reservationDAO);

        // user login
        User user;
        user = userService.login(scanner);
        System.out.println("user" + user);

        List<Seat> seats = seatDAO.generateSeats();
        seatDAO.displaySeatLayout(seats, reservationDAO);

        // user
        int selectedOption = -1;
        while (selectedOption != 1) {
            System.out.println("이용하실 서비스를 선택해주세요.");
            System.out.println("1.좌석 선택      2.시간 충전");
            selectedOption = scanner.nextInt();
            if (selectedOption == 2) {
                userService.chargeService(user);
                continue;
            }
            else if (selectedOption == 1) {
                int userUid = user.getUserUid();
                // 좌석 선택
                Seat seat = null;
                while (seat == null) {
                    System.out.println("이용할 좌석 번호를 입력하세요:");
                    int selectedSeatId = scanner.nextInt();
                    seat = seatService.selectSeat(selectedSeatId);
                }

                // 단체석일 경우 인원 수 입력
                boolean isGroupSeatValid = true;
                if (seat.getSeatType() == SeatType.GROUP) {
                    while (true) {
                        System.out.println("인원 수를 입력하세요(최대 6인 가능):");
                        int groupSize = scanner.nextInt();
                        isGroupSeatValid = seatService.validateGroupSeat(seat, groupSize);
                        if (isGroupSeatValid) {
                            break;
                        }else if(!isGroupSeatValid){
                            System.out.println("단체석 예약에 실패했습니다.");
                            continue;
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
            }
            else {
                System.out.println("정확한 번호를 입력해주세요.");
            }
        }

        // DB 연결 닫기
        scanner.close();
        DBUtil.closeConnection();

    }
}