package com.reservation.bus.busReservation.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.reservation.bus.busReservation.entity.BookingSeats;
import com.reservation.bus.busReservation.entity.BusApp;
import com.reservation.bus.busReservation.entity.BusSeats;
import com.reservation.bus.busReservation.entity.User;
import com.reservation.bus.busReservation.repository.BookingSeatsRepo;
import com.reservation.bus.busReservation.repository.BusAppRepo;
import com.reservation.bus.busReservation.repository.BusSeatsRepo;
import com.reservation.bus.busReservation.repository.UserRepo;
import com.reservation.bus.busReservation.service.BusReservationService;


@Service
public class BusReservationServiceImpl implements BusReservationService{
    @Autowired
    BusAppRepo busAppRepo;
    @Autowired
    BusSeatsRepo busSeatRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
	BookingSeatsRepo bookingRepo;
	@Override
	public List<BusApp> getBusDetails() {
			List<BusApp> busDetailsList = busAppRepo.findAll();
			busDetailsList.stream().sorted((p1,p2)->p1.getPrice()-p2.getPrice()).forEach(x -> System.out.println(x));
			return busDetailsList;
	}
	
	@Override
	public List<Object[]> searchByDate(LocalDate travelDate, String sourceCity, String destinationCity) {
		return busAppRepo.findBysourceCityandDestinationCityandtravelDate(travelDate,sourceCity,destinationCity);
	}
	@Override
	public List<BusApp> searchbyId(List<Object[]> searchList) {
		 List<Integer> ids = mapIds(searchList);
		 List<BusApp> list = null;
		    if(!ids.isEmpty()){
		    	 list=busAppRepo.findAllById(ids);
		    }
		return list;
	}
	private List<Integer> mapIds(List<Object[]> searchList) {
		 List<Integer> ids = new ArrayList<Integer>();
		    if (searchList != null && !searchList.isEmpty()) {
		        Integer id;
		        for (Object[] object : searchList) {
		            id = (object[0] != null ? (Integer) object[0] : null);
		            ids.add(id);
		        }
		    }
		return ids;
	}
	@Override
	public List<BusApp> sortByOperatorName(List<BusApp> list) {
		Collections.sort(list, new Comparator<BusApp>() {
	           public int compare(BusApp p1, BusApp p2) {
	        	   int operatorName=p1.getOperatorName().compareTo(p2.getOperatorName());
		            return operatorName;
	  }
		});
		
          return list;
	}
	@Override
	public List<BusApp> sortByArrivalTime(List<BusApp> list) {
		Collections.sort(list, new Comparator<BusApp>() {
	           public int compare(BusApp p1, BusApp p2) {
	        	   int arrivalTime= p1.getArrivalTime().compareTo(p2.getArrivalTime());
		           return arrivalTime;
	     }
		   });
		return list;
	}
	@Override
	public List<BusApp> sortByDepartureTime(List<BusApp> list) {
		Collections.sort(list, new Comparator<BusApp>() {
	           public int compare(BusApp p1, BusApp p2) {
        	       int departureTime=p1.getDepartureTime().compareTo(p2.getDepartureTime());
		            return departureTime;
	    }
		   });
		return list;
	}
	@Override
	public List<BusApp> sortByDuration(List<BusApp> list) {
		Collections.sort(list, new Comparator<BusApp>() {
	           public int compare(BusApp p1, BusApp p2) {
	        	   int durOne=(int) (p1.getDuration()-p2.getDuration());
		           return durOne;
	    }
		   });
		return list;
	}
	@Override
	public void booking(int seatId, int busNo, String user) {
		User username=new User(user);
		userRepo.save(username);
        int totalSeats=busAppRepo.findByTotalSeat(busNo);
        if(seatId<totalSeats) {
        	List<BookingSeats>bookSeatsList=bookingRepo.findBySeatIdandBusNo(busNo,seatId);
        	  if(bookSeatsList.isEmpty()) {
        	    List<BusSeats> seatList=busSeatRepo.findBySeatIdAndBusNo(busNo,seatId);
        	      if(Objects.nonNull(seatList)) {
				   BookingSeats booking=new BookingSeats(seatId,busNo,username);
					bookingRepo.save(booking);
			       }   
                 }else {
       	           System.out.println("Already Booked");
		        }
               }else {
    	  System.out.println("No seats found with this id");
      }

	}
	@Override
	public void cancelBooking(int bookingId) {
		Optional<BookingSeats>bookSeatsList=bookingRepo.findById(bookingId);
		if(bookSeatsList.isPresent()) {
			bookingRepo.deleteById(bookingId);
		}else {
			 System.out.println("No bookings found with this id");
		}
	
	}
}
