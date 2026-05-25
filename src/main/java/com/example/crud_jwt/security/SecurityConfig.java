package com.example.crud_jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Essa anotação significa que a classe possui configurações.
@EnableWebSecurity /*Essa anotação permite personalizar o controle de acesso
de endpoints e a cadeia de filtros HTTP*/
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /*A anotação instrui o framework a gerenciar a criação,
      configuração e ciclo de vida desse objeto, permitindo
      injetar essa dependência automaticamente em
      outras classes do seu projeto.*/
    @Bean
    //Esse método configura toda a segurança HTTP da aplicação.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //não vai guardar sessão
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() //tudo que começa com auth é público
                        .anyRequest().authenticated() //todo o resto exige token
                )
                /*Adiciona seu filtro JWT no Spring Security.
                  Execute meu filtro antes do filtro padrão.*/
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        //Finaliza e cria a configuração de segurança.
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Cria um objeto para criptografar senhas.
    }

}
