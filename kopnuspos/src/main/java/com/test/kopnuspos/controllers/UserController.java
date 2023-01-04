package com.test.kopnuspos.controllers;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.kopnuspos.configuration.JwtUtils;
import com.test.kopnuspos.models.Jobs;
import com.test.kopnuspos.models.User;
import com.test.kopnuspos.models.UserDTO;
import com.test.kopnuspos.models.UserLogin;
import com.test.kopnuspos.services.JobsService;
import com.test.kopnuspos.services.UserService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;
    
    @Autowired
    private JobsService jobsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(HttpSession session,@Valid @RequestBody UserLogin userLogin) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		userLogin.getUsername(),
                		userLogin.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtils.generateToken(authentication);
        session.setAttribute("username", userLogin.getUsername());
        session.setAttribute("token", token);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User saveUser(@Valid @RequestBody UserDTO user){
        return userService.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/admin", method = RequestMethod.POST)
    public ResponseEntity<?> admin(@RequestBody Jobs jobs){
    	
    	return new ResponseEntity<String>("Only Admins Can Read This", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/apply/{code}", method = RequestMethod.POST)
    public ResponseEntity<?> user(HttpSession session, @PathVariable("code") String code){
    	
    	String username = (String) session.getAttribute("username");
    	
    	userService.saveJobs(username, code);
    	
    	return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/newjob", method = RequestMethod.POST)
    public ResponseEntity<?> newJobs(@RequestBody Jobs jobs){
    	
    	jobsService.save(jobs);
    	
    	return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value="/joblist", method = RequestMethod.GET)
    public ResponseEntity<?> getJobs(){
    	
    	List<Jobs> list = jobsService.getJobs();
    	
    	return new ResponseEntity<Object>(list, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/deletejobsid/{id}")
    public ResponseEntity<?> delJobs(@PathVariable("id") Long id){
    	
    	jobsService.deleteJobs(id);
    	
    	return new ResponseEntity<Object>("Delete Successfully", HttpStatus.OK);
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/async")
    public ResponseEntity<?> testAsync() throws InterruptedException{
    	
    	for (int i = 1; i <= 100; i++) {
			userService.getTestLoop(i);
		}
    	
    	return new ResponseEntity<Object>("RUN", HttpStatus.OK);
    }
    
}
