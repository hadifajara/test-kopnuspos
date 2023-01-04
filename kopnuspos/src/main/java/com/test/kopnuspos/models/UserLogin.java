package com.test.kopnuspos.models;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserLogin {
	
	@NotEmpty(message = "username is required.")
	private String username;
	
	@NotEmpty(message = "password is required.")
    private String password;
}
