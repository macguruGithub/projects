package com.product.productapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String title;

    @JsonProperty("category")
    private CategoryDTO category;

    public ProductDTO(Long id, String title, CategoryDTO category) {
        this.id = id;
        this.title = title;
        this.category = category;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

}

