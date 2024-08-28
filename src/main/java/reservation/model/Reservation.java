package reservation.model;



import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private int resId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private int seatId;
    private int userUid;

}