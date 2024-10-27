package com.BBC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BBC.DTO.BillDTO;
import com.BBC.model.Bill;
import com.BBC.repository.BillRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepo billRepo;

    public List<BillDTO> getBillsByCustomerId(Long customerId) {
        List<Bill> bills = billRepo.findByCustomer_CustomerId(customerId);
        return bills.stream().map(bill -> new BillDTO(
                bill.getBillId(),
                bill.getUnitConsumption(),
                bill.getBillingStartDate(),
                bill.getBillingEndDate(),
                bill.getBillDueDate(),
                bill.getAmountDue(),
                bill.getBillStatus()
        )).collect(Collectors.toList());
    }
    
    public BillDTO getBillByBillId(Long billId) {
        Bill bill = billRepo.findById(billId).orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        return new BillDTO(
                bill.getBillId(),
                bill.getUnitConsumption(),
                bill.getBillingStartDate(),
                bill.getBillDueDate(),
                bill.getAmountDue(),
                bill.getAmountDue()
        );
    }
}
