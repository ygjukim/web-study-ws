package com.weblab.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;

public class UpdateController implements Controller {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response,
			ReservationService rsvService) {
		String viewName = null;
		
		String method = request.getMethod().toUpperCase();
		
		if (method.equals("POST")) {
			String seq = request.getParameter("seq");
			String passwd = request.getParameter("passwd");
			String message = null;

			int rsvSeq = Integer.parseInt(seq);
			Reservation rsv = rsvService.getReservation(rsvSeq);
			if (passwd.equals(rsv.getPasswd())) {
				int[] seats = new int[100];

				List<Integer> seatNumbers = rsvService.getSeatNumbers(true);				
				if (seatNumbers != null && seatNumbers.size() > 0) {
					for (int number : seatNumbers) {
						seats[number-1] = 1;
					}
				}
				
				int[] bookedSeats = rsv.getSeatNumbers();
				if (bookedSeats != null && bookedSeats.length > 0) {
					for (int number : bookedSeats) {
						seats[number-1] = 2;
					}
				}
				
				request.setAttribute("seats", seats);
				request.setAttribute("rsv", rsv);
				
				viewName = "update.jsp";
			}
			else {
				message = "입력하신 비밀번호를 틀렸습니다!";

				List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
				
				request.setAttribute("name", rsv.getName());
				request.setAttribute("rsv_list", rsvList);
				request.setAttribute("message", message);
				
				viewName = "confirm.jsp";
			}
			
		}
		
		return viewName;
	}

}
