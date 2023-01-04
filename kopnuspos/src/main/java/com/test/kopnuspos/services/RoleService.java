package com.test.kopnuspos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.kopnuspos.models.Role;
import com.test.kopnuspos.repositories.RoleRepo;

@Service(value = "roleService")
public class RoleService {
	
	@Autowired
    private RoleRepo roleRepo;

    public Role findByName(String name) {
        Role role = roleRepo.findRoleByName(name);
        return role;
    }
    
    public void save(Role role) {
    	roleRepo.save(role);
    }
}
