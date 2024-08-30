package seat.model;

import common.SeatType;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class Seat {

    private int seatId;
    private String seatName;
    private SeatType seatType;
    private int seatNumber;
    private int seatPassword;
    private int maxPeople;

}
