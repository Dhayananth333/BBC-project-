package com.BBC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BBC.model.TransactionHistory;
import com.BBC.repository.TransactionHistoryRepo;

@Service
public class TransactionHistoryService {
	
	 @Autowired
	    private TransactionHistoryRepo transactionHistoryRepo;

	    public List<TransactionHistory> getTransactionHistoryByCustomer(Long customerId) {
//	    	System.out.println("CustomerID: " + customerId);
			return transactionHistoryRepo.findByBill_Customer_CustomerId(customerId);
		}
	
}
