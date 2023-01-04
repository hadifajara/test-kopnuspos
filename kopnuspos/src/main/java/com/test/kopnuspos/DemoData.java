package com.test.kopnuspos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.test.kopnuspos.models.Role;
import com.test.kopnuspos.services.RoleService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DemoData implements ApplicationRunner{
	
	@Autowired
	RoleService roleService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Role role = new Role();
		role.setId(1);
		role.setDescription("Admin Role");
		role.setName("ADMIN");
		roleService.save(role);
		
		role.setId(2);
		role.setDescription("User Role");
		role.setName("USER");
		roleService.save(role);
		
	}
}
