package com.BBC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "customer_details")

public class Customer {
	@Id
	@Column(name = "customerid", unique = true)
    private Long customerId;
	
	@Column(name = "name", nullable = false)
	private String customerName;
	 
	@Column(name = "email", nullable = false)
	private String email;
	 
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "wallet", nullable = false)
    private double wallet;
    
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	
	public Customer() {
	}
	public Customer(Long customerId, String name, String email, String mobileNumber, String address, double wallet) {
		this.customerId = customerId;
		this.customerName = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.wallet = wallet;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return customerName;
	}
	public void setName(String name) {
		this.customerName = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "CustomerId: " + customerId;
	}


}
