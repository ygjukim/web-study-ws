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
	ReservationDao rsvDao = new ReservationJdbcDao(
		application.getInitParameter("jdbc_driver"),
		application.getInitParameter("db_url"),
		application.getInitParameter("db_userid"),
		application.getInitParameter("db_passwd"));

/*
	Reservation rsv = new Reservation();
	rsv.setName("홍길동");
	rsv.setPasswd("1234");
	rsv.setPhone("010-1111-2222");
	int[] seats = {1, 2, 3};
	rsv.setSeatNumbers(seats);
	
//	int reseult = rsvDao.insertReservation(rsv);
*/	
	List<Reservation> rsvList = rsvDao.getReservations("NAME='홍길동' and PHONE='010-1111-2222'");
	
	for (Reservation r : rsvList) {
		out.println(r.toString() + "<br>");
	}
	
	Reservation rsv = rsvList.get(0);	
	rsv.setPasswd("54321");
	int reseult = rsvDao.updateReservation(rsv);
	
	rsvList = rsvDao.getReservations("NAME='홍길동' and PHONE='010-1111-2222'");
	
	for (Reservation r : rsvList) {
		out.println(r.toString() + "<br>");
	}
%>
</body>
</html>