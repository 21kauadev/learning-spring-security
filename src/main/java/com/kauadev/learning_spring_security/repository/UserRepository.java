package com.kauadev.learning_spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.kauadev.learning_spring_security.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email); // vai ser usado pelo Spring Security nas consultas.
}
