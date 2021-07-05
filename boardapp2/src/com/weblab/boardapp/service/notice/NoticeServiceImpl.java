package com.weblab.boardapp.service.notice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.weblab.boardapp.entity.Notice;
import com.weblab.boardapp.entity.NoticeView;

public class NoticeServiceImpl implements NoticeService {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.35.68:1521/xepdb1";
	private String userName = "NEWLEC";
	private String password = "1234";
	
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, userName, password);
	}
	
	public void disconnect() throws SQLException {
		if (rs != null && !rs.isClosed()) {
			rs.close();
			rs = null;
		}
		if (stmt != null && !stmt.isClosed()) {
			stmt.close();
			stmt = null;
		}
		if (conn != null && !conn.isClosed()) {
			conn.close();
			conn = null;
		}
	}

	public List<NoticeView> getNoticeViewList() {
		return getNoticeViewList("TITLE", "", 1);
	}

	public List<NoticeView> getNoticeViewList(int page) {
		return getNoticeViewList("TITLE", "", page);
	}

	public List<NoticeView> getNoticeViewList(String field, String query, int page) {
		String sql = "SELECT * FROM (" +
				" SELECT ROWNUM NUM, N.* FROM (" +
					" SELECT * FROM NOTICE_VIEW WHERE " + field + " LIKE ? ORDER BY REGDATE DESC ) N ) " +
				    " WHERE NUM BETWEEN ? AND ?";
		
		List<NoticeView> list = null;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+query+"%");
			stmt.setInt(2, (page-1)*10+1);
			stmt.setInt(3, page*10);
			
			rs = stmt.executeQuery();
			
			if (rs.isBeforeFirst()) {
				list = new ArrayList<NoticeView>();
				while (rs.next()) {
					NoticeView notice = new NoticeView();
					notice.setId(rs.getInt("ID"));
					notice.setTitle(rs.getString("TITLE"));
					notice.setWriterId(rs.getString("WRITER_ID"));
					notice.setRegDate(rs.getDate("REGDATE"));
					notice.setHit(rs.getInt("HIT"));
					notice.setFiles(rs.getString("FILES"));
					notice.setPub(rs.getBoolean("PUB"));
					notice.setCmtCount(rs.getInt("CMT_COUNT"));
					list.add(notice);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public int getNoticeCount() {
		return getNoticeCount("TITLE", "");
	}

	public int getNoticeCount(String field, String query) {
		String sql = "SELECT COUNT(ID) COUNT FROM NOTICE " +
				" WHERE " + field + " LIKE ?";
		
		int count = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+query+"%");
			
			rs = stmt.executeQuery();
			

			if (rs.next()) {
				count = rs.getInt("COUNT");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return count;
	}

	public Notice getNotice(int id) {
		String sql = "SELECT * FROM NOTICE WHERE ID = ?";
		
		Notice notice = null;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getInt("ID"));
				notice.setTitle(rs.getString("TITLE"));
				notice.setWriterId(rs.getString("WRITER_ID"));
				notice.setContent(rs.getString("CONTENT"));
				notice.setRegDate(rs.getDate("REGDATE"));
				notice.setHit(rs.getInt("HIT"));
				notice.setFiles(rs.getString("FILES"));
				notice.setPub(rs.getBoolean("PUB"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return notice;
	}

	public Notice getNextNotice(int id) {
		String sql = "SELECT ID FROM NOTICE " +
				"WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID = ?) " +
				"AND ROWNUM = 1";
		
		int nextId = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				nextId = rs.getInt("COUNT");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Notice notice = null;
		if (nextId != 0) {
			notice = getNotice(nextId);
		}
		
		return notice;
	}

	public Notice getPrevNotice(int id) {
		String sql = "SELECT ID FROM (SELECT ID, REGDATE FROM NOTICE ORDER BY REGDATE DESC) " +
				"WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID = ?) " +
				"AND ROWNUM = 1";
		
		int prevId = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				prevId = rs.getInt("COUNT");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Notice notice = null;
		if (prevId != 0) {
			notice = getNotice(prevId);
		}
		
		return notice;
	}

	@Override
	public int insertNotice(Notice notice) {
		String sql = "INSERT INTO NOTICE (TITLE, WRITER_ID, CONTENT, FILES, PUB) " +
				"VALUES (?, ?, ?, ?, ?) ";
		
		int result = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, notice.getTitle());
			stmt.setString(2, notice.getWriterId());
			stmt.setString(3, notice.getContent());
			stmt.setString(4, notice.getFiles());
			stmt.setBoolean(5, notice.getPub());
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public int updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteNotice(int id) {
		String sql = "DELETE FROM NOTICE WHERE ID = ?";
		
		int result = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public List<Notice> getNoticeNewestList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteNoticeAll(int[] ids) {
		String params = "";
		for (int i=0; i<ids.length; i++) {
			params += ids[i];
			if (i < ids.length-1) {
				params += ",";
			}
		}
		
		String sql = "DELETE FROM NOTICE WHERE ID in (" + params + ")";
		
		int result = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public int pubNoticeAll(int[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
