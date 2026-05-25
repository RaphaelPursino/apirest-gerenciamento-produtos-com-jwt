package com.example.crud_jwt.service;

import com.example.crud_jwt.dto.ProdutoDTO;
import com.example.crud_jwt.model.Produto;
import com.example.crud_jwt.model.Usuario;
import com.example.crud_jwt.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final UsuarioService usuarioService; //descobre o usuário logado.

    public ProdutoService(ProdutoRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    //CREATE - associa o produto ao usuário logado.
    public Produto criar(ProdutoDTO dto) {
        Usuario usuario = usuarioService.getUsuarioLogado(); //descobre quem está autenticado.

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setUsuario(usuario); //Esse produto pertence ao usuário logado.
        return repository.save(produto);
    }

    //READ ALL - lista só os produtos do usuário logado
    public List<Produto> listarTodos() {
        Usuario usuario = usuarioService.getUsuarioLogado(); //pega o usuário.]
        return repository.findByUsuario(usuario); //busca os produtos desse usuário.
    }

    // READ BY ID - só encontra se o produto for do usuário logado.
    public Produto buscarPorId(Long id) {
        Usuario usuario = usuarioService.getUsuarioLogado();
        return repository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    //UPDATE - só atualiza se o produto for do usuário logado
    public Produto atualizar(Long id, ProdutoDTO dto) {
        Produto produto = buscarPorId(id); //já valida o dono.
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        return repository.save(produto);
    }

    //DELETE - só deleta se o produto for do usuário logado.
    public void deletar(Long id) {
        Produto produto = buscarPorId(id); //já valida o dono.
        repository.deleteById(produto.getId()); //deleta o produto do usuário logado.
    }

}
