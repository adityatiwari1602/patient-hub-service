package com.patienthubservice.controller;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.patienthubservice.entities.Patient;
import com.patienthubservice.requests.DiagnosticCenterSignUpRequest;
import com.patienthubservice.responses.SuccessMessage;
import com.patienthubservice.service.IAdminService;
import com.patienthubservice.service.IJwtUtil;
import com.patienthubservice.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patienthubservice.entities.DiagnosticCenter;
import com.patienthubservice.entities.DiagnosticTest;


@RestController
@RequestMapping(path="/api/admin")
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private IJwtUtil jwtUtil;
	
	@Autowired
	private IAdminService adminService;
	@Autowired
	private IPatientService patientService;

	public String getAdminByUsername(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		String username = jwtUtil.extractUsername(token);
		return username;	
	}

	@Cacheable("patients")
	@GetMapping( value= "/getAllPatients")
	public ResponseEntity<List<Patient>>  getAllPatients() throws Exception {
		List<Patient> patients = patientService.getAllPatients();
		return new ResponseEntity<List<Patient>>(patients,HttpStatus.OK);
	}
	//Add diagnostic center
	@PostMapping(value = "/addDiagnosticCenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessMessage> addDiagnosticCenter(@RequestBody DiagnosticCenterSignUpRequest diagnosticCenterSignUpDetails)
			throws Exception {
		adminService.addDiagnosticCenter(diagnosticCenterSignUpDetails);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Diagnostic Center SignUp Request", "Diagnostic Center added Successfully"), HttpStatus.OK);
	}
	
	//Remove diagnostic center
	@DeleteMapping("/removeDiagnosticCenter/{diagnosticCenterId}")
	public ResponseEntity<SuccessMessage> removeDiagnosticCenter(@PathVariable("diagnosticCenterId") int diagnosticCenterId) throws Exception
	{
		adminService.removeDiagnosticCenter(diagnosticCenterId);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Remove Diagnostic Center", "Diagnostic Center removed Successfully"), HttpStatus.OK);
	}
	
	//Update diagnostic center
	@PutMapping("/updateDiagnosticCenter/{diagnosticCenterId}")
	public ResponseEntity<SuccessMessage> updateDiagnosticCenter(@PathVariable("diagnosticCenterId") int diagnosticCenterId, @RequestBody DiagnosticCenter diagnosticCenter) throws Exception
	{
		adminService.updateDiagnosticCenter(diagnosticCenter);
		return new ResponseEntity<SuccessMessage>(
				new SuccessMessage("Update Diagnostic Center", "Diagnostic Center updated Successfully"), HttpStatus.OK);
	}
	
	//Get all diagnostic center
	@Cacheable("diagnostics")
	@GetMapping("/getAllDiagnosticCenter")
	public ResponseEntity<List<DiagnosticCenter>> getAllDiagnosticCenter()
	{
		List<DiagnosticCenter> centerList = adminService.getAllDiagnosticCenter();
		return new ResponseEntity<List<DiagnosticCenter>>(centerList,HttpStatus.OK);
	}
	//this method is for getting all tests
	@GetMapping("/getAllTests")
	public ResponseEntity<List<DiagnosticTest>> getAllTests(){
		List<DiagnosticTest> tests=adminService.getAllTest();
		return new ResponseEntity<List<DiagnosticTest>>(tests,HttpStatus.OK);
	}
	//this method is for adding new test
	@PostMapping("/addNewTest")
	public ResponseEntity<DiagnosticTest> addNewTest(@RequestBody DiagnosticTest newTest){
		DiagnosticTest addedTest=adminService.addNewTest(newTest);
		return new ResponseEntity<DiagnosticTest>(addedTest,HttpStatus.OK);
	}
	//this test is for updating the details of test..
	@PutMapping("/editTest")
	public ResponseEntity<DiagnosticTest> editTest(@RequestBody DiagnosticTest test){
		DiagnosticTest updatedTest=adminService.updateTestDetail(test);
		return new ResponseEntity<DiagnosticTest>(updatedTest,HttpStatus.OK);
	}

	@GetMapping("/getDiagnosticCenterById/{centerId}")
	public ResponseEntity<DiagnosticCenter> getDiagnosticCenterbyId(@PathVariable("centerId") int centerId){
		DiagnosticCenter center=adminService.getDiagnosticCenterById(centerId);
		return new ResponseEntity<DiagnosticCenter>(center,HttpStatus.OK);
	}

}