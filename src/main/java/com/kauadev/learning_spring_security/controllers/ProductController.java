package com.kauadev.learning_spring_security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.learning_spring_security.domain.entities.Product;
import com.kauadev.learning_spring_security.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> products() {
        List<Product> products = this.productService.findAllProducts();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> product(@PathVariable("id") Integer id) {
        Product product = this.productService.findProductById(id);

        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        Product product = this.productService.createProduct(newProduct);

        return ResponseEntity.ok().body(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product newProduct) {
        Product product = this.productService.updateProduct(id, newProduct);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        this.productService.deleteProduct(id);

        return ResponseEntity.ok().body("Product deleted.");
    }
}
