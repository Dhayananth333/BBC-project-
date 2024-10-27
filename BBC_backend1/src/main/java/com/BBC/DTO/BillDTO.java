package com.BBC.DTO;

import java.time.LocalDate;

public class BillDTO {
    private Long billId;
    private Integer unitConsumption;
    private LocalDate billingStartDate;
    private LocalDate billingEndDate;
    private LocalDate billDueDate;
	private Double amountDue;
    private String billStatus;

    public BillDTO() {
    }

    // Constructor
    public BillDTO(Long billId, Integer unitConsumption, LocalDate billingStartDate, LocalDate billingEndDate, LocalDate billDueDate, Double amountDue, String billStatus) {
        this.billId = billId;
        this.unitConsumption = unitConsumption;
        this.billingStartDate = billingStartDate;
        this.billingEndDate = billingEndDate;
        this.billDueDate = billDueDate;
        this.amountDue = amountDue;
        this.billStatus = billStatus;
        
    }
    
    public BillDTO(Long billId, Integer unitConsumption,LocalDate billingStartDate, LocalDate billDueDate, Double amount1Due, Double amountDue) {
    	this.billId = billId;
    	this.unitConsumption = unitConsumption;
    	this.billingStartDate = billingStartDate;
    	this.billDueDate = billDueDate;
    	this.amountDue = amount1Due;
    	this.amountDue = amountDue;
    	
    }

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Integer getUnitConsumption() {
		return unitConsumption;
	}

	public void setUnitConsumption(Integer unitConsumption) {
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

	public Double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Double amountDue) {
		this.amountDue = amountDue;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	
}
