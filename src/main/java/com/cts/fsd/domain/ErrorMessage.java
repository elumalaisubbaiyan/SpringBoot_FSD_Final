package com.cts.fsd.domain;

public class ErrorMessage {
	private String errorMsg;

	public ErrorMessage() {

	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ErrorMessage(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

}
