package com.patienthubservice.exception;

public class DiagnosticCenterNotPresentException extends Exception{

	private static final long serialVersionUID = 1L;

	public DiagnosticCenterNotPresentException(String message) {
		super(message);
	}

}
