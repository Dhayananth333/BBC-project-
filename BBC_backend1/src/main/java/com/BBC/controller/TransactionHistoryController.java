package com.BBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BBC.model.TransactionHistory;
import com.BBC.service.TransactionHistoryService;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionHistoryController {
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@GetMapping("/customers/{customerId}/bills/{billId}/transactions")
	public List<TransactionHistory> getTransactionHistoryByCustomer(@PathVariable Long customerId) {
	    return transactionHistoryService.getTransactionHistoryByCustomer(customerId);
	}
}
