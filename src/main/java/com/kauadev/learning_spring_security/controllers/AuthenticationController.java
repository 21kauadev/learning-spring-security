package com.kauadev.learning_spring_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.learning_spring_security.domain.entities.AuthenticationDTO;
import com.kauadev.learning_spring_security.domain.entities.RegisterDTO;
import com.kauadev.learning_spring_security.domain.entities.User;
import com.kauadev.learning_spring_security.infra.security.TokenService;
import com.kauadev.learning_spring_security.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());

        // esse método authenticate recebe por parametro um usernamePasswordToken
        var auth = this.authenticationManager.authenticate(usernamePassword);

        System.out.println("objeto auth get principal em auth controller: " + auth.getPrincipal());

        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().body("Bad request");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedPassword, data.role());

        this.userRepository.save(user);

        return ResponseEntity.ok().body("User registered!");
    }

}
