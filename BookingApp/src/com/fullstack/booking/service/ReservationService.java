package com.fullstack.booking.service;

import java.util.List;

import com.fullstack.booking.model.Reservation;
import com.fullstack.booking.model.Seat;

public interface ReservationService {

	Reservation getReservation(int rsvSeq);
	
	List<Reservation> getReservations(String name, String phone);

	List<Reservation> getReservationList();
	
	int insertReservation(Reservation rsv);
	
	int updateReservation(Reservation rsv);
	
	int deleteReservation(Reservation rsv);
	
	List<Seat> getAllSeats();
	
}
