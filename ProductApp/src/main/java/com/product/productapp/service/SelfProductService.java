package com.product.productapp.service;

import com.product.productapp.dto.CategoryDTO;
import com.product.productapp.dto.ProductDTO;
import com.product.productapp.dto.Titles;
import com.product.productapp.exception.ProductServiceExceptions;
import com.product.productapp.model.Category;
import com.product.productapp.model.Product;
import com.product.productapp.projection.ProductProjection;
import com.product.productapp.repository.CategoryRepository;
import com.product.productapp.repository.ProductRepository;
import com.product.productapp.repository.ProductServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfApiService")
public class SelfProductService implements ProductServiceRepository {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> list = products.stream().map(this::convertProductToProductDTO).toList();
        return ResponseEntity.ok(list);
    }


    @Override
    public ResponseEntity<ProductDTO> getProductById(Long id) {
        ProductDTO productDTO = convertProductToProductDTO(
                productRepository.findById(id).orElseThrow(() -> new ProductServiceExceptions.BadRequestException("Product not found"))
        );
        return ResponseEntity.ok(productDTO);
    }



    @Override
    public ResponseEntity<ProductDTO> saveProduct(ProductDTO productDTO) {
        Long id = productDTO.getId();
        if(id != null) {
            Optional<Product> product = productRepository.findById(id);
            if(!product.isPresent()) {
                throw new ProductServiceExceptions.BadRequestException("Product id not found");
            }
        }

        Product product = new Product();
        product.setId(id);

        String title = Optional.ofNullable(productDTO.getTitle())
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ProductServiceExceptions.BadRequestException("Product title not given"));

        String categoryTitle = Optional.ofNullable(productDTO.getCategory())
                .map(CategoryDTO::getTitle)
                .filter(titleVal -> !titleVal.isEmpty())
                .orElseThrow(() -> new ProductServiceExceptions.BadRequestException("Category title not given"));
        Optional<Category> categoryInfo = categoryRepository.findCategoriesByTitle(categoryTitle).stream().findAny();
        if(!categoryInfo.isPresent()){
            throw new ProductServiceExceptions.BadRequestException("Category not found");
        }

        product.setTitle(title);
        product.setCategory(categoryInfo.get());
        Product save = productRepository.save(product);
        ProductDTO productDTORes = convertProductToProductDTO(save);
        return ResponseEntity.ok(productDTORes);
    }

    @Override
    public ResponseEntity<ProductDTO> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories.stream().map(this::convertCategoryToCategoryDTO).toList());
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return ResponseEntity.ok(convertCategoryToCategoryDTO(category.orElseThrow(() -> new ProductServiceExceptions.BadRequestException("Category not found"))));
    }

    @Override
    public ResponseEntity<CategoryDTO> saveCategory(CategoryDTO categoryDto) {
        Long id = categoryDto.getId();
        if(id != null) {
            Optional<Category> category = categoryRepository.findById(id);
            if(!category.isPresent()) {
                throw new ProductServiceExceptions.BadRequestException("Category id not found");
            }
        }
        String categoryTitle = Optional.ofNullable(categoryDto.getTitle())
                .filter(titleVal -> !titleVal.isEmpty())
                .orElseThrow(() -> new ProductServiceExceptions.BadRequestException("Category title not given"));
        List<Category> categories = categoryRepository.findAll();
        boolean titleAlreadyPresent = categories.stream().anyMatch(s -> s.getTitle().equals(categoryDto.getTitle()));
        if(titleAlreadyPresent) {
            throw new ProductServiceExceptions.BadRequestException("Category title already exist");
        }
        Category category = new Category();
        category.setTitle(categoryTitle);

        Category categoryRes = categoryRepository.save(category);
        return ResponseEntity.ok(convertCategoryToCategoryDTO(categoryRes));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Titles>> getTitleNames(String title) {
        List<ProductProjection> productProjection = productRepository.getProductAndCategoryTitleByProductTitle(title);
        List<Titles> titles = productProjection.stream().map(pp -> {
            String pT = pp.getTitle();
            Titles titleSet = new Titles();
            titleSet.setProductTitle(pT);
            titleSet.setCatalogTitle(pp.getCategory().getTitle());
            return titleSet;
        }).toList();
        return ResponseEntity.ok(titles);
    }

    ProductDTO convertProductToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                new CategoryDTO(product.getCategory().getId(), product.getCategory().getTitle())
        );
    }

    CategoryDTO convertCategoryToCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getTitle());
    }
}
