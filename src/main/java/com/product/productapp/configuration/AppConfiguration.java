package com.product.productapp.configuration;

import com.product.productapp.model.Category;
import com.product.productapp.model.Product;
import com.product.productapp.projection.ProductProjection;
import com.product.productapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class AppConfiguration {

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            Product product = new Product();
            product.setTitle("Product");
            product.setCategory(new Category());
            List<ProductProjection> chiti = productRepository.hh("chiti");
            chiti.forEach(g -> {
                System.out.println(g.getTitle());
                System.out.println(g.getCategory().getTitle());
            });
        };
    }

}
