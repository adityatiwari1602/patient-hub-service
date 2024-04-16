package com.patienthubservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patienthubservice.dao.AppointmentRepository;
import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.PatientRepository;
import com.patienthubservice.dao.TestResultRepository;
import com.patienthubservice.dao.UserRepository;
import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.Patient;
import com.patienthubservice.entities.TestResult;
import com.patienthubservice.entities.User;
import com.patienthubservice.exception.NoAppointmentException;
import com.patienthubservice.exception.NoTestTakenException;


@Service
@Transactional
public class PatientService implements IPatientService {
	
	private static final Logger LOGGER  = LoggerFactory.getLogger(PatientService.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private TestResultRepository testResultRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	
	/**
	 * Function to fetch Patient by UserName;
	 *
	 * @param patientUserName
	 * @return patient : details of the patient.
	 *
	 */

	@Override
	public Patient getPatientByUserName(String patientUserName) {
		User user = userRepository.findByUsername(patientUserName);
		Patient patient = patientRepository.getOne(user.getId());
		return patient;
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	/**
	 * To fetch all the test results taken by the Patient.
	 * 
	 * @param patientUserName
	 * @return Set<TestResult> collection of the test results taken by patient
	 * @exception NoAppointmentException and NoTestTakenException
	 * 
	 */
	@Override
	public ArrayList<DiagnosticTest> getAllTestResult(String patientUserName) throws Exception {
		Patient patient = getPatientByUserName(patientUserName);
		Set<Appointment> appointments = patient.getAppointments();
		if (appointments.isEmpty()) {
			LOGGER.error("No Appointment");
			throw new NoAppointmentException("Appointment Exception","No Appointment Present");
		}
		ArrayList<DiagnosticTest> tests = new ArrayList<>();
		for (Appointment a : appointments) {
			tests.add(a.getDiagnosticTest());
		}
		if (tests.isEmpty()) {
			LOGGER.info("No Test Taken");
			throw new NoTestTakenException("No Test Taken");
		}
		LOGGER.info("All Test Returned");
		return tests;
	}

	@Override
	public TestResult viewTestResult(int testResultId) throws Exception {
		Optional<TestResult> testResult = testResultRepository.findById(testResultId);
		if (!testResult.isPresent()) {
			LOGGER.error("No Test Report are Present");
			throw new NoTestTakenException("No Test Taken");
		} else {
			LOGGER.info("Test Result Found");
			return testResult.get();
		}
	}
	
	public List<DiagnosticCenter> getAllDiagnosticCenter() {
		List<DiagnosticCenter> centers = diagnosticCenterRepo.findAll();
		LOGGER.info("Fetched all diagnostic centers successfully...");
		return centers;
	}
	
	public String getUsername(int id) {
		User user= userRepository.findById(id).get();
		return user.getUsername();
	}

	
	public ArrayList<Appointment> getAllAppointments(String username){
		Patient patient=getPatientByUserName(username);
		ArrayList<Appointment> appointments=(ArrayList<Appointment>) patient.getAppointments().stream().collect(Collectors.toList());
		return appointments;
	}

}
