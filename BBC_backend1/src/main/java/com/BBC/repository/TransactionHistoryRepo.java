package com.BBC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BBC.model.TransactionHistory;

@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, Long>{
	
	 List<TransactionHistory> findByBill_Customer_CustomerId(Long customerId);
//	List<TransactionHistory> findByBill_CustomerID(Long CustomerID);
}
