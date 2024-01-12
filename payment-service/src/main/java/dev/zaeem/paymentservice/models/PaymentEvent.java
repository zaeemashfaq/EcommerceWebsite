package dev.zaeem.paymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PaymentEvent extends BaseModel{
    String orderId;
    String stripePaymentId;
    @Enumerated(EnumType.STRING)
    EventStatus status;
}
