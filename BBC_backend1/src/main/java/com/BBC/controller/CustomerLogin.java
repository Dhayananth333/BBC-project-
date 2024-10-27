package com.BBC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.BBC.model.Customer;
import com.BBC.service.CustomerService;
import com.BBC.service.OtpService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerLogin {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OtpService otpService;

    @GetMapping("/verify/{customerId}")
    public boolean verifyCustomer(@PathVariable Long customerId) {
        boolean exists = customerService.checkCustomerExists(customerId);
        if (exists) {
            otpService.generateAndSendOtp(customerId);
        }
        return exists;
    }

    @PostMapping("/validateOtp/{customerId}")
    public boolean validateOtp(@PathVariable Long customerId, @RequestBody String otp) {
        return otpService.validateOtp(customerId, otp);
    }

    @PostMapping("/clearOtp/{customerId}")
    public void clearOtp(@PathVariable Long customerId) {
        otpService.clearOtp(customerId);
        
    }
    
    @GetMapping("/customer/{customerId}")
    public Customer getCustomerDetails(@PathVariable Long customerId) {
        return customerService.getCustomerDetails(customerId);
    }
}
