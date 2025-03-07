package com.kauadev.learning_spring_security.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não encontrado.");
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
