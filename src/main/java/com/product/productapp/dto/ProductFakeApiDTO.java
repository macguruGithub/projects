package com.product.productapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFakeApiDTO {
    private Long id;
    private String title;
    private String category;

    public ProductFakeApiDTO() {
    }

    public ProductFakeApiDTO(Long id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
    }

    public ProductFakeApiDTO(String title, String category) {
        this.title = title;
        this.category = category;
    }

}
