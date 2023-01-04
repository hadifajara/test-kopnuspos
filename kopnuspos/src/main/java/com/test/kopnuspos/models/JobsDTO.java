package com.test.kopnuspos.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
public class JobsDTO {
	
	private String jobcode;
	
	private String description;
	
}
