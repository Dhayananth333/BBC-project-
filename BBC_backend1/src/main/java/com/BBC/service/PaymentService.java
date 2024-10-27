package com.BBC.service;

import com.BBC.model.Bill;
import com.BBC.model.Customer;
import com.BBC.model.PaymentMethod;
import com.BBC.model.TransactionHistory;
import com.BBC.repository.BillRepo;
import com.BBC.repository.PaymentMethodRepo;
import com.BBC.repository.TransactionHistoryRepo;
import com.itextpdf.text.DocumentException;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentReceiptPdfService paymentReceiptPdfService;
	
    private final PaymentMethodRepo paymentMethodRepo;
    private final BillRepo billRepository;
    private final TransactionHistoryRepo transactionHistoryRepo;
    private final OtpService otpService;
    
    protected double discount;
    protected double finalAmount;

    public PaymentService(PaymentMethodRepo paymentMethodRepo, BillRepo billRepository,
                          OtpService otpService, TransactionHistoryRepo transactionHistoryRepo) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.billRepository = billRepository;
        this.otpService = otpService;
        this.transactionHistoryRepo = transactionHistoryRepo;
    }

    public String verifyCardDetails(Customer customer, String cardNumber, String expiryDate, String cvv) {
        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            return "Please enter all the card details.";
        }

        Optional<PaymentMethod> paymentMethod = paymentMethodRepo.findByCustomerAndCardNumberAndExpiryDateAndCvvAndIsActive(
                customer, cardNumber, expiryDate, cvv, true);

        if (paymentMethod.isEmpty()) {
            Optional<PaymentMethod> inactivePaymentMethod = paymentMethodRepo.findByCustomerAndCardNumberAndExpiryDateAndCvvAndIsActive(
                    customer, cardNumber, expiryDate, cvv, false);
            if (inactivePaymentMethod.isPresent()) {
                return "Card is declined.";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            Date expiry;
            try {
                expiry = sdf.parse(expiryDate);
            } catch (ParseException e) {
                return "Invalid expiry date format. Please use MM/YY.";
            }

            Date now = new Date();
            if (expiry.before(now)) {
                return "Card has expired / Invalid Card details";
            }

            return "Invalid card details. Please check and try again.";
        }

        //otpService.generateAndSendOtpForCardVerification(customer.getCustomerId(), cardNumber);
        return "Card details verified successfully. An OTP has been sent for verification.";
    }

    public String processPayment(Long customerId, Long billId, double amount, String cardNumber) {
        
        String cardNumberFirst14Digits = cardNumber.substring(0, 14);

        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepo.findByCustomerCustomerIdAndCardNumberStartsWith(
            customerId, cardNumberFirst14Digits
        );

        if (paymentMethodOptional.isEmpty()) {
            return "No active payment method found for the provided card number.";
        }

        PaymentMethod paymentMethod = paymentMethodOptional.get();
        double cardBalance = paymentMethod.getBalance();

        if (cardBalance < amount) {
            return "Insufficient balance. Please add funds to your card.";
        }

        paymentMethod.setBalance(cardBalance - amount);
        paymentMethodRepo.save(paymentMethod);

        Optional<Bill> billOptional = billRepository.findById(billId);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            double discount = calculateDiscount(amount, bill.getBillDueDate());
            double finalAmount = amount - discount;

            LocalDate currentDate = LocalDate.now();

            bill.setBillStatus("Paid");
            billRepository.save(bill);

            TransactionHistory transaction = new TransactionHistory();
            transaction.setBill(bill);
            transaction.setPaymentDate(currentDate);
            transaction.setAmount(amount);
            transaction.setPaymentMethod(paymentMethod.getCardType());
            transaction.setPaymentStatus("Success");
            transaction.setDiscount(discount);
            transaction.setNetAmount(finalAmount);

            transactionHistoryRepo.save(transaction);
            
            
            try {
				paymentReceiptPdfService.sendPaymentReceiptAsPdf(customerId, bill, paymentMethod, discount, finalAmount);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            
            return "Payment successful. Your card has been charged.";
        }

        return "Bill not found.";
    }
    
    private double calculateDiscount(double amount, LocalDate dueDate) {
    	LocalDate currentDate = LocalDate.now();
    	if (currentDate.isBefore(dueDate) || currentDate.isEqual(dueDate)) {
    		return amount * 0.10; 
    	} else {
    		return amount * 0.05; 
    	}
    }

    
}


