package com.patienthubservice.exception;



public class NoTestFoundAtThisCenterException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoTestFoundAtThisCenterException(String message) {
		super(message);
	}
}