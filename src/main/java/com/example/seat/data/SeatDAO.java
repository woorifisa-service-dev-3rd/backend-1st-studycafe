package com.example.seat.data;

import com.example.common.SeatType;
import com.example.reservation.data.ReservationDAO;
import com.example.seat.model.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    private Connection connection;

    public SeatDAO(Connection connection) {
        this.connection = connection;
    }

    public Seat getSeatById(int seatId) {
        String query = "SELECT * FROM seat WHERE seatId = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, seatId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Seat.builder()
                        .seatId(rs.getInt("seatId"))
                        .seatName(rs.getString("seatName"))
                        .seatType(SeatType.valueOf(rs.getString("seatType")))
                        .seatNumber(rs.getInt("seatNumber"))
                        .seatPassword(rs.getInt("seatPassword"))
                        .maxPeople(rs.getInt("maxPeople"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSeatAvailable(int seatId) {

        String query = "SELECT COUNT(*) FROM seat WHERE seatId = ? AND seatId NOT IN (SELECT seatId FROM reservation)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, seatId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 모든 좌석 정보를 가져오는 메서드
    public List<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT * FROM seat";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Seat seat = Seat.builder()
                        .seatId(rs.getInt("seatId"))
                        .seatName(rs.getString("seatName"))
                        .seatType(SeatType.valueOf(rs.getString("seatType"))) // Enum으로 변환
                        .seatNumber(rs.getInt("seatNumber"))
                        .seatPassword(rs.getInt("seatPassword"))
                        .maxPeople(rs.getInt("maxPeople"))
                        .build();

                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }


    public static List<Seat> generateSeats() {
        List<Seat> seats = new ArrayList<>();

        // 임의의 Group 좌석 지정 (좌석 ID: 16, 17, 18, 25, 26, 35)
        int[] groupSeats = {15, 16, 17, 25, 26, 75, 76, 77};

        for (int i = 1; i <= 77; i++) {
            SeatType seatType = SeatType.PERSON;
            for (int gs : groupSeats) {
                if (i == gs) {
                    seatType = SeatType.GROUP;
                    break;
                }
            }
            seats.add(Seat.builder()
                    .seatId(i)
                    .seatName("Seat " + i)
                    .seatType(seatType)
                    .build());
        }
        return seats;
    }

    public static void displaySeatLayout(List<Seat> seats, ReservationDAO reservationDAO) {
        String[][] layout = {
                {"  1", "  2", "  3", "  4", "  5", "  6", "  7", "  8", "  9", " 10", " 11", " 12", " 13", " 14", " 15", " 16", " 17"},
                {" 18", " 19", "    ", " 20", " 21", " 22", " 23", " 24", " 25", " 26", " 27", " 28", " 29", " 30", " 31", " 32", " 33"},
                {" 34", " 35", " 36", "    ", " 37", " 38", " 39", " 40", " 41", " 42", " 43", " 44", " 45", " 46", " 47", "    ", "    "},
                {" 48", " 49", "    ", " 50", " 51", " 52", " 53", " 54", " 55", " 56", " 57", "    ", "    ", "    ", "    ", "    ", "    "},
                {" 58", " 59", " 60", " 61", " 62", " 63", " 64", " 65", " 66", " 67", " 68", " 69", " 70", " 71", " 72", " 73", " 74"},
                {" 75", " 76", " 77"}
        };

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                String cell = layout[row][col].trim();

                if (!cell.isEmpty()) {
                    // 좌석 ID 추출
                    int seatId = Integer.parseInt(cell.trim());
                    Seat seat = seats.get(seatId - 1); // 좌석 ID는 1부터 시작하기 때문에 인덱스는 -1
                    String seatType = seat.getSeatType() == SeatType.PERSON ? "P" : "G";
                    boolean isReserved = reservationDAO.isSeatAvailable(seatId);
                    String status = isReserved ? "[ ]" : "[X]";

                    // 각 좌석을 8칸으로 맞추기 위해 출력
                    System.out.printf("%-8s", seatType + seatId + status);
                }
                if (col < layout[row].length - 1) {
                    System.out.print("|");  // 좌석 간 구분자
                }
            }
            System.out.println();  // 줄 바꿈
        }
    }

}
