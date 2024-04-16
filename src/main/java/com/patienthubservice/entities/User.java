package com.patienthubservice.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "USER_MASTER")
@SequenceGenerator(name = "user_id", initialValue = 1000, allocationSize = 1)
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
	private int id;
	@Column(name="USERNAME", unique = true)
	private String username;
	
	private String password;
	@Pattern(regexp ="ROLE_(PATIENT|ADMIN|CENTER)")
	private String role;
		
	public User() {
		
	}
	
	
	
	public User(int id, String username, String password,
			@Pattern(regexp = "ROLE_(PATIENT|ADMIN|CENTER)") String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}



	public User(String username, String password, String role)
	{
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
