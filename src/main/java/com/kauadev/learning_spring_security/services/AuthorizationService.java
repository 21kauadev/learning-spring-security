package com.kauadev.learning_spring_security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kauadev.learning_spring_security.repository.UserRepository;

// implementa UserDetailsService pro Spring Security identificar que ela deve
// ser chamada de forma automatica pra verificação e autenticaçao.
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null)
            throw new UsernameNotFoundException("User - Username / email not found");

        return this.userRepository.findByEmail(username);
    }

}
