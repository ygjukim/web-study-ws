package com.fullstack.booking.model;

import java.util.List;

public interface SeatDao {
	
	Seat getSeat(int number);
	
	int getSeatCount(String type);
	
	List<Seat> getSeats();
	
	int insertSeat(Seat seat);
	
	int updateSeat(Seat seat);

	int deleteSeat(Seat seat);
	
}
