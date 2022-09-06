package com.wsa.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wsa.app.constant.ExceptionMessage;
import com.wsa.app.domain.User;
import com.wsa.app.exception.domain.BadRequestException;
import com.wsa.app.exception.domain.NotFoundException;
import com.wsa.app.repository.UserRepository;
import com.wsa.app.request.LoginRequest;
import com.wsa.app.response.AuthenticationResponse;
import com.wsa.app.service.jwt.JwtUtils;
import com.wsa.app.service.jwt.UserDetailsImpl;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException(ExceptionMessage.USER_NOT_FOUND);
		}
		return new UserDetailsImpl(user);
	}

	@Override
	public ResponseEntity<AuthenticationResponse> authenticateUser(LoginRequest loginRequest) throws BadRequestException, NotFoundException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		AuthenticationResponse response = new AuthenticationResponse();
		response.setId(userDetails.getUser().getUserId());
		response.setUsername(userDetails.getUsername());
		response.setRole(userDetails.getUser().getRole().name());
		response.setFirstName(userDetails.getUser().getFirstName());
		response.setLastName(userDetails.getUser().getLastName());
		return ResponseEntity.ok().header("Authorization", token)
				.header("Access-Control-Expose-Headers", "Authorization").body(response);
	}

}
