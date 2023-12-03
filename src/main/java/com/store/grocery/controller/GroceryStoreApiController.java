package com.store.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.store.grocery.exception.GroceryStoreException;
import com.store.grocery.model.CreateGroceryItemRequest;
import com.store.grocery.model.GroceryItems;
import com.store.grocery.model.Item;
import com.store.grocery.model.ManageGroceryItemRequest;
import com.store.grocery.model.OrderGroceryItemRequest;
import com.store.grocery.service.GroceryStoreService;
import com.store.grocery.util.GroceryStoreConstants;
import com.store.grocery.util.GroceryStoreExceptionConstants;
import com.store.grocery.util.HeaderMapper;

@CrossOrigin
@RestController
public class GroceryStoreApiController implements GroceryStoreApi {
	
	private static final Logger log = LoggerFactory.getLogger(GroceryStoreApiController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private GroceryStoreService groceryStoreService;

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> createGroceryItems(String accept, String correlationId, CreateGroceryItemRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			groceryStoreService.createGroceryItems(body);
			return  new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0002, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0002), correlationId);
		}

	}
	
	@Override
	public ResponseEntity<GroceryItems> getAllGroceryItems(String accept, String correlationId) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			GroceryItems response = groceryStoreService.getAllGroceryItems();
			return  new ResponseEntity<GroceryItems>(response,responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0003, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_ERROR_0003), correlationId);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteGroceryItems(String accept, String correlationId, Integer itemId) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			groceryStoreService.deleteGroceryItems(itemId);
			return  new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_DELETE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_DELETE_ERROR_0001), correlationId);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> updateGroceryItems(String accept, String correlationId, Integer itemId,CreateGroceryItemRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			groceryStoreService.updateGroceryItems(body,itemId,correlationId);
			return  new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001), correlationId);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Item> getGroceryItem(String accept, String correlationId, Integer itemId) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			Item response = groceryStoreService.getGroceryItem(itemId, correlationId);
			return  new ResponseEntity<Item>(response,responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_ITEM_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_ITEM_ERROR_0001), correlationId);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> manageGroceryItem(String accept, String correlationId, Integer itemId,ManageGroceryItemRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			groceryStoreService.manageGroceryItem(body, itemId, correlationId);
			return  new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001), correlationId);
		}
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Void> orderGroceryItems(String accept, String correlationId, OrderGroceryItemRequest body) {
		if(accept == null || !accept.contains(GroceryStoreConstants.APPLICATION_JSON)) 
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		
		HttpHeaders responseHeaders = HeaderMapper.getHttpHeaders(correlationId);
		
		try {
			groceryStoreService.orderGroceryItems(body,correlationId);
			return  new ResponseEntity<>(responseHeaders,HttpStatus.OK);
		}catch(GroceryStoreException ex) {
			throw new GroceryStoreException(ex.getStatusCode(), ex.getCode(), ex.getMessage(), correlationId);
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR, GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_UPDATE_ERROR_0001), correlationId);
		}
	}
}
