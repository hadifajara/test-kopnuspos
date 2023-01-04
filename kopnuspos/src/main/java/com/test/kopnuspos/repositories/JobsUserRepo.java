package com.test.kopnuspos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.kopnuspos.models.JobUser;

public interface JobsUserRepo extends JpaRepository<JobUser, Long>{
	
	
}
