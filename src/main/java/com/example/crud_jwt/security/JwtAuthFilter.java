package com.example.crud_jwt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/*OncePerRequestFilter - transforma a classe em um filtro http
que é executado apenas uma vez.*/

/*A anotação é usada para indicar que uma classe é um componente
gerenciado pelo container IoC, o Spring detecta essa classe
automaticamente e a transforma em um bean,
permitindo que ela seja injetada em outras partes do código.*/

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    //É o método que o spring executa automaticamente para filtrar as requisições.
    @Override
    protected void doFilterInternal(HttpServletRequest request, //representa a requisição(header, body, url)
                                    HttpServletResponse response, //representa a resposta http
                                    FilterChain filterChain) //permite continuar a requisição para o próximo filtro/controller.
            throws ServletException, IOException {

        // Pega o header "Authorization" da requisição
        String authHeader = request.getHeader("Authorization");

        // Se não tiver token, deixa passar (o Security vai barrar se a rota for protegida)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove o prefixo "Bearer " para ficar só o token
        String token = authHeader.substring(7);

        // Valida o token e autentica o usuário
        if (jwtService.tokenValido(token)) {
            String email = jwtService.extrairEmail(token);

            //criar o usuário atenticado
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

            //adiciona detalhes da requisição: ip, sessão, detalhes http
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Registra o usuário como autenticado no contexto do Spring Security
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        //diz que pode continuar a requisição.
        filterChain.doFilter(request, response);

    }
}
