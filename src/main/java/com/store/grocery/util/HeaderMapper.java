package com.store.grocery.util;

import org.springframework.http.HttpHeaders;

public class HeaderMapper {
	private static final String CORRELATION_ID="CorrelationId";
	private static final String ACCEPT = "Accept";
	private static HttpHeaders headers = new HttpHeaders();
	
	/*
	 * This method return HttpHeader
	 * 
	 * @param correlationId
	 * @return HttpHeaders
	 */
	public static HttpHeaders getHttpHeaders(String correlationId) {
		headers.set(CORRELATION_ID, correlationId);
		headers.set(ACCEPT, GroceryStoreConstants.APPLICATION_JSON);
		return headers;
	}
	
}
