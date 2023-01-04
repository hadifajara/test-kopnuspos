package com.test.kopnuspos.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.kopnuspos.models.Jobs;
import com.test.kopnuspos.repositories.JobsRepo;


@Service
@Transactional
public class JobsService {
	
	@Autowired
	JobsRepo jobsRepo;
	
	public void save(Jobs jobs) {
		jobsRepo.save(jobs);
    }
	
	public List<Jobs> getJobs(){
		return jobsRepo.findAll();
	}
	
	public void deleteJobs(Long id) {
		
		jobsRepo.deleteById(id);
	}
}
