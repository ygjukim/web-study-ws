package com.weblab.booking.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.booking.dao.ReservationDao;
import com.weblab.booking.dao.ReservationJdbcDao;
import com.weblab.booking.dao.SeatDao;
import com.weblab.booking.dao.SeatJdbcDao;
import com.weblab.booking.service.ReservationService;
import com.weblab.booking.service.ReservationServiceImpl;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ReservationService rsvService = null;	
	private HandlerMapping mapper = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
 	@Override
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		String driver = context.getInitParameter("jdbc_driver");
		String url = context.getInitParameter("db_url");
		String userName = context.getInitParameter("db_userid");
		String password = context.getInitParameter("db_passwd");
		
		SeatDao seatDao = new SeatJdbcDao(driver, url, userName, password);
		ReservationDao rsvDao = new ReservationJdbcDao(driver, url, userName, password);
		rsvService = new ReservationServiceImpl(rsvDao, seatDao);
		
		mapper = new HandlerMapping();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getRequestURI();
		String viewName = null;
		
		// step #2. data processing ==> dispatch request to controller
		Controller handler = mapper.getHandler(path);
		
		if (handler != null) {
			viewName = handler.handleRequest(request, response, rsvService);
		}
		
		if (viewName == null) {
			viewName = "error.jsp";
		}
				
		// step #3. output processing results
		if (viewName != null) {
			viewName = viewName.trim();
			
			viewName = "/WEB-INF/views/" + viewName;
			
			RequestDispatcher view = request.getRequestDispatcher(viewName);
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
