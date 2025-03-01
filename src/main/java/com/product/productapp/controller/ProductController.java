package com.product.productapp.controller;

import com.product.productapp.dto.ProductDTO;
import com.product.productapp.dto.Titles;
import com.product.productapp.projection.ProductProjection;
import com.product.productapp.repository.ProductServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private final ProductServiceRepository productServiceRepository;
    //fakeApiService or selfApiService
    public ProductController(@Qualifier("selfApiService") ProductServiceRepository productServiceRepository) {
        this.productServiceRepository = productServiceRepository;
    }

   @GetMapping("/products")
   public ResponseEntity<List<ProductDTO>> getAllProducts(){
       return productServiceRepository.getAllProduct();
   }

   @GetMapping("products/{id}")
   public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id){
        return productServiceRepository.getProductById(id);
   }

   @GetMapping("/pro/{title}")
   public ResponseEntity<List<Titles>> get(@PathVariable("title") String title){
    return productServiceRepository.gg(title);

   }

   @PostMapping("product")
   public ResponseEntity<ProductDTO> createOrUpdateProduct(@RequestBody ProductDTO productDTO){
        return productServiceRepository.saveProduct(productDTO);
   }

   @DeleteMapping("products/{id}")
   public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Long id){
        return productServiceRepository.deleteProduct(id);
   }

}
