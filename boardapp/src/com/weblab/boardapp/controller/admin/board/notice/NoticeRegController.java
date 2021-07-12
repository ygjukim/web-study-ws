package com.weblab.boardapp.controller.admin.board.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.weblab.boardapp.entity.notice.Notice;
import com.weblab.boardapp.service.notice.NoticeService;

/**
 * Servlet implementation class NoticeRegController
 */
@MultipartConfig(
	fileSizeThreshold=1024*1024,
	maxFileSize=1024*1024*50,
	maxRequestSize=1024*1024*50*5
)
@WebServlet("/admin/board/notice/reg")
public class NoticeRegController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeRegController() {
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
		String isOpen = request.getParameter("open");
		
		boolean pub = (isOpen == null) ? false : true;
	
// Single file uploading
/*		
		Part filePart = request.getPart("file");
		InputStream fis = filePart.getInputStream();
		
		String filePath = getServletContext().getRealPath("/upload")
							+ File.separator + filePart.getSubmittedFileName();
		FileOutputStream fos = new FileOutputStream(filePath); 
		
		byte[] buffer = new byte[1024];
		int size = 0;
		while ((size = fis.read(buffer)) != -1) {
			fos.write(buffer, 0, size);
		}
		
		fos.close();
		fis.close();
*/
		
// Multiple file uploading
		StringBuilder builder = new StringBuilder();
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			if (!part.getName().equals("file"))  continue;
			if (part.getSize() == 0)  continue;
			
			InputStream fis = part.getInputStream();
			builder.append(part.getSubmittedFileName()).append(",");
			
			String uploadPath = getServletContext().getRealPath("/upload");
			File path = new File(uploadPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			
			String filePath = uploadPath + File.separator + part.getSubmittedFileName();
//			System.out.println(filePath);
			FileOutputStream fos = new FileOutputStream(filePath); 
			
			byte[] buffer = new byte[1024];
			int size = 0;
			while ((size = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, size);
			}
			
			fos.close();
			fis.close();
		}
		
		int fnLength = builder.length();
		if (fnLength > 0) {
			builder.delete(fnLength-1, fnLength);	// delete last ',' char
		}
		
		// step #2. data processing
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("admin");
		notice.setFiles(builder.toString());
		
		NoticeService noticeService = (NoticeService)getServletContext().getAttribute("notice_service");		
		noticeService.insertNotice(notice);
		
		// step #3. output processing results
		response.sendRedirect("list");
	}

}
