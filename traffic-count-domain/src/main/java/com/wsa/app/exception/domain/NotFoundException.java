package com.wsa.app.exception.domain;

import com.wsa.app.constant.ExceptionMessage;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 5165976759912324389L;

	public NotFoundException() {
		super(ExceptionMessage.DEFAULT_NOT_FOUND_MESSAGE);
	}

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}
}