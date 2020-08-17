package com.reservation.bus.busReservation.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reservation.bus.busReservation.entity.BusApp;


public interface BusAppRepo extends JpaRepository<BusApp,Integer> {
	@Query("select busNumber,operatorName,departureTime,arrivalTime,duration,price,totalSeats FROM BusApp  b WHERE b.travelDate=?1 AND b.sourceCity=?2 AND b.destinationCity=?3")
	List<Object[]> findBysourceCityandDestinationCityandtravelDate(LocalDate travelDate, String sourceCity,
			String destinationCity);
	@Query("select totalSeats FROM BusApp  b WHERE b.busNumber=?1")
	int findByTotalSeat(int busNo);
	

}
