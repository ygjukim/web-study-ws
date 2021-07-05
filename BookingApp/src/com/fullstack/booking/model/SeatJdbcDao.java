package com.fullstack.booking.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatJdbcDao implements SeatDao {
	private String driver;
	private String url;
	private String userName;
	private String password;
	
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	private static final String GET_SEAT = "select * from SEAT where seat_number = ?";
	private static final String GET_SEAT_COUNT = "select COUNT(*) count from SEAT where seat_type = ? and rsv_seq = 0";
	private static final String GET_SEATS = "select * from SEAT";
	private static final String INSERT_SEAT = "insert into SEAT(seat_number, seat_type, rsv_seq) values (?, ?, ?)";
	private static final String UPDATE_SEAT = "update SEAT set seat_type = ?, rsv_seq = ? where seat_number = ?";
	private static final String DELETE_SEAT = "delete from SEAT where seat_number = ?";
	
	public SeatJdbcDao() {
		// TODO Auto-generated constructor stub
	}	

	public SeatJdbcDao(String driver, String url, String userName, String password) {
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
	public Seat getSeat(int number) {
		Seat seat = null;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(GET_SEAT);
			stmt.setInt(1, number);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				seat = new Seat();
				seat.setNumber(rs.getInt("seat_number"));
				seat.setType(rs.getString("seat_type"));
				seat.setRsvSeq(rs.getInt("rsv_seq"));
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
		
		return seat;
	}

	@Override
	public int getSeatCount(String type) {
		int count = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(GET_SEAT_COUNT);
			stmt.setString(1, type);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("count");
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
		
		return count;
	}

	@Override
	public List<Seat> getSeats() {
		List<Seat> seats = null;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(GET_SEATS);
			
			rs = stmt.executeQuery();
			
			if (rs.isBeforeFirst()) {
				seats = new ArrayList<Seat>();
				while (rs.next()) {
					Seat seat = new Seat();
					seat.setNumber(rs.getInt("seat_number"));
					seat.setType(rs.getString("seat_type"));
					seat.setRsvSeq(rs.getInt("rsv_seq"));
					
					seats.add(seat);
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
		
		return seats;
	}

	@Override
	public int insertSeat(Seat seat) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(INSERT_SEAT);
			stmt.setInt(1, seat.getNumber());
			stmt.setString(2, seat.getType());
			stmt.setInt(3, seat.getRsvSeq());
			
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
	public int updateSeat(Seat seat) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(UPDATE_SEAT);
			stmt.setString(1, seat.getType());
			stmt.setInt(2, seat.getRsvSeq());
			stmt.setInt(3, seat.getNumber());
			
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
	public int deleteSeat(Seat seat) {
		int result = -1;
		
		try {
			connect();
			
			stmt = conn.prepareStatement(DELETE_SEAT);
			stmt.setInt(1, seat.getNumber());
			
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
