<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seat Selection</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>
<body>
    <h1>좌석 선택</h1>
    <form action="/studycafe/front-controller/reservation" method="post">
        <label for="seatId">이용할 좌석 번호를 입력하세요:</label>
        <input type="text" id="seatId" name="seatId"><br><br>
        
        <label for="groupSize">인원 수를 입력하세요 (최대 6인 가능, 단체석인 경우):</label>
        <input type="text" id="groupSize" name="groupSize"><br><br>
        
        <label for="time">몇 시간을 이용하시겠습니까? (1시간/2시간/3시간):</label>
        <select id="time" name="time">
            <option value="1시간">1시간</option>
            <option value="2시간">2시간</option>
            <option value="3시간">3시간</option>
        </select><br><br>
        
        <input type="submit" value="예약">
    </form>
</body>
</html>
