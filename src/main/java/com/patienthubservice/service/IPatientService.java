package com.patienthubservice.service;

import java.util.ArrayList;
import java.util.List;

import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.Patient;
import com.patienthubservice.entities.TestResult;

public interface IPatientService {

	//fetch patient details from username
	Patient getPatientByUserName(String patientUserName);

	List<Patient> getAllPatients();

	//fetch all the TestResult taken by the patient
	ArrayList<DiagnosticTest> getAllTestResult(String patientUserName) throws Exception;

	// view test result for patient
	TestResult viewTestResult(int testResultId) throws Exception;

}