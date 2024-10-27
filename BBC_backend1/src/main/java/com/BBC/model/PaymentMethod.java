package com.BBC.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "customer_id", referencedColumnName = "customerID", nullable = false)
    private Customer customer; 

	@Column(nullable = false)
    private String cardType;
    

	@Column(nullable = false)
    private String cardNumber;

    private String cardHolderName;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    private boolean isActive = true;
    
    private double balance;


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public double getBalance() {
    	return balance;
    }
    
    public void setBalance(double balance) {
    	this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public String getCardType() {
    	return cardType;
    }
    
    public void setCardType(String cardType) {
    	this.cardType = cardType;
    }
    
}
