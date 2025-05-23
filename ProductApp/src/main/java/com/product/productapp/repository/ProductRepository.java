package com.product.productapp.repository;

import com.product.productapp.model.Product;
import com.product.productapp.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    List<Product> findAll();

    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    Optional<Product> findById(Long id);


    @Override
    <S extends Product> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Query("select p.title as title, p.category as category from  Product p where p.title=:title")
    List<ProductProjection> getProductAndCategoryTitleByProductTitle(@Param("title") String title);


}

