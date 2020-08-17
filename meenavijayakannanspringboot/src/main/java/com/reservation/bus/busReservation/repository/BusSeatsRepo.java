package com.reservation.bus.busReservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservation.bus.busReservation.entity.BusSeats;


public interface BusSeatsRepo extends JpaRepository<BusSeats,Integer> {

	@Query("from BusSeats where seat_id=:seatId and bid=:bid")
	List<BusSeats> findBySeatIdAndBusNo(int bid, int seatId);

}
