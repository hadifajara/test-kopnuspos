package com.test.kopnuspos.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern.Flag;

import lombok.Data;

@Data
public class UserDTO {
	
	@NotEmpty(message = "username is required.")
	private String username;
	
	@NotEmpty(message = "password is required.")
    private String password;
	
    @NotEmpty(message = "email is required.")
	@Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String email;
    
    @NotEmpty(message = "name is required.")
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
