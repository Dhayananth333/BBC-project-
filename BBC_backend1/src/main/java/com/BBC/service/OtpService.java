package com.BBC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private CustomerService customerService;

    private Map<Long, String> otpStore = new HashMap<>();
    private Map<Long, Timer> otpTimers = new HashMap<>();
    private static final long OTP_EXPIRY_TIME = 90 * 1000;

    public void generateAndSendOtp(Long customerID) {
        String otp = generateOtp();
        System.out.println("OTP : " + otp);
        otpStore.put(customerID, otp);
        sendOtpEmail(customerID, otp,true);
        startOtpTimer(customerID);
    }

    private String generateOtp() {

        int otp = 100000 + new Random().nextInt(900000); 
        
        return String.valueOf(otp); 
    }


    private void sendOtpEmail(Long customerID, String otp, boolean isForLogin) {
    
        String customerEmail = getCustomerEmail(customerID);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dhayawork333@gmail.com");
        message.setTo(customerEmail);

        if (isForLogin) {
            message.setSubject("Your OTP Code for BBC Login");
            message.setText("Your OTP for logging into your account is: " + otp);
            System.out.println("login otp : "+ otp);
        } else {
            message.setSubject("Your OTP Code for BBC Payment Verification");
            message.setText("Your OTP for verifying your payment is: " + otp);
            System.out.println("payment otp : "+ otp);
        }
        
        //mailSender.send(message);
    }

    private void startOtpTimer(Long customerID) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clearOtp(customerID);
            }
        }, OTP_EXPIRY_TIME);
        otpTimers.put(customerID, timer);
    }

    public boolean validateOtp(Long customerID, String otp) {
        String storedOtp = otpStore.get(customerID);
        //System.out.println(storedOtp);
        return storedOtp != null && storedOtp.equals(otp);
    }

    public void clearOtp(Long customerID) {
        otpStore.remove(customerID);
        Timer timer = otpTimers.remove(customerID);
        if (timer != null) {
            timer.cancel();
        } 
    }

    private String getCustomerEmail(Long customerID) {
        return customerService.getCustomerEmail(customerID);
    }
   
    public void generateAndSendOtpForCardVerification(Long customerID, String cardNumber) {
        String otp = generateOtp();
        System.out.println("OTP for card " + cardNumber + ": " + otp);
        otpStore.put(customerID, otp);
        sendOtpEmail(customerID, otp, false);
        startOtpTimer(customerID);
    }
}
