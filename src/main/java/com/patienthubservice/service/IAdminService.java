package com.patienthubservice.service;

import java.util.List;

import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;
import com.patienthubservice.requests.DiagnosticCenterSignUpRequest;

public interface IAdminService {

	// Add
	DiagnosticCenter addDiagnosticCenter(DiagnosticCenterSignUpRequest diagnosticCenter) throws Exception;

	// Get by Id
	DiagnosticCenter getDiagnosticCenterById(int diagnosticCenterId);

	// Remove
	List<DiagnosticCenter> removeDiagnosticCenter(int diagnosticCenterId) throws Exception;

	// Update
	DiagnosticCenter updateDiagnosticCenter(DiagnosticCenter diagnosticCenter);

	// GetAll
	List<DiagnosticCenter> getAllDiagnosticCenter();
	

	List<DiagnosticTest> getAllTest();


	DiagnosticTest addNewTest(DiagnosticTest test);

	DiagnosticTest updateTestDetail(DiagnosticTest test);



}