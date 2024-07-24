package com.microserviceTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microserviceTest.entity.UserEntity;

public interface IUserDao extends JpaRepository<UserEntity, Long>{

	public boolean existsByEmail(String email);
}
