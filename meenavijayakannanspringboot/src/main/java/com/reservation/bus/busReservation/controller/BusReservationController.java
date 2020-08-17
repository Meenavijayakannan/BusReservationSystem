package com.reservation.bus.busReservation.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.bus.busReservation.entity.BusApp;
import com.reservation.bus.busReservation.service.BusReservationService;


@RestController
public class BusReservationController {
	
	@Autowired
	BusReservationService busService;
	

	@RequestMapping("/getPage")
	public String home() {
		return "Welcome home page";
	}
	
	@GetMapping("/getBusDetails")
	public List<BusApp> getBusDetails() {
		List<BusApp> list= busService.getBusDetails();
		 return list;
	}
	@GetMapping("/searchbydate")
	public List<Object[]> searchByDate(@RequestParam(value="travelDate")@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate travelDate,@RequestParam(value="sourceCity")String sourceCity,@RequestParam(value="destinationCity") String destinationCity) {
		List<Object[]> searchList=  busService.searchByDate(travelDate,sourceCity,destinationCity);
		if(Objects.nonNull(searchList)) {
			 List<BusApp> list= busService.searchbyId(searchList);
			 if(Objects.nonNull(list)) {
			 List<BusApp> sortByOperatorName=busService.sortByOperatorName(list);
			 sortByOperatorName.stream().forEach(action->System.out.println(action));
			 List<BusApp> sortByArrivalTime=busService.sortByArrivalTime(list);
			 sortByArrivalTime.stream().forEach(action->System.out.println(action));
			 List<BusApp> sortByDepartureTime=busService.sortByDepartureTime(list);
			 sortByDepartureTime.stream().forEach(action->System.out.println(action));
			 List<BusApp> sortByDuration=busService.sortByDuration(list);
			 sortByDuration.stream().forEach(action->System.out.println(action));
			 }
		}
		return searchList;
	}
	@PostMapping("/booking")
	public void Booking(@RequestParam(value="busNumber") int busNo,@RequestParam(value="user")String user,@RequestParam(value="seatId") int seatId) {
		busService.booking(seatId,busNo,user);
	}
	@DeleteMapping("/cancelBooking")
	public void CancelBooking(@RequestParam(value="bookingId") int bookingId) {
		   busService.cancelBooking(bookingId);
	}
	
}
