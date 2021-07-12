package com.weblab.boardapp.service.notice;

import java.util.List;

import com.weblab.boardapp.dao.notice.NoticeDao;
import com.weblab.boardapp.entity.notice.Notice;
import com.weblab.boardapp.entity.notice.NoticeView;

public class NoticeServiceImpl implements NoticeService {
	
	private NoticeDao noticeDao = null;
	
	public NoticeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeServiceImpl(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public List<NoticeView> getNoticeViewList() {
		return noticeDao.getNoticeViewList("TITLE", "", 1);
	}

	@Override
	public List<NoticeView> getNoticeViewList(int page) {
		return noticeDao.getNoticeViewList("TITLE", "", page);
	}

	@Override
	public List<NoticeView> getNoticeViewList(String field, String query, int page) {
		return noticeDao.getNoticeViewList(field, query, page);
	}

	@Override
	public int getNoticeCount() {
		return noticeDao.getNoticeCount("TITLE", "");
	}

	@Override
	public int getNoticeCount(String field, String query) {
		return noticeDao.getNoticeCount(field, query);
	}

	@Override
	public Notice getNotice(int id) {
		return noticeDao.getNotice(id);
	}

	@Override
	public Notice getPrevNotice(int id) {
		return noticeDao.getPrevNotice(id);
	}

	@Override
	public Notice getNextNotice(int id) {
		return noticeDao.getNextNotice(id);
	}

	@Override
	public List<Notice> getNoticeNewestList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertNotice(Notice notice) {
		return noticeDao.insertNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return noticeDao.updateNotice(notice);
	}

	@Override
	public int deleteNotice(int id) {
		return noticeDao.deleteNotice(id);
	}

	@Override
	public int deleteNoticeAll(int[] ids) {
		return noticeDao.deleteNotices(ids);
	}

	@Override
	public int pubNoticeAll(int[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
