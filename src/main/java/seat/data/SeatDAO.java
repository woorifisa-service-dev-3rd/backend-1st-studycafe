package seat.data;

import common.SeatType;
import reservation.data.ReservationDAO;
import seat.model.Seat;

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
        // 색상 코드 정의
        final String RED = "\u001B[31m";
        final String DARK_GRAY = "\u001B[37;1m";
        final String RESET = "\u001B[0m";

        // 좌석 배치 배열
        String[][] layout = {
                {"  1", "  2", "  3", "  4", "  5", "  6", "  7", "  8", "  9", " 10", " 11", " 12"},
                {" 13", " 14", " 15", " 16", " 17", " 18", " 19", " 20", " 21", " 22", " 23", " 24"},
                {" 25", " 26", " 27", " 28", " 29", " 30", " 31", " 32", " 33", " 34", " 35", " 36"},
                {" 37", " 38", " 39", " 40", " 41", " 42", " 43", " 44", " 45", " 46", " 47", " 48"},
                {" 49", " 50", " 51", " 52", " 53", " 54", " 55", " 56", " 57", " 58", " 59", " 60"},
                {" 61", " 62", " 63", " 64", " 65", " 66", " 67", " 68", " 69", " 70", " 71", " 72"},
                {" 73", " 74", " 75", " 76", " 77"}
        };

        final int SEAT_WIDTH = 8;  // 좌석 폭을 조정 (좌석 번호와 상태에 맞게 조정)
        final String BORDER = "+--------";  // 경계 문자열
        final int SEATS_PER_ROW = 12;  // 한 행에 표시할 좌석 수

        // 각 행에 대해 반복
        for (int row = 0; row < layout.length; row++) {
            String[] currentRow = layout[row];
            int i = 0;

            // 상단 경계 출력
            while (i < currentRow.length) {
                System.out.print(BORDER);
                if ((i + 1) % SEATS_PER_ROW == 0) {
                    System.out.print("+");
                }
                i++;
            }
            System.out.println();

            // 좌석 번호 및 상태 출력
            i = 0;
            while (i < currentRow.length) {
                String cell = currentRow[i].trim();
                if (!cell.isEmpty()) {
                    int seatId = Integer.parseInt(cell.trim());
                    Seat seat = seats.get(seatId - 1);  // 좌석 ID는 1부터 시작하기 때문에 인덱스는 -1
                    String seatType = seat.getSeatType() == SeatType.PERSON ? "P" : "G";
                    boolean isReserved = !reservationDAO.isSeatAvailable(seatId);
                    String status = isReserved ? "[X]" : "[ ]";

                    // 좌석을 일정한 폭으로 맞추어 출력
                    String seatDisplay = seatType + seatId + status;

                    // 색상 적용
                    if (status.equals("[X]")) {
                        System.out.printf("| " + DARK_GRAY + "%-" + (SEAT_WIDTH - 1) + "s" + RESET, seatDisplay);
                    }
                    else if (seatType.equals("G")) {
                        System.out.printf("| " + RED + "%-" + (SEAT_WIDTH - 1) + "s" + RESET, seatDisplay);
                    } else {
                        System.out.printf("| %-" + (SEAT_WIDTH - 1) + "s", seatDisplay);
                    }
                } else {
                    // 빈 칸을 일정한 폭으로 맞추기
                    System.out.print("|" + " ".repeat(SEAT_WIDTH - 1));
                }
                if ((i + 1) % SEATS_PER_ROW == 0) {
                    System.out.print("|");
                }
                i++;
            }
            System.out.println();

            // 하단 경계 출력
            i = 0;
            while (i < currentRow.length) {
                System.out.print(BORDER);
                if ((i + 1) % SEATS_PER_ROW == 0) {
                    System.out.print("+");
                }
                i++;
            }
            System.out.println();
        }
    }
}