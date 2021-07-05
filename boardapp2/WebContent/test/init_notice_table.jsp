<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.weblab.boardapp.entity.*, com.weblab.boardapp.service.notice.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	NoticeService noticeService = new NoticeServiceImpl();

	for (int i=1; i<=75; i++) {
		Notice notice = new Notice();
		notice.setTitle("테스트글 제목 #" + i);
		notice.setContent("테스트 글입니다....<br>글 번호 #" + i);
		notice.setWriterId("admin");
		notice.setFiles("");
		notice.setPub(false);
		
		noticeService.insertNotice(notice);
	}
%>
</body>
</html>