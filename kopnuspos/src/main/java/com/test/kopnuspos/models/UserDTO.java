package com.test.kopnuspos.models;

import lombok.Data;

@Data
public class UserDTO {
	
	private String username;
    private String password;
    private String email;
    private String name;
    
    public User UserDTOMapper(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        
        return user;
    }
}
