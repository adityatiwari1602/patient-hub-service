package com.patienthubservice.service;


import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patienthubservice.dao.AppointmentRepository;
import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.TestRepository;
import com.patienthubservice.dao.TestResultRepository;
import com.patienthubservice.dao.UserRepository;
import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.TestResult;
import com.patienthubservice.entities.User;
import com.patienthubservice.exception.NoAppointmentException;
import com.patienthubservice.requests.TestResultForm;


@Service
@Transactional
public class DiagnosticCenterService implements IDiagnosticCenterService {
	

	private static final Logger LOGGER  = LoggerFactory.getLogger(DiagnosticCenterService.class);

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRepository testRepository;

	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	
	@Autowired
	private TestResultRepository testResultRepository;
	
	@Override
	public String addTestResult(TestResultForm testResultForm) {
		TestResult testResult = new TestResult();
		testResult.setTestReading(testResultForm.getTestReading());
		testResult.setAppointment(appointmentRepository.getOne(testResultForm.getAppointmentId()));
		testResult.setCondition(testResultForm.getCondition());
		try {
			testResultRepository.save(testResult);
		}
		catch(Exception dataBaseException)
		{
			LOGGER.error("Error While adding test result");
		}
		return "Successfully Created Test Result";
	}
	@Override
	public DiagnosticTest getTestInfo(String testName)
	{
		DiagnosticTest test = testRepository.findBytestName(testName);
		return test;
	}
	

	private static Validator validator;
	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	

	@Override
	public DiagnosticCenter getDiagnosticCenterByUsername(String diagnosticCenterUsername) {
		User user = userRepository.findByUsername(diagnosticCenterUsername);
		DiagnosticCenter diagnosticCenter = diagnosticCenterRepo.getOne(user.getId());
		return diagnosticCenter;
	}

	@Override
	public String updateTestResult(TestResultForm testResult) throws Exception {
		TestResult result =new TestResult();
		if (appointmentRepository.existsById(testResult.getAppointmentId()))
		{
			Appointment appointment = appointmentRepository.getOne(testResult.getAppointmentId());
			result.setCondition(testResult.getCondition());
			result.setTestReading(testResult.getTestReading());
			result.setAppointment(appointment);
			TestResult savedResult = testResultRepository.save(result);
			appointment.setTestResult(savedResult);
			return "Test results of "+testResult.getAppointmentId()+" Updated";
		} 
		else
		{
			LOGGER.error("Error while Updating the Test Result");
			throw new NoAppointmentException("Appointment Exception",
					"Entered appointment id does not xists");
		}
	}
	
	public List<Appointment> viewAppointments() {
		List<Appointment> appointments=appointmentRepository.findAll();
		
		return appointments;
	}
	
	
	@Override
	public List<Appointment> listOfCenterAppointment(String centerUserName)
	{
		List<Appointment> allAppointments = viewAppointments();
		List<Appointment> appointmentsInCenter =new ArrayList<Appointment>();
		DiagnosticCenter diognosticCenter =getDiagnosticCenterByUsername(centerUserName);
		for(Appointment appointment : allAppointments) {
			if((appointment.getDiagnosticCenter().getName()).contentEquals(diognosticCenter.getName()) && appointment.getApprovalStatus() == 1) {
				appointmentsInCenter.add(appointment);
			}
		}
		return appointmentsInCenter;
		
	}
	
}
