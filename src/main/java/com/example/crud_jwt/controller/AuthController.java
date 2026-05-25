package com.example.crud_jwt.controller;

import com.example.crud_jwt.dto.LoginRequest;
import com.example.crud_jwt.dto.LoginResponse;
import com.example.crud_jwt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    //retorna uma resposta http contendo uma string, converte o JSON para LoginRequest.
    public ResponseEntity<String> registrar(@RequestBody LoginRequest request) {
        authService.registrar(request);
        return ResponseEntity.ok("Usuário registrado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
