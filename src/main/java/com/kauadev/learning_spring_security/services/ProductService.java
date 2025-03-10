package com.kauadev.learning_spring_security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauadev.learning_spring_security.domain.entities.Product;
import com.kauadev.learning_spring_security.exceptions.product.ProductNotFoundException;
import com.kauadev.learning_spring_security.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    public Product findProductById(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException();

        return product.get();
    }

    public Product createProduct(Product newProduct) {
        return this.productRepository.save(newProduct);
    }

    public Product updateProduct(Integer id, Product newProduct) {
        Optional<Product> product = this.productRepository.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException();

        product.get().setName(newProduct.getName());
        product.get().setDescription(newProduct.getDescription());
        product.get().setPrice(newProduct.getPrice());
        product.get().setQuantity(newProduct.getQuantity());

        return this.productRepository.save(product.get());
    }

    public String deleteProduct(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException();

        this.productRepository.delete(product.get());

        return "Deleted product.";
    }

}
