package com.BBC.DTO;

import jakarta.annotation.Nonnull;

public class CardVerificationRequest {
    @Nonnull
    private Long customerId;
    @Nonnull
    private String cardNumber;
    @Nonnull
    private String expiryDate;
    @Nonnull
    private String cvv;
    
    public Long getCustomerId() {
        return customerId;
    }
    
    
    public CardVerificationRequest(Long customerId, String cardNumber, String expiryDate, String cvv) {
		
		this.customerId = customerId;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
	}

	public CardVerificationRequest() {
		
	}

	public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
}
