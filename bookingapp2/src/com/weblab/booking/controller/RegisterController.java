package com.weblab.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;

public class RegisterController implements Controller {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, ReservationService rsvService) {
		String viewName  = null;
		String method = request.getMethod().toUpperCase();
		
		if (method.equals("GET")) {
			int[] seats = new int[100];

			List<Integer> seatNumbers = rsvService.getSeatNumbers(true);				
			if (seatNumbers != null && seatNumbers.size() > 0) {
				for (int number : seatNumbers) {
					seats[number-1] = 1;
				}
			}
			
			request.setAttribute("seats", seats);
			
			viewName = "register.jsp";
		}
		else if (method.equals("POST")) {
			String name = request.getParameter("name");
			String passwd = request.getParameter("passwd");
			String phone = request.getParameter("phone");
			
			Reservation rsv = new Reservation();
			rsv.setName(name);
			rsv.setPasswd(passwd);
			rsv.setPhone(phone);
			
			String[] seats = request.getParameterValues("seats");
			if (seats.length > 0) {
				int[] seatNumbers = new int[seats.length];
				for (int i=0; i<seats.length; i++) {
					seatNumbers[i] = (seats[i].charAt(0)-'A') * 10 + Integer.parseInt(seats[i].substring(1));
				}
				rsv.setSeatNumbers(seatNumbers);
			}
			else {
				rsv.setSeatNumbers(new int[0]);
			};
			
			rsvService.registerReservation(rsv);
			
			List<Reservation> rsvList = rsvService.getReservations(name, phone);
			
			request.setAttribute("name", name);
			request.setAttribute("rsv_list", rsvList);
			
			viewName = "confirm.jsp";
		}
		
		return viewName;
	}

}
