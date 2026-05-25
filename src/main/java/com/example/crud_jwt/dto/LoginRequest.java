package com.example.crud_jwt.dto;

public class LoginRequest {

    private String email;
    private String senha;

    public LoginRequest() {}

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    //GETTERS
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    //SETTERS
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
