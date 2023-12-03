package com.store.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.grocery.entity.GroceryItem;

public interface GroceryItemsJpaRepository extends JpaRepository<GroceryItem, Integer> {

}
