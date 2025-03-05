package com.product.productapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvise {

    @ExceptionHandler(ProductServiceExceptions.BadRequestException.class)
    ResponseEntity<?> handleBadRequest(ProductServiceExceptions.BadRequestException ex) {

        return ResponseEntity.badRequest().body(new ProductControllerAdvise.ResponseError(ex.getMessage()));
    }


    static class ResponseError {
        @Setter
        @Getter
        private String message;

        @Getter
        @Setter
        private boolean isFailed = true;

        public ResponseError(String message) {
            this.message = message;
        }


    }
}

