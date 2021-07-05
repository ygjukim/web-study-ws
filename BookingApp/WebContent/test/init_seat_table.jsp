<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fullstack.booking.model.*" %>    
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

//	Seat seat = seatDao.getSeat(1);	
//	System.out.println(seat);

	Seat seat = null;
	int count = seatDao.getSeatCount("B");
	
	if (count > 0) {
		out.println("이미 SEAT 테이블은 초기화 되었음.<br><hr>");
	}
	else {
		out.println("SEAT 테이블은 초기화합니다.<br><hr>");
		
		seat = new Seat();
		
		seat.setType("R");
		seat.setRsvSeq(0);
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
	
	for (int i=1; i<=100; i++) {
		seat = seatDao.getSeat(i);
		out.println(seat + "<br>");
	}
%>
</body>
</html>