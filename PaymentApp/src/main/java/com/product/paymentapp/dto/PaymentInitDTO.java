package com.product.paymentapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PaymentInitDTO {
    private String name;
    private String email;
    private String phone;
    private String orderId;
    private int amount;

}
