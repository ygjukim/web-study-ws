package com.weblab.booking.dao;

import java.util.List;

import com.weblab.booking.entity.Reservation;

public interface ReservationDao {

	Reservation getReservation(int rsvSeq);

	List<Reservation> getReservations(String query);
	
	int insertReservation(Reservation rsv);
	
	int updateReservation(Reservation rsv);

	int deleteReservation(int rsvSeq);
	
}
