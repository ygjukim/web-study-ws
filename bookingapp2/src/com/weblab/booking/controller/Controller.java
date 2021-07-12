package com.weblab.booking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.booking.service.ReservationService;

public interface Controller {

	String handleRequest(HttpServletRequest request, HttpServletResponse response, ReservationService rsvService);
	
}
