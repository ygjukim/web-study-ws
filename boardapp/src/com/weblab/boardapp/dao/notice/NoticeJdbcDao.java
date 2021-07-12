package com.weblab.boardapp.dao.notice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.weblab.boardapp.entity.notice.Notice;
import com.weblab.boardapp.entity.notice.NoticeView;

public class NoticeJdbcDao implements NoticeDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.35.68:1521/xepdb1";
	private String userName = "NEWLEC";
	private String password = "1234";
	
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public NoticeJdbcDao() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeJdbcDao(String driver, String url, String userName, String password) {
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

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
	
	public List<NoticeView> getNoticeViewList(String field, String query, int page) {
		List<NoticeView> list = null;
		
		String sql = "SELECT * FROM (" +
				" SELECT ROWNUM NUM, N.* FROM (" +
					" SELECT * FROM NOTICE_VIEW WHERE " + field + " LIKE ? ORDER BY REGDATE DESC ) N ) " +
				    " WHERE NUM BETWEEN ? AND ?";
		
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

	@Override
	public List<NoticeView> getNoticeViewPubList(String field, String query, int page) {
		List<NoticeView> list = null;
		
		String sql = "SELECT * FROM (" +
				" SELECT ROWNUM NUM, N.* FROM (" +
					" SELECT * FROM NOTICE_VIEW WHERE " + field + " LIKE ? ORDER BY REGDATE DESC ) N ) " +
				    " WHERE PUB = 1 AND NUM BETWEEN ? AND ?";
		
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

	@Override
	public int getNoticePubCount(String field, String query) {
		String sql = "SELECT COUNT(ID) COUNT FROM NOTICE " +
				" WHERE " + field + " LIKE ? AND PUB = 1";
		
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
		Notice notice = null;
		
		String sql = (id != 0) ? "SELECT * FROM NOTICE WHERE ID = ?" :
						"SELECT * FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) WHERE ROWNUM = 1";
		
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

	@Override
	public Notice getNextNotice(int id) {
		Notice notice = null;

		String sql = "SELECT ID FROM (SELECT ID, REGDATE FROM NOTICE ORDER BY REGDATE) " +
				"WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID = ?) AND ROWNUM = 1";
		String sql2 = "SELECT * FROM NOTICE WHERE ID = ?";
		
		int nextId = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				nextId = rs.getInt("ID");
			}
			
			rs.close();
			stmt.close();
			
			if (nextId > 0) {
				stmt = conn.prepareStatement(sql2);
				stmt.setInt(1, nextId);
				
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

	@Override
	public Notice getPrevNotice(int id) {
		Notice notice = null;

		String sql = "SELECT ID FROM (SELECT ID, REGDATE FROM NOTICE ORDER BY REGDATE DESC) " +
				"WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID = ?) AND ROWNUM = 1";
		String sql2 = "SELECT * FROM NOTICE WHERE ID = ?";
		
		int prevId = 0;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				prevId = rs.getInt("ID");
			}
			
			rs.close();
			stmt.close();
			
			if (prevId > 0) {
				stmt = conn.prepareStatement(sql2);
				stmt.setInt(1, prevId);
				
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

	public int updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateNoticePubs(int[] opnIds, int[] clsIds) {
		return updateNoticePubs(convertToCSV(opnIds), convertToCSV(clsIds));
	}

	@Override
	public int updateNoticePubs(String opnCSV, String clsCSV) {
		int result = 0;

		String sql = "UPDATE NOTICE SET PUB = ? WHERE ID IN (%s)";
		
		try {
			connect();
			
			stmt = conn.prepareStatement(String.format(sql,  opnCSV));
			stmt.setInt(1, 1);
			result += stmt.executeUpdate();
			stmt.close();
			
			stmt = conn.prepareStatement(String.format(sql,  clsCSV));
			stmt.setInt(1, 0);
			result += stmt.executeUpdate();			
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

	public int deleteNotice(int id) {
		int result = 0;
		
		String sql = "DELETE FROM NOTICE WHERE ID = ?";
		
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
	public int deleteNotices(int[] ids) {
		int result = 0;
		
		String sql = String.format("DELETE FROM NOTICE WHERE ID IN (%s)", convertToCSV(ids));
		
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
	
	private String convertToCSV(int[] ids) {
		StringBuilder csv = new StringBuilder();

		for (int i=0; i<ids.length-1; i++) {
			csv.append(ids[i]).append(", ");
		}
		csv.append(ids[ids.length-1]);
		
		return csv.toString();
	}

}
