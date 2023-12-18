package com.patienthubservice.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.patienthubservice.requests.TestResultForm;
import com.patienthubservice.service.DiagnosticCenterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.patienthubservice.dao.AppointmentRepository;
import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.TestRepository;
import com.patienthubservice.dao.TestResultRepository;
import com.patienthubservice.dao.UserRepository;
import com.patienthubservice.entities.Appointment;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.Patient;
import com.patienthubservice.entities.TestResult;
import com.patienthubservice.entities.User;

@ExtendWith(MockitoExtension.class)
public class DiagnosticCenterTests {
	
	@InjectMocks
	private DiagnosticCenterService diagnosticCenterService;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TestRepository testRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	
	@Mock
	private TestResultRepository testResultRepository;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	


	private static User mockDiagnosticUser;
	private static DiagnosticCenter mockDiagnosticCenter;
	private static User mockDiagnosticUser2;
	private static DiagnosticCenter mockDiagnosticCenter2;
	private static User mockPatientUser;
	private static Patient mockPatient;
	private static DiagnosticTest mockDiagnosticTest;
	private static Appointment mockAppointment;
	private static TestResultForm testResultForm;
	private static TestResult mocKTestResult;
	
	private static User mockPatientUser1;
	private static Patient mockPatient1;
	private static User mockPatientUser2;
	private static Patient mockPatient2;
	private static User mockPatientUser3;
	private static Patient mockPatient3;
	
	
	@BeforeEach
	public void init() {
		mockDiagnosticUser = new User(10, "center1@gmail.com", "Password@123", "ROLE_CENTER");
		mockDiagnosticCenter = new DiagnosticCenter(mockDiagnosticUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
		mockPatientUser = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		mockPatient = new Patient(mockPatientUser.getId(),"Patient",21,"Male","9988776655");
		mockDiagnosticTest =new DiagnosticTest("HemoglobinTest",1700.00,"23.4","21.4");
		mockDiagnosticTest.setId(1001);
		final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2018, 10, 7), LocalTime.of(8, 45, 0)));
		mockAppointment= new Appointment(1000,timestamp, 1, "Blood","Blood Pressure", mockPatient, mockDiagnosticCenter );
		testResultForm=new TestResultForm(1000,"Normal",21.3);
		mocKTestResult=new TestResult(1000,21.3,"Normal",mockAppointment);
		
		mockPatientUser1 = new User(11, "patient1@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient1 = new Patient(mockPatientUser1.getId(),"Patient 1",21,"Male","9988776655");		

		mockPatientUser2 = new User(13, "patient2@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient2 = new Patient(mockPatientUser2.getId(), "Patient 2", 21, "Female", "9797979797");
		
		mockPatientUser3 = new User(14, "patient3@gmail.com", "Password@123", "ROLE_PATIENT");
		
		mockPatient3 = new Patient(mockPatientUser3.getId(), "Patient 3", 21, "Female", "9697979797");
	
		mockDiagnosticUser2 = new User(12, "center2@gmail.com", "Password@123", "ROLE_CENTER");
		
		mockDiagnosticCenter2 = new DiagnosticCenter(mockDiagnosticUser2.getId(), "Center2", "9898989899", "Address", "email2@gmail.com", "Services 2");
	
		
	}
	@Test
	public void getTestInfoTest()  {
		String testname="HemoglobinTest";
		Mockito.when(testRepository.findBytestName(testname)).thenReturn(mockDiagnosticTest);
		Assertions.assertEquals("HemoglobinTest", diagnosticCenterService.getTestInfo(testname).getTestName());
	}

	@Test
	public void getTestInfoIdTest() {
		String testname="HemoglobinTest";
		Mockito.when(testRepository.findBytestName(testname)).thenReturn(mockDiagnosticTest);
		Assertions.assertEquals(1001, diagnosticCenterService.getTestInfo(testname).getId());
	}
	
	@Test
	public void updateTestResultsTest() throws Exception
	{
		Mockito.when(appointmentRepository.existsById(1000)).thenReturn(true);
		Mockito.when(appointmentRepository.getOne(1000)).thenReturn(mockAppointment);
		assertEquals("Test results of "+testResultForm.getAppointmentId()+" Updated",diagnosticCenterService.updateTestResult(testResultForm));
	}
	
	
	/*
	 * Gets the list of appointments present in a diagnostic center 
	 */
	@Test
	public void getListOfAppointmentsSuccessfull() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(3, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only gives the list of appointments with given username
	 */
	
	@Test
	public void getListOfAppointmentsFiltersOutOtherCenters() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter2);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only gives the list of Accepted Appointments
	 */
	@Test
	public void getListOfAppointmentsFiltersOutPendingAppointments() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 0, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
	
	/*
	 * Only Gives the list of accepted appointments 
	 */
	@Test
	public void getListOfAppointmentsFiltersOutRejectedAppointments() throws Exception {
		Appointment appointment1 = new Appointment(101, Timestamp.valueOf(LocalDateTime.now()), 2, "Diagnosis", "Symptoms", mockPatient1, mockDiagnosticCenter);
		Appointment appointment2 = new Appointment(102, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient2, mockDiagnosticCenter);
		Appointment appointment3 = new Appointment(103, Timestamp.valueOf(LocalDateTime.now()), 1, "Diagnosis", "Symptoms", mockPatient3, mockDiagnosticCenter);
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointments.add(appointment3);
		Mockito.when(appointmentRepository.findAll()).thenReturn(appointments);
		Mockito.when(userRepository.findByUsername("center1@gmail.com")).thenReturn(mockDiagnosticUser);
		Mockito.when(diagnosticCenterRepository.getOne(10)).thenReturn(mockDiagnosticCenter);
		assertEquals(2, diagnosticCenterService.listOfCenterAppointment("center1@gmail.com").size());
	}
}


