package com.weblab.boardapp.service.notice;

import java.util.List;

import com.weblab.boardapp.entity.Notice;
import com.weblab.boardapp.entity.NoticeView;

public interface NoticeService {

	List<NoticeView> getNoticeViewList();
	
	List<NoticeView> getNoticeViewList(int page);

	List<NoticeView> getNoticeViewList(String field, String query, int page);
	
	int getNoticeCount();
	
	int getNoticeCount(String field, String query);
	
	Notice getNotice(int id);
	
	Notice getPrevNotice(int id);
	
	Notice getNextNotice(int id);
	
	List<Notice> getNoticeNewestList();
	
	int insertNotice(Notice notice);

	int updateNotice(Notice notice);

	int deleteNotice(int id);
	
	int deleteNoticeAll(int[] ids);
	
	int pubNoticeAll(int[] ids);
}
