package com.fullstack.booking.service;

import java.util.List;

import com.fullstack.booking.model.Reservation;
import com.fullstack.booking.model.ReservationDao;
import com.fullstack.booking.model.Seat;
import com.fullstack.booking.model.SeatDao;

public class ReservationServiceImpl implements ReservationService {
	private ReservationDao rsvDao = null;
	private SeatDao seatDao = null;
	
	public void setRsvDao(ReservationDao rsvDao) {
		this.rsvDao = rsvDao;
	}

	public void setSeatDao(SeatDao seatDao) {
		this.seatDao = seatDao;
	}

	@Override
	public Reservation getReservation(int rsvSeq) {
		return rsvDao.getReservation(rsvSeq);
	}

	@Override
	public List<Reservation> getReservations(String name, String phone) {
		return rsvDao.getReservations(String.format("name = '%s' and phone = '%s' order by rsv_seq desc", name, phone));
	}

	@Override
	public List<Reservation> getReservationList() {
		return rsvDao.getReservations("rsv_seq > 0");
	}

	@Override
	public int insertReservation(Reservation rsv) {
		int rsvSeq = rsvDao.insertReservation(rsv);

		int[] seatNumbers = rsv.getSeatNumbers();
		if (seatNumbers.length > 0) {
			for (int i=0; i<seatNumbers.length; i++) {
				Seat seat = seatDao.getSeat(seatNumbers[i]);
				seat.setRsvSeq(rsvSeq);
				seatDao.updateSeat(seat);
			}
		}
		
		return rsvSeq;
	}

	@Override
	public int updateReservation(Reservation rsv) {
		int result = rsvDao.updateReservation(rsv);
		
		int rsvSeq = rsv.getRsvSeq();
		int[] seatNumbers = rsv.getSeatNumbers();
		if (seatNumbers.length > 0) {
			for (int i=0; i<seatNumbers.length; i++) {
				Seat seat = seatDao.getSeat(seatNumbers[i]);
				if (seat.getRsvSeq() != rsvSeq) {
					seat.setRsvSeq(rsvSeq);
					seatDao.updateSeat(seat);
				}
			}
		}
		
		return result;
	}

	@Override
	public int deleteReservation(Reservation rsv) {
		int result = rsvDao.deleteReservation(rsv);
		
		int[] seatNumbers = rsv.getSeatNumbers();
		if (seatNumbers.length > 0) {
			for (int i=0; i<seatNumbers.length; i++) {
				Seat seat = seatDao.getSeat(seatNumbers[i]);
				seat.setRsvSeq(0);
				seatDao.updateSeat(seat);
			}
		}
		
		return result;
	}

	@Override
	public List<Seat> getAllSeats() {
		return seatDao.getSeats();
	}

}
