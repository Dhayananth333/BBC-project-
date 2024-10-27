package com.BBC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BBC.model.Bill;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    // Method to find bills by customer ID
    List<Bill> findByCustomer_CustomerId(Long customerId); 
}
