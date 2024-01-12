package dev.zaeem.paymentservice.paymentgateway;

import org.springframework.stereotype.Service;

@Service
public interface PaymentGateway {
    public String generatePaymentLink(String orderId, String productName, String name,String email,
                                      String phoneNo, Long amount);
}
