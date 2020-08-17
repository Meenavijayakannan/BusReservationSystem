package com.reservation.bus.busReservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservation.bus.busReservation.entity.BookingSeats;


public interface BookingSeatsRepo extends JpaRepository<BookingSeats,Integer> {

	@Query("from BookingSeats where seatId=?2 and busNumber=?1")
	List<BookingSeats> findBySeatIdandBusNo(int busNo, int seatId);

}
