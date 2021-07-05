package com.weblab.boardapp.controller.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.boardapp.entity.NoticeView;
import com.weblab.boardapp.service.notice.NoticeService;
import com.weblab.boardapp.service.notice.NoticeServiceImpl;

/**
 * Servlet implementation class NoticeListController
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
		
		String field = "TITLE";
		if (field_ != null && field_.length() > 0) {
			field = field_;
		}
		
		String query = "";
		if (query_ != null && query_.length() > 0) {
			query = query_;
		}
		
		int page = 1;
		if (page_ != null && page_.length() > 0) {
			page = Integer.parseInt(page_);
		}
		
		// step #2. data processing
		NoticeService noticeService = new NoticeServiceImpl();
		
		List<NoticeView> list = noticeService.getNoticeViewList(field, query, page);
		int count = noticeService.getNoticeCount(field, query);
		
		// step #3. output processing results
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
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
