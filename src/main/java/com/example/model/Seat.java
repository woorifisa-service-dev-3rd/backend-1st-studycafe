package com.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    private int seatId;
    private String seatName;
    private SeatType seatType;
    private int seatNumber;
    private String seatPassword;
    private int maxPeople;
}
