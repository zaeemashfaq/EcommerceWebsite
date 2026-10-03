package dev.zaeem.paymentservice.strategies;

import dev.zaeem.paymentservice.paymentgateway.PaymentGateway;
import dev.zaeem.paymentservice.paymentgateway.StripePaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayChooserStrategy {
//    private RazorpayPaymentGateway razorpayPaymentGateway;
    private StripePaymentGateway stripePaymentGateway;
    public PaymentGatewayChooserStrategy(StripePaymentGateway stripePaymentGateway){
//        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }
    public PaymentGateway getPaymentGateway(){
        return stripePaymentGateway;
    }
}
