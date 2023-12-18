package com.patienthubservice.service;

import java.util.List;

import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.requests.TestResultForm;

public interface IDiagnosticCenterService {


	DiagnosticTest getTestInfo(String testName);


	DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername);

	String addTestResult(TestResultForm testResult) throws Exception;


	String updateTestResult(TestResultForm testResult) throws Exception;

	List<Appointment> listOfCenterAppointment(String centerUserName);

	

}