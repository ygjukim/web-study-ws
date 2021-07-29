package com.weblab.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.weblab.booking.entity.Seat;
import com.weblab.booking.exception.IllegalReservationSequenceException;

@Component("seatSpringJdbcDao")
public class SeatSpringJdbcDao implements SeatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Override
	public Seat getSeat(int number) {
		String sql = "SELECT * FROM SEAT WHERE SEAT_NUMBER = ?";
		return jdbcTemplate.queryForObject(sql, new SeatRowMapper(), number);
	}

	@Override
	public List<Seat> getSeats(String query) {
		String sql = "SELECT * FROM SEAT";
		sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY SEAT_NUMBER");		
		return jdbcTemplate.query(sql, new SeatRowMapper());
	}

	@Override
	public int insertSeat(Seat seat) {
		String sql = "INSERT INTO SEAT (SEAT_NUMBER, SEAT_TYPE, RSV_SEQ) VALUES (?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sql, new String[] {"SEAT_NUMBER"});
				stmt.setInt(1, seat.getNumber());
				stmt.setString(2, seat.getType());
				stmt.setInt(3, seat.getRsvSeq());
				return stmt;
			}
		}, keyHolder);
		
		Number keyValue = keyHolder.getKey();
		return keyValue.intValue();
	}

	@Override
	public int updateSeat(Seat seat) {
		String sql = "UPDATE SEAT SET SEAT_TYPE = ?, RSV_SEQ = ? WHERE SEAT_NUMBER = ?";
		return jdbcTemplate.update(sql, seat.getType(), seat.getRsvSeq(), seat.getNumber());
	}

	@Override
	public int deleteSeat(int number) {
		String sql = "DELETE FROM SEAT WHERE SEAT_NUMBER = ?";
		return jdbcTemplate.update(sql, number);
	}

	@Override
	public int updateSeat(int[] seats, int rsvSeq) {
		if (rsvSeq < 0) {
			throw new IllegalReservationSequenceException();
		}
		
		String numList = "";
		for (int i=0; i<seats.length-1; i++ ) {
			numList += seats[i] + ", ";
		}
		numList += seats[seats.length-1];
		
		String sql = "UPDATE SEAT SET RSV_SEQ = ? WHERE SEAT_NUMBER IN (" + numList + ")";
		return jdbcTemplate.update(sql, rsvSeq);
	}

	@Override
	public List<Integer> getSeatNumbers(boolean booked) {
		String sql = "SELECT SEAT_NUMBER FROM SEAT WHERE RSV_SEQ " + (booked ? ">" : "=") + " 0";
		return jdbcTemplate.queryForList(sql, Integer.class);
	}

	private static class SeatRowMapper implements RowMapper<Seat> {

		@Override
		public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
			Seat seat = new Seat();
			seat.setNumber(rs.getInt("SEAT_NUMBER"));
			seat.setType(rs.getString("SEAT_TYPE"));
			seat.setRsvSeq(rs.getInt("RSV_SEQ"));
			return seat;
		}
		
	}
}
