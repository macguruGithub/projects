package com.product.paymentapp.configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Value("${razorpay.key.id}")
    private String apiKey;

    @Value("${razorpay.key.secret}")
    private String apiSecret;

    @Bean
    public RazorpayClient razorpayClient() {
        try {
            return new RazorpayClient(apiKey, apiSecret);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }


}
