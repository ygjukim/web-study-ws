package com.weblab.boardapp.controller.admin.board.notice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblab.boardapp.entity.Notice;
import com.weblab.boardapp.service.notice.NoticeService;
import com.weblab.boardapp.service.notice.NoticeServiceImpl;

/**
 * Servlet implementation class RegController
 */
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewName = "/WEB-INF/view/admin/board/notice/reg.jsp";
		
		RequestDispatcher view = request.getRequestDispatcher(viewName);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step #1. get request parameters
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String files = request.getParameter("file");
		String isOpen = request.getParameter("open");
		
		// step #2. data processing
		NoticeService noticeService = new NoticeServiceImpl();
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setWriterId("admin");
		notice.setContent(content);
		notice.setFiles(files == null ? "" : files);
		notice.setPub(isOpen == null ? false : true);
		
		int result = noticeService.insertNotice(notice);
		
		// step #3. output processing results
		response.sendRedirect("list");
	}

}
