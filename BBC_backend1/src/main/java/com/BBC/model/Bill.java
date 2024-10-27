package com.BBC.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "bill")

public class Bill {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customer;

    @Column(name = "unit_consumption", nullable = false)
    private int unitConsumption;

    @Column(name = "billing_start_date", nullable = false)
    private LocalDate billingStartDate;

    @Column(name = "billing_end_date", nullable = false)
    private LocalDate billingEndDate;

    @Column(name = "bill_due_date", nullable = false)
    private LocalDate billDueDate;

    @Column(name = "amount_due", nullable = false)
    private double amountDue;

    @Column(name = "billstatus", nullable = false)
    private String billStatus;

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getUnitConsumption() {
		return unitConsumption;
	}

	public void setUnitConsumption(int unitConsumption) {
		this.unitConsumption = unitConsumption;
	}

	public LocalDate getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(LocalDate billingStartDate) {
		this.billingStartDate = billingStartDate;
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public LocalDate getBillDueDate() {
		return billDueDate;
	}

	public void setBillDueDate(LocalDate billDueDate) {
		this.billDueDate = billDueDate;
	}

	public double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public Bill(Long billId, Customer customer, int unitConsumption, LocalDate billingStartDate,
			LocalDate billingEndDate, LocalDate billDueDate, double amountDue, String billStatus) {
		this.billId = billId;
		this.customer = customer;
		this.unitConsumption = unitConsumption;
		this.billingStartDate = billingStartDate;
		this.billingEndDate = billingEndDate;
		this.billDueDate = billDueDate;
		this.amountDue = amountDue;
		this.billStatus = billStatus;
	}
	public Bill() {
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", customer=" + customer + ", unitConsumption=" + unitConsumption
				+ ", billingStartDate=" + billingStartDate + ", billingEndDate=" + billingEndDate + ", billDueDate="
				+ billDueDate + ", amountDue=" + amountDue + ", billStatus=" + billStatus + "]";
	}    
}
