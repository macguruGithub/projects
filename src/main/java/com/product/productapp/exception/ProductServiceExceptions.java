package com.product.productapp.exception;

public class ProductServiceExceptions {
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
