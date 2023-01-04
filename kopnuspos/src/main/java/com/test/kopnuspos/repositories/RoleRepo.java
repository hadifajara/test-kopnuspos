package com.test.kopnuspos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.kopnuspos.models.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Role findRoleByName(String name);
}
