package com.product.productapp;

import com.product.productapp.model.Product;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProductAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductAppApplication.class, args);
    }



}
