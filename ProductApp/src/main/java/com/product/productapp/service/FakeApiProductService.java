package com.product.productapp.service;

import com.product.productapp.dto.CategoryDTO;
import com.product.productapp.dto.ProductDTO;
import com.product.productapp.dto.ProductFakeApiDTO;
import com.product.productapp.dto.Titles;
import com.product.productapp.projection.ProductProjection;
import com.product.productapp.repository.ProductServiceRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("fakeApiService")
public class FakeApiProductService implements ProductServiceRepository {
    private RestTemplate restTemplate;

    public FakeApiProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct(int pageIndex, int pageSize) {
        ProductFakeApiDTO[] forObject = restTemplate.getForObject("https://fakestoreapi.com/products", ProductFakeApiDTO[].class);
        ProductFakeApiDTO[] products = Optional.ofNullable(forObject).orElse(new ProductFakeApiDTO[]{});
        List<ProductFakeApiDTO> fakeAPIRes = Arrays.stream(products).toList();
        List<ProductDTO> productDTOS = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        fakeAPIRes.forEach(productFakeApiDTO -> {
            CategoryDTO categoryDTO = new CategoryDTO((long)counter.incrementAndGet(), productFakeApiDTO.getCategory());
            productDTOS.add(new ProductDTO(productFakeApiDTO.getId(), productFakeApiDTO.getTitle(), categoryDTO));
        });
        return ResponseEntity.ok(productDTOS);
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(Long id) {
        ProductFakeApiDTO resp = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, ProductFakeApiDTO.class);
        ProductFakeApiDTO productFakeApiDTO = Optional.ofNullable(resp).orElse(new ProductFakeApiDTO());
        ProductDTO productDTO = covertToProductDTO(productFakeApiDTO);
        return ResponseEntity.ok(productDTO);
    }

    @Override
    public ResponseEntity<ProductDTO> saveProduct(ProductDTO product) {
        ProductFakeApiDTO productFakeApiDTOReq = null;
        if(product.getId() == null) {
            productFakeApiDTOReq = new ProductFakeApiDTO(product.getTitle(), product.getCategory().getTitle());
        }else{
            productFakeApiDTOReq = new ProductFakeApiDTO(product.getId(),product.getTitle(), product.getCategory().getTitle());
        }
        ProductFakeApiDTO productFakeApiDTO = restTemplate.postForObject("https://fakestoreapi.com/products", productFakeApiDTOReq, ProductFakeApiDTO.class);
        assert productFakeApiDTO != null;
        ProductDTO productDTO = covertToProductDTO(productFakeApiDTO);
        return ResponseEntity.ok(productDTO);
    }

    @Override
    public ResponseEntity<ProductDTO> deleteProduct(Long id) {
        ProductFakeApiDTO productFakeApiDTO = restTemplate.exchange(String.format("https://fakestoreapi.com/products/%s", id), HttpMethod.DELETE, null, ProductFakeApiDTO.class).getBody();
        assert productFakeApiDTO != null;
        ProductDTO productDTO = covertToProductDTO(productFakeApiDTO);
        return ResponseEntity.ok(productDTO);
    }

    private static ProductDTO covertToProductDTO(ProductFakeApiDTO productFakeApiDTO) {
        AtomicInteger counter = new AtomicInteger();
        assert productFakeApiDTO != null;
        CategoryDTO categoryDTO = new CategoryDTO((long)counter.incrementAndGet(), productFakeApiDTO.getCategory());
        return new ProductDTO(productFakeApiDTO.getId(), productFakeApiDTO.getTitle(), categoryDTO);
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        AtomicInteger counter = new AtomicInteger();
        List<CategoryDTO> categoryDTOList =
                Arrays.stream(Objects.requireNonNull(restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class)))
                .map((title) -> new CategoryDTO((long)counter.incrementAndGet(), title)).toList();
        return ResponseEntity.ok(categoryDTOList);
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<CategoryDTO> saveCategory(CategoryDTO category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseEntity<List<Titles>> getTitleNames(String title) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
