  
package com.patienthubservice.exception;

public class TestAlreadyFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public TestAlreadyFoundException(String message) {
		super(message);
	}
}