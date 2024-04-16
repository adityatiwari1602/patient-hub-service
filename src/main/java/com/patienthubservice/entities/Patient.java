package com.patienthubservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PATIENTS")
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PHONE_NO", nullable = false)
	private String phoneNo;

	@Column(name = "AGE", nullable = false)
	private int age;

	@Column(name = "GENDER", nullable = false)
	@Pattern(regexp = "Male|Female|Other")
	private String gender;
	
	@OneToMany(mappedBy="patient")
	@JsonIgnore
	private Set<Appointment> appointments=new HashSet<>();
	

	public Patient() {

	}

	public Patient(int id, String name, int age, String gender, String phoneNo) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phoneNo = phoneNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
