package com.store.grocery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.store.grocery.entity.GroceryItem;
import com.store.grocery.entity.UserInfo;
import com.store.grocery.entity.UserOrder;
import com.store.grocery.exception.GroceryStoreException;
import com.store.grocery.model.CreateGroceryItemRequest;
import com.store.grocery.model.GroceryItems;
import com.store.grocery.model.Item;
import com.store.grocery.model.ManageGroceryItemRequest;
import com.store.grocery.model.OrderGroceryItemRequest;
import com.store.grocery.repository.GroceryItemsJpaRepository;
import com.store.grocery.repository.UserInfoRepository;
import com.store.grocery.repository.UserOrderJpaRepository;
import com.store.grocery.util.GroceryStoreExceptionConstants;
import com.store.grocery.util.SecurityUtill;

@Service
public class GroceryStoreService {
	
	@Autowired
	private GroceryItemsJpaRepository groceryItemsJpaRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UserOrderJpaRepository userOrderJpaRepository;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SecurityUtill securityUtill;
	
	public void createGroceryItems(CreateGroceryItemRequest itemRequest) {
		GroceryItem groceryItem = new GroceryItem();
		groceryItem.setName(itemRequest.getName());
		groceryItem.setPrice(itemRequest.getPrice());
		groceryItem.setQuantity(itemRequest.getQuantity());
		
		groceryItemsJpaRepository.save(groceryItem);
	}
	
	public GroceryItems getAllGroceryItems() {
		List<GroceryItem> groceryItems = groceryItemsJpaRepository.findAll();
		
		List<Item> items = new ArrayList<>();
		groceryItems.forEach((ele) ->{
			items.add(new Item()
					.id(ele.getItemId())
					.name(ele.getName())
					.price(ele.getPrice())
					.quantity(ele.getQuantity()));
		});
		String email = securityUtill.getUserDetails();
		System.out.println("Email : " + email);
		return new GroceryItems().itemList(items);
	}
	
	public void deleteGroceryItems(Integer itemId) {
		groceryItemsJpaRepository.deleteById(itemId);
	}
	
	public void updateGroceryItems(CreateGroceryItemRequest request,Integer itemId,String correlationId) {
		GroceryItem groceryItem = groceryItemsJpaRepository.findById(itemId)
				.orElseThrow(()->new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,
						GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001, 
						env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001), correlationId));
	
		groceryItem.setName(request.getName());
		groceryItem.setPrice(request.getPrice());
		groceryItem.setQuantity(request.getQuantity());
		
		groceryItemsJpaRepository.saveAndFlush(groceryItem);
	}
	
	public Item getGroceryItem(Integer itemId,String correlationId) {
		GroceryItem groceryItem = groceryItemsJpaRepository.findById(itemId)
				.orElseThrow(()->new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,
						GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001, 
						env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001), correlationId));
		
		return new Item()
				.id(groceryItem.getItemId())
				.name(groceryItem.getName())
				.price(groceryItem.getPrice())
				.quantity(groceryItem.getQuantity());
	}
	
	public void manageGroceryItem(ManageGroceryItemRequest request,Integer itemId,String correlationId) {
		GroceryItem groceryItem = groceryItemsJpaRepository.findById(itemId)
				.orElseThrow(()->new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,
						GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001, 
						env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001), correlationId));
		
		if(request.getAction().equals(ManageGroceryItemRequest.ActionEnum.INCREASE)) {
			groceryItem.setQuantity(groceryItem.getQuantity() + request.getQuantity());
		}
		else if(request.getAction().equals(ManageGroceryItemRequest.ActionEnum.DECREASE) && groceryItem.getQuantity() >= request.getQuantity()) {
			groceryItem.setQuantity(groceryItem.getQuantity() - request.getQuantity());
		}
		else {
			throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,GroceryStoreExceptionConstants.ITEM_QUANTITY_ERROR_0001, 
					env.getRequiredProperty(GroceryStoreExceptionConstants.ITEM_QUANTITY_ERROR_0001), correlationId);
		}
		
		groceryItemsJpaRepository.saveAndFlush(groceryItem);
	}
	
	public void orderGroceryItems(OrderGroceryItemRequest request,String correlationId) {
		String userEmail=securityUtill.getUserDetails();
		UserInfo user = userInfoRepository.findByEmail(userEmail)
				.orElseThrow(()->new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,
						GroceryStoreExceptionConstants.GROCERY_STORE_USER_ERROR_0001, 
						env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_STORE_USER_ERROR_0001), correlationId));
		
		List<UserOrder> orderList = new ArrayList<>();
		List<GroceryItem> groceryItemList=new ArrayList<>();
		
		request.getOrderList().forEach(order->{
			GroceryItem groceryItem = groceryItemsJpaRepository.findById(order.getItemId())
					.orElseThrow(()->new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,
							GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001, 
							env.getRequiredProperty(GroceryStoreExceptionConstants.GROCERY_ITEM_ERROR_0001), correlationId));
			
			if(groceryItem.getQuantity() < order.getQuantity()) 
				throw new GroceryStoreException(HttpStatus.INTERNAL_SERVER_ERROR,GroceryStoreExceptionConstants.ITEM_QUANTITY_ERROR_0001, 
						env.getRequiredProperty(GroceryStoreExceptionConstants.ITEM_QUANTITY_ERROR_0001), correlationId);
			
			groceryItem.setQuantity(groceryItem.getQuantity() - order.getQuantity());
			groceryItemList.add(groceryItem);
			
			UserOrder UserOrder =new UserOrder();
			UserOrder.setGroceryItem(groceryItem);
			UserOrder.setQuantity(order.getQuantity());
			UserOrder.setUser(user);
			UserOrder.setOrderTimestamp(LocalDateTime.now());
			
			orderList.add(UserOrder);
		});
		
		userOrderJpaRepository.saveAllAndFlush(orderList);
		groceryItemsJpaRepository.saveAllAndFlush(groceryItemList);
	}
}
