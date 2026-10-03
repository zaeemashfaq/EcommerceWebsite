package dev.zaeem.paymentservice.paymentgateway;

import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import dev.zaeem.paymentservice.models.EventStatus;
import dev.zaeem.paymentservice.models.PaymentEvent;
import dev.zaeem.paymentservice.repository.PaymentEventRepository;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentGateway implements PaymentGateway{
    PaymentEventRepository paymentEventRepository;
    @Value("${stripe.key.secret}")
    String apiKey;
    public StripePaymentGateway(PaymentEventRepository paymentEventRepository){
        this.paymentEventRepository = paymentEventRepository;
    }
    @Override
    public String generatePaymentLink(String orderId, String productName, String name, String email,
                                                     String phoneNo, Long amount) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(orderId);
        paymentEvent.setStatus(EventStatus.PAYMENT_LINK_REQUESTED);
        paymentEventRepository.save(paymentEvent);
        try{
            // Set your secret key. Remember to switch to your live secret key in production.
// See your keys here: https://dashboard.stripe.com/apikeys

            Stripe.apiKey = apiKey;
            PriceCreateParams priceParams =
                    PriceCreateParams.builder()
                            .setCurrency("inr")
                            .setUnitAmount(amount)
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName(productName).build()
                            )
                            .build();
            Price price = Price.create(priceParams);
            String priceId = price.getId();
            /*Map<String,Object> redirect = new HashMap<>();
            redirect.put("url","https://www.scaler.com/");
            Map<String,Object> afterCompletion = new HashMap<>();
            afterCompletion.put("type","redirect");
            afterCompletion.put("redirect",redirect);*/
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
//                                            .setPrice("{{PRICE_ID}}")
                                            .setPrice(priceId)
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setAfterCompletion(
                                    PaymentLinkCreateParams.AfterCompletion.builder()
                                            .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                            .setRedirect(
                                                    PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
//                                                            .setUrl("https://www.scaler.com/")
                                                            .setUrl("http://localhost:8080/payments/thankyou")
                                                            .build()
                                            )
                                            .build()
                            )
                            .build();
//            params.put("afterCompletion",afterCompletion);

            PaymentLink paymentLink = PaymentLink.create(params);
            paymentEvent.setStripePaymentId(paymentLink.getId());
            paymentEventRepository.save(paymentEvent);
            System.out.println("Payment Link URL: "+paymentLink.getUrl());
            System.out.println("Stripe id: "+paymentLink.getId());
            ResponseEntity<String> response = new ResponseEntity<>(paymentLink.getUrl(), HttpStatus.OK);
            return paymentLink.getUrl();
        }
        catch (Exception e){
            paymentEvent.setStatus(EventStatus.PAYMENT_LINK_FAILED_TO_REQUEST);
            paymentEventRepository.save(paymentEvent);
            System.out.println("An exception has occurred: "+e.toString());
            return e.toString();
        }
    }
}
