package com.kauadev.learning_spring_security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kauadev.learning_spring_security.domain.entities.User;
import com.kauadev.learning_spring_security.exceptions.user.UserNotFoundException;
import com.kauadev.learning_spring_security.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findUsers() {
        return this.userRepository.findAll();
    }

    public User findUserById(Integer id) {
        Optional<User> user = this.userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException();

        return user.get();
    }

    public User updateUser(Integer id, User newUser) {
        Optional<User> user = this.userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException();

        user.get().setEmail(newUser.getEmail());
        user.get().setPassword(newUser.getPassword());

        this.userRepository.save(user.get());

        return user.get();
    }

    public String deleteUser(Integer id) {
        Optional<User> user = this.userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException();

        this.userRepository.delete(user.get());

        return "Deleted user.";
    }
}