package com.example.crud_jwt.service;

import com.example.crud_jwt.model.Usuario;
import com.example.crud_jwt.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Pega o usuário que está autenticado na requisição atual.
    public Usuario getUsuarioLogado() {
        // O Spring Security guarda o email de quem está autenticado aqui.
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Busca usuário no banco.
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

}
