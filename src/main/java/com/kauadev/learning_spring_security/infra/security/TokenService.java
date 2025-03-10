package com.kauadev.learning_spring_security.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kauadev.learning_spring_security.domain.entities.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            // o secret é como se fosse uma chave de um cadeado. devemos guardar a chave
            // para evitar problemas de segurança
            System.out.println("Email do usuario aqui em TokenService: " + user.getEmail());

            System.out.println("Hora de expiração: " + genExpirationDate());

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api") // qaulquer nome
                    .withSubject(user.getEmail()) // identifica o usuario
                    .withExpiresAt(this.genExpirationDate()) // tempo de expiração
                    .sign(algorithm); // usa o algoritmo para criar

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token: ", exception);
        }
    }

    // verifica se o token é valido, e se for, retorna o email
    // se não, retorna null. ou seja, mesmo havendo o token e ainda podendo ser
    // decodificado, ele não seria válido
    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            System.out.println("Erro ao validar / verificar token: " + exception.getMessage());
            return null;
        }
    }

    private Instant genExpirationDate() {
        System.out.println("Hora atual: " + LocalDateTime.now());
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
