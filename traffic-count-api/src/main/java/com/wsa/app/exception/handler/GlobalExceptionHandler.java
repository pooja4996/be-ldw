package com.wsa.app.exception.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wsa.app.constant.ExceptionMessage;
import com.wsa.app.exception.domain.BadRequestException;
import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.exception.response.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {

		return createHttpResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {

		return createHttpResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> badCrendentialException() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, ExceptionMessage.INCORRECT_CRENDENTIALS);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ExceptionResponse> accountDisabledException() {
		return createHttpResponse(HttpStatus.BAD_REQUEST, ExceptionMessage.ACCOUNT_DISABLED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> accessDeniedException() {
		return createHttpResponse(HttpStatus.FORBIDDEN, ExceptionMessage.NOT_ENOUGH_PERMISSION);
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ExceptionResponse> accountLockedException() {
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ExceptionMessage.ACCOUNT_LOCKED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> illegalArgumentExceptionException(IllegalArgumentException ex) {
		return createHttpResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> internalServerException(Exception ex) {
		ex.printStackTrace();
		log.error(ex.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionResponse> iOException(IOException ex) {
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessage.ERROR_PROCESSING_FILE);
	}

	private ResponseEntity<ExceptionResponse> createHttpResponse(HttpStatus httpStatus, String message) {

		return new ResponseEntity<>(new ExceptionResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
	}
}
