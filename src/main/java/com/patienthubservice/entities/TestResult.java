package com.patienthubservice.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="TEST_RESULTS")
@SequenceGenerator(name = "TEST_R_SEQ",allocationSize = 1, initialValue = 1001)
public class TestResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_R_SEQ")
	private int id;

	@Column(name="TEST_READ")
	private double testReading;
	
	@Column(name="P_CONDITION")
	@Pattern(regexp ="(Below Normal)|(Normal)|(Above Normal)")
	private String condition;
	
	@OneToOne(mappedBy="testResult")
	private Appointment appointment;

	public TestResult() {
		
	}


	public TestResult(int id, double testReading,
			@Pattern(regexp = "(Below Normal)|(Normal)|(Above Normal)") String condition, Appointment appointment) {
		this.id = id;
		this.testReading = testReading;
		this.condition = condition;
		this.appointment = appointment;
	}

	public double getTestReading() {
		return testReading;
	}
	public void setTestReading(double testReading) {
		this.testReading = testReading;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}	
	
}