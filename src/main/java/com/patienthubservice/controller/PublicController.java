package com.patienthubservice.controller;

import java.util.Map;

import javax.validation.Valid;

import com.patienthubservice.requests.LoginRequest;
import com.patienthubservice.requests.PatientSignUpRequest;
import com.patienthubservice.responses.LoginResponse;
import com.patienthubservice.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patienthubservice.responses.SuccessMessage;

@RestController
@RequestMapping(value = "/api/public")
public class PublicController {

	@Autowired
	private PublicService publicService;


	@PostMapping(value = "/registerPatient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> registerPatient(@RequestBody @Valid PatientSignUpRequest patientDetails)
			throws Exception {
		publicService.registerPatient(patientDetails);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Patient Registration", "The Patient Registered Successfully"), HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> getAuthenticationToken(@RequestBody @Valid LoginRequest loginRequest)
			throws Exception {
		LoginResponse loginResponse = publicService.getAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
		return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
	}
}
