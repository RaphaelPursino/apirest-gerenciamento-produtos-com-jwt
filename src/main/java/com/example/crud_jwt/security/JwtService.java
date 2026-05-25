package com.example.crud_jwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}") //pega valor do application.properties
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /* Transforma a String secreta em bytes e cria uma chave criptográfica
    usando esses bytes, usada para assinar e validar o token.*/
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Gera um token para o email informado
    public String gerarToken(String email) {
        return Jwts.builder() //começa a montar o token
                .subject(email) //guarda o email dentro do token
                .issuedAt(new Date()) //data de criação do token
                .expiration(new Date(System.currentTimeMillis() + expiration)) //define quando o token expira
                .signWith(getSigningKey()) //assina o token
                .compact(); //transforma tudo em string JWT
    }

    // Extrai o email de dentro do token
    public String extrairEmail(String token) {
        return Jwts.parser() //cria leitor do token
                .verifyWith(getSigningKey()) //usa a chave para validar o token
                .build() //finaliza a configuração do leitor.
                .parseSignedClaims(token) //decodifica e valida o token
                .getPayload() //pega os dados dentro do token.
                .getSubject(); //retorna o email
    }

    // Verifica se o token é válido e não expirou
    public boolean tokenValido(String token) {
        try {
            extrairEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
