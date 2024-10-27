package com.BBC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BBC.model.Customer;
import com.BBC.repository.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	public boolean checkCustomerExists(Long customerId) {
		return customerRepo.existsById(customerId);
	}
	
	 public Customer getCustomerDetails(Long customerId) {
	        return customerRepo.findById(customerId).orElse(null);
	 }
	 
	public String getCustomerEmail(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);
        //System.out.println(customerID);
        return (customer != null) ? customer.getEmail() : null;
    }
}
