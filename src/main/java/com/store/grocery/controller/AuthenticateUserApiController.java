package com.store.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.store.grocery.exception.GroceryStoreException;
import com.store.grocery.model.UserLoginRequest;
import com.store.grocery.model.UserRegistrationRequest;
import com.store.grocery.service.AuthenticateUserService;
import com.store.grocery.util.GroceryStoreConstants;
import com.store.grocery.util.GroceryStoreExceptionConstants;
import com.store.grocery.util.HeaderMapper;

@CrossOrigin
@RestController
public class AuthenticateUserApiController implements AuthenticateUserApi {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticateUserApiController.class);

    @Autowired
	private Environment env;
    @Autowired
    AuthenticateUserService authUserService;

	@Override
	public ResponseEntity<String> userLogin(String accept, String correlationId, UserLoginRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			String responce = authUserService.userLogin(body);
			return  new ResponseEntity<String>(responce,responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0001), correlationId);
		}
	}

	@Override
	public ResponseEntity<Void> userRegistration(String accept, String correlationId, UserRegistrationRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			authUserService.userRegistration(body);
			return new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0001), correlationId);
		}
	}

}
