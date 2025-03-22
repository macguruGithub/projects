package com.product.productapp.controller;

import com.product.productapp.dto.ProductDTO;
import com.product.productapp.dto.Titles;
import com.product.productapp.repository.ProductServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private final ProductServiceRepository productServiceRepository;
    //fakeApiService or selfApiService
    public ProductController(@Qualifier("selfApiService") ProductServiceRepository productServiceRepository) {
        this.productServiceRepository = productServiceRepository;
    }

   @GetMapping("/products/{pageIndex}/{pageSize}")
   @Cacheable(value = "products", key = "#pageIndex")
   public List<ProductDTO> getAllProducts(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return productServiceRepository.getAllProduct(pageIndex, pageSize).getBody();
   }

   @GetMapping("products/{id}")
   @Cacheable(value = "product", key = "#id")
   public ProductDTO getProductById(@PathVariable("id") Long id){
        return productServiceRepository.getProductById(id).getBody();
   }

   @GetMapping("/pro/{title}")
   public ResponseEntity<List<Titles>> get(@PathVariable("title") String title){
    return productServiceRepository.getTitleNames(title);

   }

   @PostMapping("product")
   @CachePut(value = "product", key="#result.title")
   public ProductDTO createOrUpdateProduct(@RequestBody ProductDTO productDTO){
        return productServiceRepository.saveProduct(productDTO).getBody();
   }

   @DeleteMapping("products/{id}")
   public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("id") Long id){
        return productServiceRepository.deleteProduct(id);
   }

}
