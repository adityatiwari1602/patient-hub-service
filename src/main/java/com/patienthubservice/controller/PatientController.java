package com.patienthubservice.controller;


import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.patienthubservice.service.IJwtUtil;
import com.patienthubservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.TestResult;

@RestController
@RequestMapping(path="/api/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired 
	private IJwtUtil jwtUtil;
	

	@GetMapping(value="/allTest", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<DiagnosticTest>> getAllTestResult(HttpServletRequest request) throws Exception{
		String patientUserName=getPatientByUsername(request);
		ArrayList<DiagnosticTest> allTest=patientService.getAllTestResult(patientUserName);
		return new ResponseEntity<ArrayList<DiagnosticTest>>(allTest,HttpStatus.OK);
	}

	
	@GetMapping(value="/testResult/{testResultId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TestResult> getTestResultById(@PathVariable int testResultId) throws Exception{
		return new ResponseEntity<TestResult>(patientService.viewTestResult(testResultId),HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter()
	{
		List<DiagnosticCenter> centerList = patientService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(centerList,HttpStatus.OK);
	}

	@GetMapping(value = "/getAllAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Appointment>> getAllAppointments(HttpServletRequest request) throws Exception {
	String username=getPatientByUsername(request)	;
	ArrayList appointments=patientService.getAllAppointments(username);
	return new ResponseEntity<ArrayList<Appointment>>(appointments,HttpStatus.OK);
	}
	
	
	  public String getPatientByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7); String username = jwtUtil.extractUsername(token);
	  	return username;
	}


}
