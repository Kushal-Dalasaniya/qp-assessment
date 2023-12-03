package com.store.grocery.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="user_orders")
public class UserOrder {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private GroceryItem groceryItem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @Column(name = "order_timestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderTimestamp;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public GroceryItem getGroceryItem() {
		return groceryItem;
	}

	public void setGroceryItem(GroceryItem groceryItem) {
		this.groceryItem = groceryItem;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public LocalDateTime getOrderTimestamp() {
		return orderTimestamp;
	}

	public void setOrderTimestamp(LocalDateTime orderTimestamp) {
		this.orderTimestamp = orderTimestamp;
	}
}
