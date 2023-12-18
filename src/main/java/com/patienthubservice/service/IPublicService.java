package com.patienthubservice.service;

import com.patienthubservice.requests.PatientSignUpRequest;
import com.patienthubservice.responses.LoginResponse;

public interface IPublicService {

	void registerPatient(PatientSignUpRequest patientRequest) throws Exception;

	LoginResponse getAuthenticationToken(String username, String password) throws Exception;
	
	void registerAdmin(String username, String password) throws Exception;

}