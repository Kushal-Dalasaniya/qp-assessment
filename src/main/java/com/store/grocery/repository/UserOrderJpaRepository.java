package com.store.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.grocery.entity.UserOrder;

public interface UserOrderJpaRepository extends JpaRepository<UserOrder, Integer> {

}
