package com.patienthubservice.service;

import java.util.Set;

import com.patienthubservice.entities.Appointment;

public interface IAppointmentService {
	Appointment makeAppointment(Appointment appointment, String username, int testId, int diagnosticCenterId)
			throws Exception;
	Set<Appointment> viewAppointments(String username) throws Exception;
	Appointment viewMyAppointment(int appointmentId, String username) throws Exception;

}
