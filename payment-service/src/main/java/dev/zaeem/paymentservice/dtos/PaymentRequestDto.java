package dev.zaeem.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
public class PaymentRequestDto {
    private String orderId;
    private String productName;
    private String name;
    private String email;
    private String phoneNo;
    private long amount;
}
