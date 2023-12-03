package com.store.grocery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="grocery_items")
public class GroceryItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_id")
    private Integer itemId;
	
	@Column(name="name")
    private String name;
	
	@Column(name="price")
    private Double price;
	
	@Column(name="quantity")
    private Integer quantity;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
