package com.store.grocery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.grocery.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	public Optional<UserInfo> findByEmail(String username);
}
