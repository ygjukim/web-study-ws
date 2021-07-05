package com.fullstack.booking.model;

import java.util.List;

public interface ReservationDao {

	Reservation getReservation(int rsvSeq);
	
	List<Reservation> getReservations(String query);

	int insertReservation(Reservation rsv);
	
	int updateReservation(Reservation rsv);
	
	int deleteReservation(Reservation rsv);
	
}
