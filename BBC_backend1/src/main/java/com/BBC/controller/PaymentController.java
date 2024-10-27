package com.BBC.controller;

import com.BBC.DTO.CardVerificationRequest;
import com.BBC.DTO.PaymentRequestDTO;
import com.BBC.DTO.VerificationResponse; // Import your new DTO
import com.BBC.service.BillService;
import com.BBC.service.OtpService; // Import your OtpService
import com.BBC.model.Customer;
import com.BBC.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;
    private final OtpService otpService;
    private final BillService billService;

    public PaymentController(PaymentService paymentService, OtpService otpService, BillService billService) {
        this.paymentService = paymentService;
        this.otpService = otpService;
        this.billService = billService;
    }

    @PostMapping("/verify-card")
    public ResponseEntity<VerificationResponse> verifyCard(@RequestBody CardVerificationRequest request) {
        Customer customer = new Customer();
        customer.setCustomerId(request.getCustomerId());

        String verificationResult = paymentService.verifyCardDetails(
            customer,
            request.getCardNumber(),
            request.getExpiryDate(),
            request.getCvv()
        );

        VerificationResponse response;
        if (verificationResult.equals("Card details verified successfully. An OTP has been sent for verification.")) {
            otpService.generateAndSendOtpForCardVerification(customer.getCustomerId(), request.getCardNumber());
            response = new VerificationResponse("success", "Card details verified successfully. OTP sent to the Mail.");
            return ResponseEntity.ok(response);
        } else {
            response = new VerificationResponse("error", verificationResult);
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/send-otp/{customerId}")
    public ResponseEntity<String> sendOtp(@PathVariable Long customerId) {
        otpService.generateAndSendOtp(customerId);
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/validate-otp/{customerId}")
    public ResponseEntity<VerificationResponse> validateOtp(
            @PathVariable Long customerId, 
            @RequestParam String otp) {

        boolean isValid = otpService.validateOtp(customerId, otp);
        VerificationResponse response;

        if (isValid) {
            response = new VerificationResponse("success", "OTP verified & Payment Successful, Check mail for receipt");
            return ResponseEntity.ok(response);
        } else {
            response = new VerificationResponse("error", "OTP expired or invalid.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    
    @PostMapping("/process-payment")
    public ResponseEntity<VerificationResponse> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
    	//System.out.println("Customer Id  from controller: "+paymentRequest.getCustomerId().getClass().getSimpleName());
        String result = paymentService.processPayment(
            paymentRequest.getCustomerId(),
            paymentRequest.getBillId(),
            paymentRequest.getAmount(),
            paymentRequest.getCardNumber()
        );
        
        VerificationResponse response;
        if (result.equals("Payment successful. Your card has been charged.")) {
            response = new VerificationResponse("success", "Payment successful, Check mail for receipt.");
            return ResponseEntity.ok(response);
        } else {
            response = new VerificationResponse("error", result);
            return ResponseEntity.badRequest().body(response);
        }
    }

    
    

}
