package com.example.crud_jwt.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Double preco;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Produto() {}

    public Produto(Long id, String nome, String descricao, Double preco, Integer quantidade, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.usuario = usuario;
    }

    //GETTERS
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Double getPreco() { return preco; };
    public Integer getQuantidade() { return quantidade; };
    public Usuario getUsuario() { return usuario; }

    //SETTERS


    public void setId(Long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setPreco(Double preco) {this.preco = preco;}
    public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}
