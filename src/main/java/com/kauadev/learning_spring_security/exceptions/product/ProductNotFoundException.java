package com.kauadev.learning_spring_security.exceptions.product;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Produto não encontrado.");
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
