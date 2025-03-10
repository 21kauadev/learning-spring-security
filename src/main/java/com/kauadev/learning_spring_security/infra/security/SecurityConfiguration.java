package com.kauadev.learning_spring_security.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// para desabilitarmos a autenticação padrão do spring security e usar a nossa (via tokens)

@Configuration // indica q é classe de config
@EnableWebSecurity // habilita a config do web security
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    // security filter chain -> corrente de filtros que será aplicado na requisição
    // pra tornar a aplicação segura

    // filtros -> métodos chamados pra validar o usuário que está fazendo
    // requisições, verificando se ele está apto.

    @Bean // -> pro spring conseguir instanciar a classe
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // seção stateless (nao guardaremos estado, usando tokens)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // aqui decidimos certinho quais rotas precisarão de quais roles.
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // qualquer pessoa
                                                                                             // permitida
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // normalmente não é
                                                                                                // feito assim, mas é
                                                                                                // para fins didáticos
                                .requestMatchers(HttpMethod.POST, "/user/findByEmail").permitAll()
                                .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                                .anyRequest().authenticated()) // todas outras usuario autenticado.
                // filtro que verifica antes do authorizeHTTPRequests
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // configuramos a authenticationManager para que possa ser usada
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // para o hash e decode da senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
