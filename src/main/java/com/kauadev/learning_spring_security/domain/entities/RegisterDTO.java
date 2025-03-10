package com.kauadev.learning_spring_security.domain.entities;

import com.kauadev.learning_spring_security.enums.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {

}
