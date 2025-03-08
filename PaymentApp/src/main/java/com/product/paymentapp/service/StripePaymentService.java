package com.product.paymentapp.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class StripePaymentService implements PaymentService {
    @Value("${stripepay.key}")
    private String apiKey;

    @Override
    public String initPayment(String name, String phoneNumber, String email, String orderId, int amount) {
        try {
            Stripe.apiKey = apiKey;
            PaymentLinkCreateParams params = PaymentLinkCreateParams.builder().addLineItem(PaymentLinkCreateParams.LineItem.builder().setPrice(createPrice(amount)).setQuantity(1L).build()).build();
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private String createPrice(Integer amount) throws StripeException {
        PriceCreateParams params = PriceCreateParams.builder().setCurrency("usd").setUnitAmount(Long.valueOf(amount)).setRecurring(PriceCreateParams.Recurring.builder().setInterval(PriceCreateParams.Recurring.Interval.MONTH).build()).setProductData(PriceCreateParams.ProductData.builder().setName("Gold Plan").build()).build();
        Price price = Price.create(params);
        return price.getId();
    }

}
