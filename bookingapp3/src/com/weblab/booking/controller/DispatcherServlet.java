package com.weblab.booking.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.weblab.booking.config.BookingAppConfig;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ApplicationContext container = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
 	@Override
	public void init() throws ServletException {
//		container = new ClassPathXmlApplicationContext("classpath:com/weblab/booking/config/BookingAppConfig.xml");		
		container = new AnnotationConfigApplicationContext(BookingAppConfig.class);		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getRequestURI();
		if (path.equals("/"))  path="/index";
		String viewName = null;
		
		// step #2. data processing ==> dispatch request to controller
		HandlerMapping mapper = container.getBean(HandlerMapping.class);
		Controller handler = mapper.getHandler(path);
		
		if (handler != null) {
			viewName = handler.handleRequest(request, response);
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
