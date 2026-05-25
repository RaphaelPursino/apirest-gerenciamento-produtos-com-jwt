package com.example.crud_jwt.repository;

import com.example.crud_jwt.model.Produto;
import com.example.crud_jwt.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByUsuario(Usuario usuario);

    Optional<Produto> findByIdAndUsuario(Long id, Usuario usuario);
}
