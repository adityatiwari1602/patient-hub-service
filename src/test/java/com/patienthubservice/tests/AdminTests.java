package com.patienthubservice.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.User;
import com.patienthubservice.exception.NoTestFoundAtThisCenterException;
import com.patienthubservice.exception.TestAlreadyFoundException;
import com.patienthubservice.exception.TestNotPresentInCenter;
import com.patienthubservice.exception.UsernameAlreadyExistsException;
import com.patienthubservice.requests.DiagnosticCenterSignUpRequest;
import com.patienthubservice.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.TestRepository;
import com.patienthubservice.dao.UserRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminTests {

	@InjectMocks
	private AdminService adminService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DiagnosticCenterRepository diagnosticCenterRepository;
	@Mock
	private TestRepository testRepository;

	private static DiagnosticCenter diagnosticCenter;
	private static DiagnosticTest test1, test2, test3;

	private static User mockDiagnosticUser, mockDiagnosticUser2, mockDiagnosticUser3, mockDiagnosticUser4;
	private static DiagnosticCenter mockDiagnosticCenter, mockDiagnosticCenter2, mockDiagnosticCenter3, mockDiagnosticCenter4;
	private static DiagnosticCenterSignUpRequest mockRequestSignUp, mockRequestSignUp2, mockRequestSignUp3, mockRequestSignUp4;

	@BeforeEach
	public void init() {
		mockDiagnosticUser = new User(10, "center1@gmail.com", "Password@12310", "ROLE_CENTER");
		mockDiagnosticUser2 = new User(11, "center2@gmail.com", "Password@12311", "ROLE_CENTER");
		mockDiagnosticUser3 = new User(12, "center3@gmail.com", "Password@12312", "ROLE_CENTER");
		mockDiagnosticUser4 = new User(13, "center4@gmail.com", "Password@12313", "ROLE_CENTER");

		mockDiagnosticCenter = new DiagnosticCenter(mockDiagnosticUser.getId(), "Center 1", "9876543210", "Address",
				"email@gmail.com", "Services");
		mockDiagnosticCenter2 = new DiagnosticCenter(mockDiagnosticUser2.getId(), "Center 2", "9474543560", "Address2",
				"email2@gmail.com", "Services2");
		mockDiagnosticCenter3 = new DiagnosticCenter(mockDiagnosticUser3.getId(), "Center 3", "7256489312", "Address3",
				"email3@gmail.com", "Services3");
		mockDiagnosticCenter4 = new DiagnosticCenter(mockDiagnosticUser4.getId(), "Center 4", "8624576810", "Address4",
				"email4@gmail.com", "Services4");

		mockRequestSignUp = new DiagnosticCenterSignUpRequest("Center 1", "9876543210", "Address", "email@gmail.com", "Services",
				"center1@gmail.com", "Password@12310");
		mockRequestSignUp2 = new DiagnosticCenterSignUpRequest("Center 2", "9474543560", "Address2", "email2@gmail.com", "Services2",
				"center2@gmail.com", "Password@12311");
		mockRequestSignUp3 = new DiagnosticCenterSignUpRequest("Center 3", "7256489312", "Address3", "email3@gmail.com", "Services3",
				"center3@gmail.com", "Password@12312");
		mockRequestSignUp4 = new DiagnosticCenterSignUpRequest("Center 4", "8624576810", "Address4", "email4@gmail.com", "Services4",
				"center4@gmail.com", "Password@12313");

		diagnosticCenter = new DiagnosticCenter("Akash Diagnostic Center", "1223", "UP", "akash@gmail.com", "testing");
		diagnosticCenter.setId(101);
		test1 = new DiagnosticTest("blood test", 1000, "13-17", "gm/dl");
		test1.setId(1001);
		test2 = new DiagnosticTest("Eye Test", 1000, "6/6", "mm");
		test2.setId(1002);
		test3 = new DiagnosticTest("LFT", 2000, "<1.1", "milligm/dl");
		test3.setId(1003);

	}



	//Add diagnostic center test case
	@Test
	public void addDiagnosticCenter() throws Exception {
		assertThrows(UsernameAlreadyExistsException.class, () -> {

			Mockito.when(userRepository.findByUsername(mockRequestSignUp.getUserName())).thenReturn(mockDiagnosticUser);
			Mockito.when(userRepository.save(mockDiagnosticUser)).thenReturn(mockDiagnosticUser);
			Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
			adminService.addDiagnosticCenter(mockRequestSignUp);
		});

	}

	//Get diagnostic center by id test case
	@Test
	public void getDiagnosticCenterById() {
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter));
		DiagnosticCenter center = adminService.getDiagnosticCenterById(10);
		assertEquals(10, center.getId());
	}

	//Remove diagnostic center test case
	@Test
	public void removeDiagnosticCenter() throws Exception {
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter));
		List<DiagnosticCenter> center = adminService.removeDiagnosticCenter(mockDiagnosticCenter.getId());
		assertEquals(0, center.size());
	}

	//Update diagnostic center test case
	@Test
	public void updateDiagnosticCenter() {
		Mockito.when(diagnosticCenterRepository.findById(10)).thenReturn(Optional.of(mockDiagnosticCenter));
		Mockito.when(diagnosticCenterRepository.save(mockDiagnosticCenter)).thenReturn(mockDiagnosticCenter);
		DiagnosticCenter center = adminService.updateDiagnosticCenter(mockDiagnosticCenter);
		assertEquals(10, center.getId());
	}

	//Get all diagnostic center test case
	@Test
	public void getAllDiagnosticCenter() {
		List<DiagnosticCenter> centers = new LinkedList<>();
		centers.add(mockDiagnosticCenter);
		Mockito.when(diagnosticCenterRepository.findAll()).thenReturn(centers);
		assertEquals(1, adminService.getAllDiagnosticCenter().size());
	}


	//getting all test test case
	@Test
	public void getAllTestsTest() {
		List<DiagnosticTest> tests = new LinkedList<>();
		tests.add(test1);
		tests.add(test2);
		Mockito.when(testRepository.findAll()).thenReturn(tests);
		assertEquals(2, adminService.getAllTest().size());
	}

	// adding new test testcase
	@Test
	public void addNewTestTest() {
		Mockito.when(testRepository.save(test1)).thenReturn(test1);
		Assertions.assertEquals(test1, adminService.addNewTest(test1));
	}
}