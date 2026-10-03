package dev.zaeem.paymentservice.disabled;
import com.razorpay.PaymentLink;
import dev.zaeem.paymentservice.paymentgateway.PaymentGateway;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RazorpayPaymentGateway implements PaymentGateway {
    RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }
    @Override
    public String generatePaymentLink(String orderId, String name,String email,
                                      String phoneNo, Long amount)  {
        try{
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");
            paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",100);
            paymentLinkRequest.put("expire_by", (Instant.now().toEpochMilli()+300000)/1000);
            paymentLinkRequest.put("reference_id",orderId);
            paymentLinkRequest.put("description","Payment for policy no #23456");
            JSONObject customer = new JSONObject();
            customer.put("name",phoneNo);
            customer.put("contact",name);
            customer.put("email",email);
            paymentLinkRequest.put("customer",customer);
            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
            paymentLinkRequest.put("reminder_enable",true);
            JSONObject notes = new JSONObject();
            notes.put("policy_name","Jeevan Bima");
            paymentLinkRequest.put("notes",notes);
            paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.toString();
        }
        catch (Exception e){
            System.out.println(e.toString());
            return "Exception has occurred!";
        }
    }
}
