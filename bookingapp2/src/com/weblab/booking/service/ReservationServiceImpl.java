package com.weblab.booking.service;

import java.util.List;

import com.weblab.booking.dao.ReservationDao;
import com.weblab.booking.dao.SeatDao;
import com.weblab.booking.entity.Reservation;
import com.weblab.booking.entity.Seat;

public class ReservationServiceImpl implements ReservationService {
	private ReservationDao rsvDao;
	private SeatDao seatDao;
	
	public ReservationServiceImpl(ReservationDao rsvDao, SeatDao seatDao) {
		this.rsvDao = rsvDao;
		this.seatDao = seatDao;
	}

	@Override
	public Reservation getReservation(int rsvSeq) {
		return rsvDao.getReservation(rsvSeq);
	}

	@Override
	public List<Reservation> getReservations(String name, String phone) {
		return rsvDao.getReservations(
			String.format("NAME='%s' and PHONE='%s'", name, phone));
	}

	@Override
	public int registerReservation(Reservation rsv) {
		int result = 0;
		
		int rsvSeq = rsvDao.insertReservation(rsv);
		
		result = seatDao.updateSeat(rsv.getSeatNumbers(), rsvSeq);
		
		return result;
	}

	@Override
	public int updateReservation(Reservation rsv) {
		int result = 0;		
		int rsvSeq = rsv.getRsvSeq();
		
		Reservation prevRsv = rsvDao.getReservation(rsvSeq);
		seatDao.updateSeat(prevRsv.getSeatNumbers(), 0);
		
		result = rsvDao.updateReservation(rsv);	
		result = seatDao.updateSeat(rsv.getSeatNumbers(), rsvSeq);
		
		return result;	
	}

	@Override
	public int deleteReservation(Reservation rsv) {
		int result = 0;
		
		result = rsvDao.deleteReservation(rsv.getRsvSeq());
		if (result == 1) {
			result = seatDao.updateSeat(rsv.getSeatNumbers(), 0);
		}
		
		return result;
	}

	@Override
	public List<Integer> getSeatNumbers(boolean booked) {
		return seatDao.getSeatNumbers(booked);
	}

}
