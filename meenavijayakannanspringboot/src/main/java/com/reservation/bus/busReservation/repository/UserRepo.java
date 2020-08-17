package com.reservation.bus.busReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.bus.busReservation.entity.User;


public interface UserRepo extends JpaRepository<User,Integer> {

}
