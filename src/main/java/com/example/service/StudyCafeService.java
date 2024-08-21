package com.example.service;

import com.example.dao.ReservationDAO;
import com.example.dao.SeatDAO;
import com.example.model.Reservation;
import com.example.model.Seat;
import com.example.model.SeatType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class StudyCafeService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    // 좌석 예약 메서드
    public void reserveSeat(int seatId, int userUid, LocalTime startTime, LocalTime endTime, LocalDate date, int numberOfPeople) {
        Seat seat = seatDAO.findById(seatId);

        if (seat == null) {
            System.out.println("좌석 번호가 잘못되었습니다. 다시 선택해주세요.");
            return;
        }

        if (seat.getSeatType() == SeatType.GROUP && numberOfPeople > seat.getMaxPeople()) {
            System.out.println("수용 가능 인원을 넘었습니다.");
            return;
        }

        // 예약 생성
        String seatPassword = generateRandomPassword();
        Reservation reservation = new Reservation(0, startTime, endTime, date, seatId, userUid);
        reservationDAO.insert(reservation);

        System.out.println("예약이 완료되었습니다.");
        System.out.println(reservation);
    }

    // 4자리 랜덤 비밀번호 생성 메서드
    private String generateRandomPassword() {
        Random random = new Random();
        int password = random.nextInt(10000); // 0부터 9999까지의 랜덤 숫자
        return String.format("%04d", password); // 4자리로 포맷팅
    }

    public static void main(String[] args) {
        StudyCafeService service = new StudyCafeService();

        // 예약 예시
        service.reserveSeat(1, 123, LocalTime.of(10, 0), LocalTime.of(12, 0), LocalDate.of(2024, 8, 21), 4);
    }
}