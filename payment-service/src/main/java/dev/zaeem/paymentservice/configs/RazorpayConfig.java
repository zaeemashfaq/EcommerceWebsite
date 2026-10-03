package dev.zaeem.paymentservice.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This class is used to create a Razorpay client bean at the time of app startup.
// This bean will be injected to the razorpay payment gateway when required.
@Configuration
public class RazorpayConfig {
    @Value("${razorpay.key.id}")
    private String razorPayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorPayKeySecret;
    @Bean
    public RazorpayClient createRazorPayClient() throws RazorpayException {
        return new RazorpayClient(razorPayKeyId,razorPayKeySecret);

    }

}
