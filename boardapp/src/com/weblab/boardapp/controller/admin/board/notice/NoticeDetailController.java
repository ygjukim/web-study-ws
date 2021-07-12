package com.weblab.boardapp.controller.admin.board.notice;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.boardapp.entity.notice.Notice;
import com.weblab.boardapp.service.notice.NoticeService;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/admin/board/notice/detail")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		String id_ = request.getParameter("id");

		int id = (id_ != null && !id_.equals("")) ? Integer.parseInt(id_) : 0; 
		
		// step #2. data processing
		NoticeService noticeService = (NoticeService)getServletContext().getAttribute("notice_service");
		
		Notice notice = noticeService.getNotice(id);
		Notice nextNotice = noticeService.getNextNotice(notice.getId());
		Notice prevNotice = noticeService.getPrevNotice(notice.getId());
		
		request.setAttribute("notice", notice);
		request.setAttribute("next_notice", nextNotice);
		request.setAttribute("prev_notice", prevNotice);
		request.setAttribute("upload_path", 
				getServletContext().getRealPath("/upload") + File.separator);
		
		// step #3. output processing results
		String viewName = "/WEB-INF/view/admin/board/notice/detail.jsp";
		
		RequestDispatcher view = request.getRequestDispatcher(viewName);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
