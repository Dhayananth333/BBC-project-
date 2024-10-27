package com.BBC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.BBC.model.Customer;
import com.BBC.model.PaymentMethod;
import java.util.Optional;

@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {

    Optional<PaymentMethod> findByCustomerAndCardNumberAndExpiryDateAndCvvAndIsActive(
        Customer customerId, 
        String cardNumber, 
        String expiryDate, 
        String cvv, 
        Boolean isActive
    );
   

    Optional<PaymentMethod> findByCustomerCustomerIdAndCardNumberStartsWith(Long customerId, String cardNumberFirst14Digits);

    

}
