package com.weblab.boardapp.controller.admin.board.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Servlet implementation class NoticeListController
 */
@WebServlet("/admin/board/notice/list")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListController() {
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
		
		List<NoticeView> list = noticeService.getNoticeViewList(field, query, page);
		int count = noticeService.getNoticeCount(field, query);
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		// step #3. output processing results
		String viewName = "/WEB-INF/view/admin/board/notice/list.jsp";
		
		RequestDispatcher view = request.getRequestDispatcher(viewName);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		String cmd = request.getParameter("cmd");
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String[] ids = request.getParameter("ids").trim().split(" ");
				
		// step #2. data processing
		NoticeService noticeService = (NoticeService)getServletContext().getAttribute("notice_service");
		
		switch (cmd) {
		case "일괄공개":
			List<String> openList = Arrays.asList(openIds);
			List<String> closeList = new ArrayList<String>(Arrays.asList(ids));
			closeList.removeAll(openList);
			noticeService.pubNoticeAll(openList, closeList);
			break;
		case "일괄삭제":
			int[] dids = new int[delIds.length];
			for (int i=0; i<dids.length; i++) {
				dids[i] = Integer.parseInt(delIds[i]);
			}
			noticeService.deleteNoticeAll(dids);
			break;
		}
		
		// step #3. output processing results
		response.sendRedirect("list");
	}

}
