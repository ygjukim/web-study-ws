package com.weblab.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;

@Component("/confirm")
public class ConfirmController implements Controller {
	@Autowired
	private ReservationService rsvService;

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
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
