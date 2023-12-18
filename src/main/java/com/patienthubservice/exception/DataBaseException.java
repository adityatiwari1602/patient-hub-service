package com.patienthubservice.exception;

public class DataBaseException extends Exception{

	private static final long serialVersionUID = 1L;

	public DataBaseException() {
		super("Some problem occured with database. Try again later");
	}
}