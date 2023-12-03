package com.store.grocery.exception;

import org.springframework.http.HttpStatus;

/*
 * @author Kushal.dalasaniya
 * This is custom exception for Api calls
 */
public class GroceryStoreException extends RuntimeException{
	
	private final HttpStatus statusCode;
	private final String code;
	private final String message;
	private final String correlationId;

	public GroceryStoreException(HttpStatus statusCode, String code, String message, String correlationId) {
		this.statusCode = statusCode;
		this.code = code;
		this.message = message;
		this.correlationId = correlationId;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getCorrelationId() {
		return correlationId;
	}
}
