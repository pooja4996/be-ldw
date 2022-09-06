package com.wsa.app.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wsa.app.exception.domain.BadRequestException;
import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.request.LoginRequest;
import com.wsa.app.response.AuthenticationResponse;

public interface UserService extends UserDetailsService{
	ResponseEntity<AuthenticationResponse> authenticateUser(LoginRequest loginRequest) throws BadRequestException, NotFoundException;
}
