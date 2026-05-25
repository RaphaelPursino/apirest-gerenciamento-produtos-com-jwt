package com.example.crud_jwt.service;

import com.example.crud_jwt.dto.LoginRequest;
import com.example.crud_jwt.dto.LoginResponse;
import com.example.crud_jwt.model.Usuario;
import com.example.crud_jwt.repository.UsuarioRepository;
import com.example.crud_jwt.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UsuarioRepository usuarioRepository; //Usado para acessar usuários no banco.
    private JwtService jwtService; //Usado para gerar tokens JWT.
    private PasswordEncoder passwordEncoder; //Usado para criptografar e validar senhas.

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // Registra um novo usuário com senha criptografada
    public void registrar(LoginRequest request) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha())); //não salva senha pura.
        usuarioRepository.save(usuario);
    }

    // Valida login e retorna o token
    public LoginResponse login(LoginRequest request) { //parâmetro passa email e senha.
        //busca usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Senha incorreta."));

        // Compara a senha enviada com a senha criptografada no banco
        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        //Cria um token usando email do usuário.
        String token = jwtService.gerarToken(usuario.getEmail());
        return new LoginResponse(token); //retorna o token para o cliente.
    }
}
