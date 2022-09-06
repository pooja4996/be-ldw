package com.wsa.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wsa.app.exception.domain.BadRequestException;
import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.exception.handler.GlobalExceptionHandler;
import com.wsa.app.request.LoginRequest;
import com.wsa.app.response.AuthenticationResponse;
import com.wsa.app.service.user.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController extends GlobalExceptionHandler {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest request)
			throws BadRequestException, NotFoundException {
		return userService.authenticateUser(request);
	}

}
