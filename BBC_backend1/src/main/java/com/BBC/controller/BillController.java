package com.BBC.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BBC.DTO.BillDTO;
import com.BBC.service.BillService;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/customers/{customerId}/bills")
    public List<BillDTO> getBillsByCustomerId(@PathVariable Long customerId) {
        return billService.getBillsByCustomerId(customerId);
    }
    
    @GetMapping("payment/bills/{billId}")
    public BillDTO getBillByBillId(@PathVariable Long billId) {
        return billService.getBillByBillId(billId);
    }
}
