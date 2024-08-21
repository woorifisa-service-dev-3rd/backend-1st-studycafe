package com.example.reservation.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

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