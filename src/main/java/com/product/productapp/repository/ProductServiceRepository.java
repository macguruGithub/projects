package com.product.productapp.repository;

import com.product.productapp.dto.CategoryDTO;
import com.product.productapp.dto.ProductDTO;
import com.product.productapp.dto.Titles;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductServiceRepository {
    public ResponseEntity<List<ProductDTO>> getAllProduct();

    public ResponseEntity<ProductDTO> getProductById(Long id);

    public ResponseEntity<ProductDTO> saveProduct(ProductDTO product);

    public ResponseEntity<ProductDTO> deleteProduct(Long id);

    public ResponseEntity<List<CategoryDTO>> getAllCategories();

    public ResponseEntity<CategoryDTO> getCategoryById(Long id);

    public ResponseEntity<CategoryDTO> saveCategory(CategoryDTO category);

    public ResponseEntity<Void> deleteCategory(Long id);

    public ResponseEntity<List<Titles>> gg(String title);

}
