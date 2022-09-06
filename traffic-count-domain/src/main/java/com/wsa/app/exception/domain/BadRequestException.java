package com.wsa.app.exception.domain;

import com.wsa.app.constant.ExceptionMessage;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 5605169750720449391L;

	public BadRequestException() {
		super(ExceptionMessage.DEFAULT_BAD_REQUEST_MESSAGE);
	}

	public BadRequestException(String errorMessage) {
		super(errorMessage);
	}
}