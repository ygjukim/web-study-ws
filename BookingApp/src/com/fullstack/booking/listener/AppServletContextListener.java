package com.fullstack.booking.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fullstack.booking.model.ReservationDao;
import com.fullstack.booking.model.ReservationJdbcDao;
import com.fullstack.booking.model.SeatDao;
import com.fullstack.booking.model.SeatJdbcDao;
import com.fullstack.booking.service.ReservationService;
import com.fullstack.booking.service.ReservationServiceImpl;

public class AppServletContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String driver = context.getInitParameter("jdbc_driver");
		String url = context.getInitParameter("db_url");
		String userName = context.getInitParameter("db_userid");
		String password = context.getInitParameter("db_passwd");
		
		ReservationDao rsvDao = new ReservationJdbcDao(driver, url, userName, password);
		SeatDao seatDao = new SeatJdbcDao(driver, url, userName, password);
		
		ReservationServiceImpl rsvService = new ReservationServiceImpl();
		rsvService.setRsvDao(rsvDao);
		rsvService.setSeatDao(seatDao);
		
		context.setAttribute("rsv_service", rsvService);
	}
	
}
