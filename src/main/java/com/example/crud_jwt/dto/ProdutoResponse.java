package com.example.crud_jwt.dto;

/* Produto.java é uma entidade do banco de dados usada para
representar a tabela produtos no banco.

ProdutoResponse.java é um DTO usado apenas para enviar
dados na resposta da API. Não representa tabela no banco e serve para
controlar quais informações serão retornadas ao cliente,
evitando expor dados importantes.*/

public class ProdutoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private String usuarioEmail; //mostra o dono do produto.

    public ProdutoResponse() {}

    public ProdutoResponse(Long id, String nome, String descricao, Double preco, Integer quantidade, String usuarioEmail) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.usuarioEmail = usuarioEmail;
    }

    //GETTERS
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    //SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
}
