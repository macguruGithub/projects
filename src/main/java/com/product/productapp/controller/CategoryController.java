package com.product.productapp.controller;

import com.product.productapp.dto.CategoryDTO;
import com.product.productapp.repository.ProductServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final ProductServiceRepository productServiceRepository;
    //fakeApiService or selfApiService
    public CategoryController(@Qualifier("selfApiService") ProductServiceRepository productServiceRepository) {
        this.productServiceRepository = productServiceRepository;
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return productServiceRepository.getAllCategories();
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        return productServiceRepository.getCategoryById(id);
    }

    @PostMapping("category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return productServiceRepository.saveCategory(categoryDTO);
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        return productServiceRepository.deleteCategory(id);
    }
}
