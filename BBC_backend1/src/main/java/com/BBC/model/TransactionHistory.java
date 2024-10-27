package com.BBC.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_ref")
    private Long transaction_ref;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;

    @Column(name = "payment_status", nullable = false, length = 10)
    private String paymentStatus;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "net_amount", nullable = false)
    private double netAmount;
    
    @Column( name = "customer_name")
    @Transient
    private String name;
    
    

	public TransactionHistory(Long transaction_ref, Bill bill, LocalDate paymentDate, double amount, String paymentMethod,
			String paymentStatus, double discount, double netAmount) {
		this.transaction_ref = transaction_ref;
		this.bill = bill;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.discount = discount;
		this.netAmount = netAmount;
	}

	public TransactionHistory() {}

	public Long getTransaction_ref() {
		return transaction_ref;
	}

	public void setTransaction_ref(Long transaction_ref) {
		this.transaction_ref = transaction_ref;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate currentDate) {
		this.paymentDate = currentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	
	
	

    public String getCustomerName() {
        return this.bill.getCustomer().getName();
    }

	public void setName(String name) {
		this.name = name;
	}
    
    
}