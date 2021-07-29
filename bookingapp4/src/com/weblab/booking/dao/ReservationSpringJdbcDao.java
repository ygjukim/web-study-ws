package com.weblab.booking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.weblab.booking.entity.Reservation;

@Component("rsvSpringJdbcDao")
public class ReservationSpringJdbcDao implements ReservationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
			
	@Override
	public Reservation getReservation(int rsvSeq) {
		String sql = "SELECT * FROM RESERVATION WHERE RSV_SEQ = ?";
		return jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), rsvSeq);
	}

	@Override
	public List<Reservation> getReservations(String query) {
		String sql = "SELECT * FROM RESERVATION";
		sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY RSV_SEQ DESC");
		return jdbcTemplate.query(sql, new ReservationRowMapper());
	}

	@Override
	public int insertReservation(Reservation rsv) {
		String sql = "INSERT INTO RESERVATION (NAME, PASSWD, PHONE, SEAT_NUMBER) VALUES (?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sql, new String[] {"RSV_SEQ"});
				stmt.setString(1, rsv.getName());
				stmt.setString(2, rsv.getPasswd());
				stmt.setString(3, rsv.getPhone());
				stmt.setString(4, (new Gson()).toJson(rsv.getSeatNumbers()));				
				return stmt;
			}
		}, keyHolder);
		
		Number keyValue = keyHolder.getKey();
		return keyValue.intValue();
	}

	@Override
	public int updateReservation(Reservation rsv) {
		String sql = "UPDATE RESERVATION SET NAME=?, PASSWD=?, PHONE=?, SEAT_NUMBER=?, RSV_DATE = SYSTIMESTAMP WHERE RSV_SEQ = ?";
		
		Object[] args = new Object[] {
				rsv.getName(), rsv.getPasswd(), rsv.getPhone(), 
				(new Gson()).toJson(rsv.getSeatNumbers()), 
				rsv.getRsvSeq() 
		};
		
		return jdbcTemplate.update(sql, args);
	}

	@Override
	public int deleteReservation(int rsvSeq) {
		String sql = "DELETE FROM RESERVATION WHERE RSV_SEQ = ?";
		return jdbcTemplate.update(sql, rsvSeq);
	}
	
	private static class ReservationRowMapper implements RowMapper<Reservation> {

		@Override
		public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation rsv = new Reservation();
			rsv.setRsvSeq(rs.getInt("RSV_SEQ"));
			rsv.setName(rs.getString("NAME"));
			rsv.setPasswd(rs.getString("PASSWD"));
			rsv.setPhone(rs.getString("PHONE"));
			rsv.setRsvDate(rs.getDate("RSV_DATE"));
			rsv.setSeatNumbers((new Gson()).fromJson(rs.getString("SEAT_NUMBER"), int[].class));
			return rsv;
		}
		
	}

}
