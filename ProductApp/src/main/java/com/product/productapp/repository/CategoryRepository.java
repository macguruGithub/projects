package com.product.productapp.repository;

import com.product.productapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    Optional<Category> findById(Long aLong);



    @Override
    List<Category> findAll();

    @Override
    <S extends Category> S save(S entity);

    @Override
    void deleteById(Long aLong);

    List<Category> findCategoriesByTitle(String title);

    boolean existsCategoriesByTitle(String title);
}
