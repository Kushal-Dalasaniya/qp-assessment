package com.store.grocery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.grocery.config.JwtService;
import com.store.grocery.entity.UserInfo;
import com.store.grocery.model.UserLoginRequest;
import com.store.grocery.model.UserRegistrationRequest;
import com.store.grocery.repository.UserInfoRepository;
import com.store.grocery.util.GroceryStoreConstants;

@Service
public class AuthenticateUserService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String userLogin(UserLoginRequest userLoginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(userLoginRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid user request !");
		}
	}
	
	public void userRegistration(UserRegistrationRequest userRegistrationRequest) {
		UserInfo user = new UserInfo();
		user.setEmail(userRegistrationRequest.getEmail());
		user.setName(userRegistrationRequest.getName());
		user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
		user.setRoles(GroceryStoreConstants.ROLE_USER);
		
		userInfoRepository.save(user);
	}
	
}
