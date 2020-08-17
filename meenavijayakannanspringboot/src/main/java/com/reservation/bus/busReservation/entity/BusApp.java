package com.reservation.bus.busReservation.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
public class BusApp {
	@Id
	private int busNumber;
	@Column(nullable=false)
	private String sourceCity;
	@Column(nullable=false)
	private String destinationCity;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(nullable=false)
	private LocalDate travelDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate returnDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd hh:mm:ss a")
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss a")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime departureTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd hh:mm:ss a")
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss a")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime arrivalTime;
	
	private String operatorName;
	
	private long duration;
	private int price;
	private int totalSeats;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="bid", referencedColumnName="busNumber")
	private List<BusSeats> seats = new ArrayList<BusSeats>();
	
	public List<BusSeats> getSeats() {
		return seats;
	}
	public void setSeats(List<BusSeats> seats) {
		this.seats = seats;
	}
	public int getBusNumber() {
		return busNumber;
	}
	public void setBusNumber(int busNumber) {
		this.busNumber = busNumber;
	}
	public String getSourceCity() {
		return sourceCity;
	}
	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public LocalDate getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public long getDuration() {
	     Duration dur=Duration.between(arrivalTime, departureTime);
	   	 return Math.abs(dur.toHours());
	}
	public void setDuration(long l) {
		 Duration dur=Duration.between(arrivalTime, departureTime);
		 this.duration= Math.abs(dur.toHours());
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public BusApp() {
		
	}
	
	public BusApp(int busNumber, String sourceCity, String destinationCity, LocalDate travelDate, LocalDate returnDate,
			 LocalDateTime arrivalTime,LocalDateTime departureTime, String operatorName, int price, int totalSeats) {
		this.busNumber = busNumber;
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		this.travelDate = travelDate;
		this.returnDate = returnDate;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.operatorName = operatorName;
		Duration dur=Duration.between(arrivalTime, departureTime);
		this.duration= Math.abs(dur.toHours());
		if (this.getSeats() != null && !this.getSeats().isEmpty()) 
		{
			this.seats = this.getSeats().stream().map(s->new BusSeats(s.getSeatName())).collect(Collectors.toList());
		} 
		else 
		{
			System.out.println("in bus seat constructor");
			this.seats = createSeats(totalSeats);
			this.seats = this.getSeats().stream().map(s->new BusSeats(s.getSeatName())).collect(Collectors.toList());
			System.out.println("seat created " + seats.size());
		}
		this.price = price;
		this.totalSeats = totalSeats;
	}
	private List<BusSeats> createSeats(int s) {
		List<BusSeats> seats = new ArrayList<>();
		for (int i = 0; i < s; i++) {
			BusSeats seat = new BusSeats();
			seat.setSeatName("A-"+i);
			seats.add(seat);
		}
		return seats;
	}
	@Override
	public String toString() {
		return "BusApp [busNumber=" + busNumber + ", sourceCity=" + sourceCity + ", destinationCity=" + destinationCity
				+ ", travelDate=" + travelDate + ", returnDate=" + returnDate + ", departureTime=" + departureTime
				+ ", arrivalTime=" + arrivalTime + ", operatorName=" + operatorName + ", duration=" + duration
				+ ", price=" + price + ", totalSeats=" + totalSeats + "]";
	}
	
	
}
