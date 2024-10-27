package com.BBC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BBC.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{
	
	boolean existsById(Long customerId);
}
