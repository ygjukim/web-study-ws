package com.weblab.boardapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weblab.boardapp.dao.notice.NoticeDao;
import com.weblab.boardapp.dao.notice.NoticeJdbcDao;
import com.weblab.boardapp.service.notice.NoticeService;
import com.weblab.boardapp.service.notice.NoticeServiceImpl;

@WebListener
public class AppServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextInitialized(sce);
		
		ServletContext context = sce.getServletContext();
		String driver = context.getInitParameter("jdbc_driver");
		String url = context.getInitParameter("db_url");
		String userName = context.getInitParameter("db_userid");
		String password = context.getInitParameter("db_passwd");
		
		NoticeDao noticeDao = new NoticeJdbcDao(driver, url, userName, password);
		NoticeService noticeService = new NoticeServiceImpl(noticeDao);
		
		context.setAttribute("notice_service", noticeService);
	}

}
