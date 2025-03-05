package com.product.productapp.repository;

import com.product.productapp.model.Product;
import com.product.productapp.projection.ProductProjection;
import lombok.NonNull;
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
    Optional<Product> findById(Long id);


    @Override
    <S extends Product> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Query("select p.title, p.category.title from  Product p where p.title=:title")
    List<ProductProjection> gg(@Param("title") String title);

    @Query("select p.title as title, p.category as category from  Product p where p.title=:title")
    List<ProductProjection> hh(@Param("title") String title);


}

