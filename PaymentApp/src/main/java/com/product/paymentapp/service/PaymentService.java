package com.product.paymentapp.service;

public interface PaymentService {
    public String initPayment(
            String name,
            String phoneNumber,
            String email,
            String orderId,
            int amount
            );
}
