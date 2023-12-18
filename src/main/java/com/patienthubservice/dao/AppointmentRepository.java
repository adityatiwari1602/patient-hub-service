package com.patienthubservice.dao;

import com.patienthubservice.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

}
