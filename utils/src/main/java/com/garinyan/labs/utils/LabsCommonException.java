package com.garinyan.labs.utils;

@SuppressWarnings("serial")
public class LabsCommonException extends RuntimeException {
	public LabsCommonException() {
	    super();
	}

	public LabsCommonException(String s) {   
		super(s);
	}

	public LabsCommonException(Exception e) {
		super(e);
	}
}
