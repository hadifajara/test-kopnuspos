package com.test.kopnuspos.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.kopnuspos.models.JobUser;
import com.test.kopnuspos.models.Jobs;
import com.test.kopnuspos.models.Role;
import com.test.kopnuspos.models.User;
import com.test.kopnuspos.models.UserDTO;
import com.test.kopnuspos.repositories.JobsRepo;
import com.test.kopnuspos.repositories.JobsUserRepo;
import com.test.kopnuspos.repositories.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
    private RoleService roleService;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private JobsRepo jobRepo;
    
    @Autowired
    private JobsUserRepo jobUserRepo;
    
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findOne(String username) {
        return userRepo.findByUsername(username);
    }

    
    public User save(UserDTO user) {

        User nUser = user.UserDTOMapper();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepo.save(nUser);
    }
    
    public void saveJobs(String username, String code) {
    	
        User user = userRepo.findByUsername(username);
        Jobs job = jobRepo.findByJobcode(code);
        
        JobUser jobuser = new JobUser();
        jobuser.setJobId(job.getId());
        jobuser.setUserId(user.getId());
        
        log.info(user.toString());
        log.info(job.toString());
        
        jobUserRepo.save(jobuser);
    }

}
