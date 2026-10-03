package dev.zaeem.paymentservice.services;

import dev.zaeem.paymentservice.dtos.PaymentRequestDto;
import dev.zaeem.paymentservice.paymentgateway.PaymentGateway;
import dev.zaeem.paymentservice.strategies.PaymentGatewayChooserStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;
    public PaymentService(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy){
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }
    public ResponseEntity<String> getPaymentLink(PaymentRequestDto requestDto){
        PaymentGateway paymentGateway = paymentGatewayChooserStrategy.getPaymentGateway();
        String url = paymentGateway.generatePaymentLink(requestDto.getOrderId(),requestDto.getProductName(),
                requestDto.getName(), requestDto.getEmail(), requestDto.getPhoneNo(), requestDto.getAmount());
        ResponseEntity<String> response = new ResponseEntity<>(url, HttpStatus.OK);
        return response;
    }
}
