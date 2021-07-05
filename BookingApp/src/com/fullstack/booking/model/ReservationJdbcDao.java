package com.fullstack.booking.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class ReservationJdbcDao implements ReservationDao {
	private String driver;
	private String url;
	private String userName;
	private String password;
	
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	private static final String GET_RSV = "select * from RESERVATION where rsv_seq = ?";
	private static final String GET_RSVS = "select * from RESERVATION where ";
	private static final String INSERT_RSV = 
		"insert into RESERVATION(rsv_seq, name, passwd, phone, rsv_date, seat_numbers) "
			+ "values (rsv_seq_gen.nextval, ?, ?, ?, SYSTIMESTAMP, ?)";
	private static final String UPDATE_RSV = 
		"update RESERVATION set name = ?, passwd = ?, phone = ?, seat_numbers = ? where rsv_seq = ?";
	private static final String DELETE_RSV = "delete from RESERVATION where rsv_seq = ?";
	private static final String RSV_SEQ_CURRVAL = "select RSV_SEQ_GEN.currval from dual";
		
	public ReservationJdbcDao() {
		// TODO Auto-generated constructor stub
	}
	
	public ReservationJdbcDao(String driver, String url, String userName, String password) {
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, userName, password);
	}
	
	void disconnect() throws SQLException {
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

	@Override
	public Reservation getReservation(int rsvSeq) {
		Reservation rsv = null;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(GET_RSV);
			stmt.setInt(1, rsvSeq);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				rsv = new Reservation();
				rsv.setRsvSeq(rs.getInt("rsv_seq"));
				rsv.setName(rs.getString("name"));
				rsv.setPasswd(rs.getString("passwd"));
				rsv.setPhone(rs.getString("phone"));
				rsv.setRsvDate(rs.getDate("rsv_date"));
				rsv.setSeatNumbers((new Gson()).fromJson(rs.getString("seat_numbers"), int[].class));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rsv;
	}
	
	@Override
	public List<Reservation> getReservations(String query) {
		List<Reservation> rsvList = null;
		
		try {
			connect();
			
			String sql = GET_RSVS + query;
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			if (rs.isBeforeFirst()) {
				rsvList = new ArrayList<Reservation>();
			
				while (rs.next()) {
					Reservation rsv = new Reservation();
					rsv.setRsvSeq(rs.getInt("rsv_seq"));
					rsv.setName(rs.getString("name"));
					rsv.setPasswd(rs.getString("passwd"));
					rsv.setPhone(rs.getString("phone"));
					rsv.setRsvDate(rs.getDate("rsv_date"));
					rsv.setSeatNumbers((new Gson()).fromJson(rs.getString("seat_numbers"), int[].class));
					
					rsvList.add(rsv);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rsvList;
	}

	@Override
	public int insertReservation(Reservation rsv) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(INSERT_RSV);
			stmt.setString(1, rsv.getName());
			stmt.setString(2, rsv.getPasswd());
			stmt.setString(3, rsv.getPhone());
			stmt.setString(4, (new Gson()).toJson(rsv.getSeatNumbers()));
			
			result = stmt.executeUpdate();
			
			if (result > 0) {
				stmt = conn.prepareStatement(RSV_SEQ_CURRVAL);
				
				rs = stmt.executeQuery();
				if (rs.next()) {
					result = rs.getInt("CURRVAL");
				}
				else {
					result = -1;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
	public int updateReservation(Reservation rsv) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(UPDATE_RSV);
			stmt.setString(1, rsv.getName());
			stmt.setString(2, rsv.getPasswd());
			stmt.setString(3, rsv.getPhone());
			stmt.setString(4, (new Gson()).toJson(rsv.getSeatNumbers()));
			stmt.setInt(5, rsv.getRsvSeq());
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
	public int deleteReservation(Reservation rsv) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(DELETE_RSV);
			stmt.setInt(1, rsv.getRsvSeq());
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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

}
