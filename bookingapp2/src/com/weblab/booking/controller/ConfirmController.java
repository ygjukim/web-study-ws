package com.weblab.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;

public class ConfirmController implements Controller {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response,
			ReservationService rsvService) {
		String viewName = null;
		
		String method = request.getMethod().toUpperCase();
		
		if (method.equals("POST")) {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			
			List<Reservation> rsvList = rsvService.getReservations(name, phone);
			
			request.setAttribute("name", name);
			request.setAttribute("rsv_list", rsvList);
			
			viewName = "confirm.jsp";
		}
		
		return viewName;
	}

}
