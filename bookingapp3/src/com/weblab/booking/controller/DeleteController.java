package com.weblab.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weblab.booking.entity.Reservation;
import com.weblab.booking.service.ReservationService;

@Component("/delete")
public class DeleteController implements Controller {
	@Autowired
	private ReservationService rsvService;

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String viewName = null;

		String method = request.getMethod().toUpperCase();
		
		if (method.equals("POST")) {
			String seq = request.getParameter("seq");
			String passwd = request.getParameter("passwd");
			String message = null;

			int rsvSeq = Integer.parseInt(seq);
			Reservation rsv = rsvService.getReservation(rsvSeq);
			if (passwd.equals(rsv.getPasswd())) {
				if (rsvService.deleteReservation(rsv) <= 0) {
					message = "예약취소 작업중에 오류가 발생하여 취소가 되지 않았습니다.";
				}
			}
			else {
				message = "입력하신 비밀번호를 틀렸습니다!";
			}
			
			List<Reservation> rsvList = rsvService.getReservations(rsv.getName(), rsv.getPhone());
			
			request.setAttribute("name", rsv.getName());
			request.setAttribute("rsv_list", rsvList);
			if (message != null) {
				request.setAttribute("message", message);
			}
			
			viewName = "confirm.jsp";
		}

		return viewName;
	}

}
