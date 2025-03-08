package com.product.paymentapp.controller;

import com.product.paymentapp.dto.PaymentInitDTO;
import com.product.paymentapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("init")
    public String initPayment(@RequestBody PaymentInitDTO request){
        return paymentService.initPayment(request.getName(), request.getPhone(), request.getEmail(), request.getOrderId(), request.getAmount());
    }

    //please make sure you created webhook event in the stripe payment test account - https://dashboard.stripe.com/test/workbench/webhooks
    @PostMapping("/webhook")
    public ResponseEntity<Object>   receiveWebhook(@RequestBody String request){
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.OK).body(null
        );
    }
}
