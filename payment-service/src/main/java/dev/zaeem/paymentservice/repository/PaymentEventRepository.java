package dev.zaeem.paymentservice.repository;

import dev.zaeem.paymentservice.models.EventStatus;
import dev.zaeem.paymentservice.models.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentEventRepository extends JpaRepository<PaymentEvent,Long> {
    <S extends PaymentEvent> S save (S entity);
    public Optional<PaymentEvent> findPaymentEventByOrderId(String orderId);
    public Optional<PaymentEvent> findPaymentEventByStripePaymentId(String stripePaymentId);
}
