package com.weblab.booking.dao;

import java.util.List;

import com.weblab.booking.entity.Seat;

public interface SeatDao {
	
	// CRUD(Create, Retrieve, Update, Delete)
	
	Seat getSeat(int number);
	
	List<Seat> getSeats(String query);
	
	int insertSeat(Seat seat);
	
	int updateSeat(Seat seat);
	
	int updateSeat(int[] seats, int rsvSeq);

	int deleteSeat(int number);
	
	List<Integer> getSeatNumbers(boolean booked);

}
