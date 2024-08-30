package reservation.service;

import reservation.data.ReservationDAO;
import reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public void createReservation(Reservation reservation) {
        reservationDAO.createReservation(reservation);
        System.out.println("예약이 완료되었습니다.");
    }

    public Reservation prepareReservation(int userUid, int seatId, int hours) {
        Reservation reservation = new Reservation();
        reservation.setUserUid(userUid);
        reservation.setSeatId(seatId);
        reservation.setStartTime(LocalTime.now());
        reservation.setEndTime(LocalTime.now().plusHours(hours));
        reservation.setDate(LocalDate.now());
        return reservation;
    }
}
