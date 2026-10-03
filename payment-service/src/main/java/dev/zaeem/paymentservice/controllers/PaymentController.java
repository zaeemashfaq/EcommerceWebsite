package dev.zaeem.paymentservice.controllers;

import dev.zaeem.paymentservice.dtos.PaymentRequestDto;
import dev.zaeem.paymentservice.paymentgateway.PaymentGateway;
import dev.zaeem.paymentservice.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/")
    public ResponseEntity<String> intiatePayment(@RequestBody PaymentRequestDto requestDto){
        return paymentService.getPaymentLink(requestDto);
    }

    @GetMapping("/thankyou")
    public String completedPayment(){
        return "Thank you for your payment!";
    }
}
