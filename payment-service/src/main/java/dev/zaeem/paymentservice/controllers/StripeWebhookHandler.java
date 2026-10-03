package dev.zaeem.paymentservice.controllers;

import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import dev.zaeem.paymentservice.models.EventStatus;
import dev.zaeem.paymentservice.models.PaymentEvent;
import dev.zaeem.paymentservice.repository.PaymentEventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/stripepaymenthook")
public class StripeWebhookHandler {
    PaymentEventRepository paymentEventRepository;
    // Replace this endpoint secret with your endpoint's unique secret
    // If you are testing with the CLI, find the secret by running 'stripe listen'
    // If you are using an endpoint defined with the API or dashboard, look in your webhook settings
    // at https://dashboard.stripe.com/webhooks
//    @Value("${stripe.key.secret}") String endpointSecret;
    public StripeWebhookHandler(PaymentEventRepository paymentEventRepository){
        this.paymentEventRepository = paymentEventRepository;
    }
    @PostMapping("/")
    public void receiveUpdate(@RequestBody String payload, @RequestHeader Map<String, String> headers ){
        System.out.println("Webhook request received!");
        Stripe.apiKey = System.getenv("STRIPE_KEY_SECRET");
        String endpointSecret = System.getenv("STRIPE_ENDPOINT_SECRET");
        Event event = null;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            System.out.println("⚠️  Webhook error while parsing basic request.");
//            response.status(400);
//            return "";
        }
        String sigHeader = headers.get("stripe-signature");
        if(endpointSecret != null && sigHeader != null) {
            // Only verify the event if you have an endpoint secret defined.
            // Otherwise use the basic event deserialized with GSON.
            try {
                event = Webhook.constructEvent(
                        payload, sigHeader, endpointSecret
                );
            } catch (SignatureVerificationException e) {
                // Invalid signature
                System.out.println("⚠️  Webhook error while validating signature.");
//                response.status(400);
//                return "";
            }
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                System.out.println("Deserialization failed!");
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            System.out.println("Event type: "+event.getType());
//            PaymentEvent paymentEvent = paymentEventRepository.findPaymentEventByStripePaymentId();
            // Handle the event
            switch (event.getType()) {
//                PaymentEvent paymentEvent = paymentEventRepository.findPaymentEventByOrderId()
                case "checkout.session.completed":
                    Session session = (Session)stripeObject;
                    System.out.println("Session id: "+session.getId());
                    Optional<PaymentEvent> paymentEventOptional =
                            paymentEventRepository.findPaymentEventByStripePaymentId(session.getId());
                    PaymentEvent paymentEvent = null;
                    if(!paymentEventOptional.isEmpty()){
                        paymentEvent = paymentEventOptional.get();
                        paymentEvent.setStatus(EventStatus.PAYMENT_SUCCEEDED);
                        paymentEventRepository.save(paymentEvent);
                    }
                    if(paymentEvent==null){
                        System.out.println("Could not find the payment event from stripe id!");
                    }
                    break;
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
                    // Then define and call a method to handle the successful payment intent.
                    // handlePaymentIntentSucceeded(paymentIntent);
                    break;
                case "payment_method.attached":
                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                    // Then define and call a method to handle the successful attachment of a PaymentMethod.
                    // handlePaymentMethodAttached(paymentMethod);
                    break;
                case "payment_link.created":
                    PaymentLink paymentLink = (PaymentLink) stripeObject;
                    String id = paymentLink.getId();
                    Optional<PaymentEvent> paymentEventOptional1 =
                            paymentEventRepository.findPaymentEventByStripePaymentId(id);
                    if(paymentEventOptional1.isEmpty()){
                        System.out.println("Could not find the payment event from stripe id!");
                        break;
                    }
                    PaymentEvent paymentEvent1 = paymentEventOptional1.get();
                    paymentEvent1.setStatus(EventStatus.PAYMENT_LINK_GENERATED);
                    paymentEventRepository.save(paymentEvent1);
                    System.out.println("Payment link has been created for order id: "+paymentEvent1.getOrderId());
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                    break;
            }
//            response.status(200);
//            return "";
        }
        else {
            System.out.println("Either endpointSecret is null or sigHeader is null");
        }
    }
}
