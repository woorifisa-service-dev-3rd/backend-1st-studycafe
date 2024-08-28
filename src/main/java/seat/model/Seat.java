package seat.model;

import common.SeatType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
