<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.weblab.booking.dao.*, com.weblab.booking.entity.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	SeatDao seatDao = new SeatJdbcDao(
		application.getInitParameter("jdbc_driver"),
		application.getInitParameter("db_url"),
		application.getInitParameter("db_userid"),
		application.getInitParameter("db_passwd"));

	List<Seat> seats = seatDao.getSeats("SEAT_TYPE = 'R'");
	if (seats != null) {
		out.println("<h2>SEAT 테이블이 이미 초기화 되어 있습니다...</h2>");
	}
	else {
		Seat seat = new Seat();
		seat.setRsvSeq(0);
		
		seat.setType("R");
		for (int i=1; i<=20; i++) {
			seat.setNumber(i);
			seatDao.insertSeat(seat);
		}
		
		seat.setType("A");
		for (int i=21; i<=50; i++) {
			seat.setNumber(i);
			seatDao.insertSeat(seat);
		}
		
		seat.setType("B");
		for (int i=51; i<=100; i++) {
			seat.setNumber(i);
			seatDao.insertSeat(seat);
		}
	}
	
	seats = seatDao.getSeats("");
	for (Seat seat : seats) {
		out.println(seat.toString() + "<br>");
	}
%>

</body>
</html>