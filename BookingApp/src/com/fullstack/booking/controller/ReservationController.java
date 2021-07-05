package com.fullstack.booking.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullstack.booking.model.Reservation;
import com.fullstack.booking.model.Seat;
import com.fullstack.booking.service.ReservationService;

/**
 * Servlet implementation class ReservationController
 */
@WebServlet("/")
public class ReservationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request URI info & request parameters
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getRequestURI();
		
		// step #2. data processing ==> dispatch request to controller
		ReservationService rsvService = (ReservationService)getServletContext().getAttribute("rsv_service");
		
		String viewName = null;
		
		if ("/index".equals(path)) {
			viewName = "index.jsp";
		}
		else if ("/reservation/register".equals(path)) {
			String method = request.getMethod().toUpperCase();
			if (method.equals("GET")) {
				List<Seat> seats = rsvService.getAllSeats();
				request.setAttribute("seats", seats);
				
				viewName = "register.jsp";
			}
			else if (method.equals("POST")) {
				Reservation rsv = new Reservation();
				rsv.setName(request.getParameter("name"));
				rsv.setPasswd(request.getParameter("passwd"));
				rsv.setPhone(request.getParameter("phone"));

				String[] seats = request.getParameterValues("seats");
				if (seats.length > 0) {
					int[] seatNumbers = new int[seats.length];
					for (int i=0; i<seats.length; i++) {
						seatNumbers[i] = (seats[i].charAt(0)-'A') * 10 + Integer.parseInt(seats[i].substring(1)) + 1;
					}
					rsv.setSeatNumbers(seatNumbers);
				}
				else {
					rsv.setSeatNumbers(new int[0]);
				};
				
				int rsvSeq = rsvService.insertReservation(rsv);
				
				List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
				request.setAttribute("name", rsv.getName());
				request.setAttribute("rsv_list", rsvList);

				viewName = "confirm.jsp";
			}
		}
		else if ("/reservation/confirm".equals(path)) {
			String method = request.getMethod().toUpperCase();
			if (method.equals("POST")) {
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				
				List<Reservation> rsvList = rsvService.getReservations(name, phone);
				request.setAttribute("name", name);
				request.setAttribute("rsv_list", rsvList);

				viewName = "confirm.jsp";
			}
		}
		else if ("/reservation/delete".equals(path)) {
			String passwd = request.getParameter("passwd");
			int rsvSeq = Integer.parseInt(request.getParameter("seq"));
			
			Reservation rsv = rsvService.getReservation(rsvSeq);

			String message = null;
			if ( passwd.equals(rsv.getPasswd()) ) {
				rsvService.deleteReservation(rsv);
				message = "예약이 정상적으로 취소되었습니다.";
			}
			else {
				message = "비밀번호 입력이 맞지 않습니다!";
			}
			
			List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
			request.setAttribute("name", rsv.getName());
			request.setAttribute("rsv_list", rsvList);
			request.setAttribute("message", message);

			viewName = "confirm.jsp";
		}
		else if ("/reservation/update".equals(path)) {
			String passwd = request.getParameter("passwd");
			int rsvSeq = Integer.parseInt(request.getParameter("seq"));
			
			Reservation rsv = rsvService.getReservation(rsvSeq);

			String message = null;
			if ( passwd.equals(rsv.getPasswd()) ) {
				List<Seat> seats = rsvService.getAllSeats();
				request.setAttribute("seats", seats);
				request.setAttribute("reservation", rsv);
				
				viewName = "update.jsp";
			}
			else {
				message = "비밀번호 입력이 맞지 않습니다!";
			
				List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
				request.setAttribute("name", rsv.getName());
				request.setAttribute("rsv_list", rsvList);
				request.setAttribute("message", message);
				
				viewName = "confirm.jsp";
			}
		}
		else if ("/reservation/update_proc".equals(path)) {
			Reservation rsv = new Reservation();
			rsv.setRsvSeq(Integer.parseInt(request.getParameter("seq")));
			rsv.setName(request.getParameter("name"));
			rsv.setPasswd(request.getParameter("passwd"));
			rsv.setPhone(request.getParameter("phone"));

			String[] seats = request.getParameterValues("seats");
			if (seats.length > 0) {
				int[] seatNumbers = new int[seats.length];
				for (int i=0; i<seats.length; i++) {
					seatNumbers[i] = (seats[i].charAt(0)-'A') * 10 + Integer.parseInt(seats[i].substring(1)) + 1;
				}
				rsv.setSeatNumbers(seatNumbers);
			}
			else {
				rsv.setSeatNumbers(new int[0]);
			};
			
			int rsvSeq = rsvService.updateReservation(rsv);
			
			List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
			request.setAttribute("name", rsv.getName());
			request.setAttribute("rsv_list", rsvList);

			viewName = "confirm.jsp";
		}
		
		// step #3. output results ==> forward or redirect to view jsp
		if (viewName != null) {
			viewName = viewName.trim();
			if (viewName.startsWith("redirect:")) {
				viewName = viewName.substring("redirect:".length());
				response.sendRedirect(viewName);
			}
			else {
				// resolve view name
				viewName = "/WEB-INF/views/".concat(viewName);
				
				RequestDispatcher view = request.getRequestDispatcher(viewName);
				view.forward(request, response);
			}
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
