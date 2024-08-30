package seat.service;

import seat.data.SeatDAO;
import seat.model.Seat;

public class SeatService {

    private SeatDAO seatDAO;

    public SeatService(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    public Seat selectSeat(int seatId) {
        if (seatDAO.isSeatAvailable(seatId)) {
            return seatDAO.getSeatById(seatId);
        } else {
            System.out.println("해당 좌석은 이용할 수 없습니다. 다시 선택해 주세요.");
            return null;
        }
    }

    public boolean validateGroupSeat(Seat seat, int groupSize) {
        if ("GROUP".equalsIgnoreCase(String.valueOf(seat.getSeatType())) && groupSize > seat.getMaxPeople()) {
            System.out.println("수용 가능한 인원을 넘었습니다.");
            return false;
        }
        return true;
    }
}