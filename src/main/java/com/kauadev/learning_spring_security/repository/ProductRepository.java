package com.kauadev.learning_spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kauadev.learning_spring_security.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
