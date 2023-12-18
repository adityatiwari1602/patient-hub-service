package com.patienthubservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.patienthubservice.dao.AppointmentRepository;
import com.patienthubservice.dao.DiagnosticCenterRepository;
import com.patienthubservice.dao.TestRepository;
import com.patienthubservice.dao.UserRepository;
import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.entities.User;
import com.patienthubservice.exception.DiagnosticCenterNotPresentException;
import com.patienthubservice.exception.UsernameAlreadyExistsException;
import com.patienthubservice.requests.DiagnosticCenterSignUpRequest;

@Service
@Transactional
public class AdminService implements IAdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private DiagnosticCenterRepository diagnosticCenterRepository;

	@Autowired
	private UserRepository userRepository;



	@Autowired
	private AppointmentRepository appointmentRepo;

	@Autowired
	private TestRepository testRepository;

	// Add diagnostic center
	@Override
	public DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception {
		User toFindUser = userRepository.findByUsername(diagnosticCenter.getUserName());
		if (toFindUser != null) {
			throw new UsernameAlreadyExistsException("Username Exception", "Username Exists Exception");
		}
		String salt = BCrypt.gensalt(10);
		User user = new User(diagnosticCenter.getUserName(), BCrypt.hashpw(diagnosticCenter.getPassword(), salt),
				"ROLE_CENTER");
		User newUser = userRepository.save(user);
		DiagnosticCenter newDiagnosticCenter = new DiagnosticCenter(newUser.getId(), diagnosticCenter.getName(),
				diagnosticCenter.getContactNo(), diagnosticCenter.getAddress(), diagnosticCenter.getContactEmail(),
				diagnosticCenter.getServicesOffered());
		DiagnosticCenter addedCenter = diagnosticCenterRepository.save(newDiagnosticCenter);
		LOGGER.info("Diagnostic center added successfully...");
		return addedCenter;
	}

	// Get diagnostic center by Id

	public DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId) {
		DiagnosticCenter center = diagnosticCenterRepository.findById(diagnosticCenterId).get();
		LOGGER.info("Fetched diagnostic center successfully...");
		return center;
	}

	// Remove diagnostic center by Id
	@Override
	public List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId) throws Exception {

		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenterId);

		if (center == null) {
			throw new DiagnosticCenterNotPresentException("Diagnostic center not present");
		}
		diagnosticCenterRepository.delete(center);
		userRepository.deleteById(center.getId());
		LOGGER.info("Diagnostic center removed successfully...");
		return getAllDiagnosticCenter();
	}

	// Update diagnostic center
	@Override
	public DiagnosticCenter updateDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		DiagnosticCenter center = getDiagnosticCenterById(diagnosticCenter.getId());
		center.setId(diagnosticCenter.getId());
		center.setName(diagnosticCenter.getName());
		center.setContactNo(diagnosticCenter.getContactNo());
		center.setAddress(diagnosticCenter.getAddress());
		center.setContactEmail(diagnosticCenter.getContactEmail());
		center.setServicesOffered(diagnosticCenter.getServicesOffered());
		DiagnosticCenter updatedCenter = diagnosticCenterRepository.save(center);
		LOGGER.info("Diagnostic center updated successfully...");
		return updatedCenter;
	}

	// Get all diagnostic center
	@Override
	public List<DiagnosticCenter> getAllDiagnosticCenter() {
		List<DiagnosticCenter> centers = diagnosticCenterRepository.findAll();
		LOGGER.info("Fetched all diagnostic centers successfully...");
		return centers;
	}

	@Override
	public List<DiagnosticTest> getAllTest() {
		List<DiagnosticTest> tests = testRepository.findAll();
		LOGGER.info("getting all tests");
		return tests;
	}



	// this method will add a new test in the database...
	@Override
	public DiagnosticTest addNewTest(DiagnosticTest test) {
		DiagnosticTest addedTest = testRepository.save(test);
		LOGGER.info("test added successfully...");
		return addedTest;
	}

	// the method is updating the test details..
	@Override
	public DiagnosticTest updateTestDetail(DiagnosticTest test) {
		DiagnosticTest updatedTest = testRepository.save(test);
		LOGGER.info("test updated successfully...");
		return updatedTest;
	}
}