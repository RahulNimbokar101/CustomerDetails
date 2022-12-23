package com.me.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer_Details_info")
public class CustomerDetails {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer id;
	private String name;
	private String email;
	private Long mobile;
	private String gender;
	private String ssn;
	private String planName;
	private String status;
}
