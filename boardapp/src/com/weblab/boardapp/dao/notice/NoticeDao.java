package com.weblab.boardapp.dao.notice;

import java.util.List;

import com.weblab.boardapp.entity.notice.Notice;
import com.weblab.boardapp.entity.notice.NoticeView;

public interface NoticeDao {

	List<NoticeView> getNoticeViewList(String field, String query, int page);
	
	List<NoticeView> getNoticeViewPubList(String field, String query, int page);

	int getNoticeCount(String field, String query);
	
	int getNoticePubCount(String field, String query);

	Notice getNotice(int id);

	Notice getNextNotice(int id);
	
	Notice getPrevNotice(int id);

	int insertNotice(Notice notice);
	
	int updateNotice(Notice notice);

	int deleteNotice(int id);
	
	int deleteNotices(int[] ids);
	
	int updateNoticePubs(int[] opnIds, int[] clsIds);

	int updateNoticePubs(String opnCSV, String clsCSV);
	
}
