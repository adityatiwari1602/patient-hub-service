package com.patienthubservice.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;

import com.patienthubservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.patienthubservice.dao.AppointmentRepository;
import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.TestResultRepository;
import com.patienthubservice.dao.UserRepository;
import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.Patient;
import com.patienthubservice.entities.TestResult;
import com.patienthubservice.entities.User;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PatientTests {
	
	@InjectMocks
	private PatientService patientService;
	
	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	@Mock
	private TestResultRepository testResultRepository;
	
	private static User mockDiagnosticCenterUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static Appointment mockAppointment;
	private static DiagnosticTest mockDiagnosticTest;
	private static TestResult mockTestResult;	
	
	
	@BeforeEach
	public void init() {
		mockDiagnosticCenterUser= new User(20,"dcenter@gmail.com","Password@123","ROLE_CENTER");
		mockDiagnosticCenter= new DiagnosticCenter(mockDiagnosticCenterUser.getId(),"Center 20","9638527410","Place xyz",
				"dc@gmail.com","serviceOffered");
		mockPatientUser= new User(22,"p@gmail.com","Password@123","ROLE_PATIENT");
		mockPatient= new Patient(mockPatientUser.getId(),"P1",23,"Male","9876432106");



		mockAppointment= new Appointment(10, new Timestamp(System.currentTimeMillis()), 1, "diagnosis", "symptoms", mockPatient, mockDiagnosticCenter);
		
		mockDiagnosticTest=new DiagnosticTest("Blood Test", 100.0, "12-16", "gram");
		mockDiagnosticCenter.getTests().add(mockDiagnosticTest);
		mockDiagnosticTest.setId(101);
		mockDiagnosticTest=new DiagnosticTest("Corona Test", 4000.0, "19", "ppi");
		mockDiagnosticCenter.getTests().add(mockDiagnosticTest);
		mockDiagnosticTest.setId(102);
		mockTestResult=new TestResult(1001, 13.5, "Normal", mockAppointment);
		
	}

}
