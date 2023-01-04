package com.test.kopnuspos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.kopnuspos.models.Jobs;

public interface JobsRepo extends JpaRepository<Jobs, Long>{
	
	Jobs findByJobcode(String jobcode);
	
}
