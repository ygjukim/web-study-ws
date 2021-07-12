package com.weblab.boardapp.controller.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.boardapp.entity.notice.NoticeView;
import com.weblab.boardapp.service.notice.NoticeService;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/notice/list")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = (field_ != null && !field_.equals("")) ? field_ : "TITLE";
		String query = (query_ != null && !query_.equals("")) ? query_ : "";
		int page = (page_ != null && !page_.equals("")) ? Integer.parseInt(page_) : 1; 
		
		// step #2. data processing
		NoticeService noticeService = (NoticeService)getServletContext().getAttribute("notice_service");
		
		List<NoticeView> list = noticeService.getNoticeViewPubList(field, query, page);
		int count = noticeService.getNoticePubCount(field, query);
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		// step #3. output processing results
		String viewName = "/WEB-INF/view/notice/list.jsp";
		
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
