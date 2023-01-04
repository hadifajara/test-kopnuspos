package com.test.kopnuspos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.kopnuspos.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}
