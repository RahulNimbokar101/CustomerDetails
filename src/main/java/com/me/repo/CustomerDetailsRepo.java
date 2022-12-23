package com.me.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.me.entity.CustomerDetails;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Integer> {
	
	@Query("select distinct(planName)from CustomerDetails")
	public List<String> getByPlanName();
	
	@Query("select distinct(status)from CustomerDetails")
	public List<String> getByStatus();

	
}



