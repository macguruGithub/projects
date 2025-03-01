package com.product.productapp.projection;

public interface ProductProjection {
    String getTitle();
    Category getCategory();
    interface  Category{
        String getTitle();
    }
}
