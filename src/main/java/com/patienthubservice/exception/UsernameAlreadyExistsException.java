package com.patienthubservice.exception;

public class UsernameAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	private String header;
	
	public UsernameAlreadyExistsException(String header,String message)
	{
		super(message);	
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "UsernameAlreadyExistsException [header=" + header + "]";
	}
	
	
}
