package com.patienthubservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.patienthubservice.responses.SuccessMessage;
import com.patienthubservice.service.IDiagnosticCenterService;
import com.patienthubservice.service.IJwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patienthubservice.entities.Appointment;
import com.patienthubservice.requests.TestResultForm;

@RestController
@RequestMapping(path = "/api/center")
public class DiagnosticCenterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticCenterController.class);

	@Autowired
	private IDiagnosticCenterService diagnosticService;

	@Autowired
	private IJwtUtil jwtUtil;



	@PostMapping(value = "/addTestResult", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addTestResult(@RequestBody TestResultForm testResultForm) throws Exception {
		String response = diagnosticService.addTestResult(testResultForm);
		return new ResponseEntity<SuccessMessage>(new SuccessMessage("Test Result", response),HttpStatus.OK);
	}
	@PostMapping(value = "/updateTestResult", produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<SuccessMessage> updateTestResult(@RequestBody TestResultForm testresultForm) throws Exception
	{
			String response =  diagnosticService.updateTestResult(testresultForm);
			return new ResponseEntity<SuccessMessage>(new SuccessMessage("Test Result", response),HttpStatus.OK);
	}
	
	/**
	 * Gets the Username From the JWToken
	 * 
	 * @param request - HttpServletRequest
	 * @return - SuccessMessage
	 * @throws Exception - JWTException
	 */
	public String getDiagnosticCenterByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;
	}
	
	@GetMapping(value = "/listOfCenterAppointment")
	public List<Appointment> listOfCenterAppointment(HttpServletRequest request) throws Exception{
			String username = getDiagnosticCenterByUsername(request);
		return diagnosticService.listOfCenterAppointment(username);
	}
}
