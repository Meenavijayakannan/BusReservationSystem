package com.reservation.bus.busReservation.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.reservation.bus.busReservation.entity.BusApp;



public interface BusReservationService {

	List<BusApp> getBusDetails();

	List<Object[]> searchByDate(LocalDate travelDate, String sourceCity, String destinationCity);

	List<BusApp> searchbyId(List<Object[]> searchList);

	List<BusApp> sortByOperatorName(List<BusApp> list);

	List<BusApp> sortByArrivalTime(List<BusApp> list);

	List<BusApp> sortByDepartureTime(List<BusApp> list);

	List<BusApp> sortByDuration(List<BusApp> list);

	void booking(int seatId,int busNo,String user );

    void cancelBooking(int bookingId);

	


}
