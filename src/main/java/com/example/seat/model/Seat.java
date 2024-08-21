package com.example.seat.model;

import com.example.common.SeatType;
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
