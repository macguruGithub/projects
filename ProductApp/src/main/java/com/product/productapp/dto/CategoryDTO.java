package com.product.productapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {
    private Long id;
    private String title;
}
