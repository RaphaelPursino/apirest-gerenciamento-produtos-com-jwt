# 🛒 API REST de Gerenciamento de Produtos

API REST desenvolvida com Java e Spring Boot, com autenticação JWT e isolamento de dados por usuário. Cada usuário autenticado gerencia exclusivamente seus próprios produtos.

---

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.2**
- **Spring Security**
- **Spring Data JPA / Hibernate**
- **JWT (JJWT 0.11.5)**
- **PostgreSQL**
- **Maven**

---

## ⚙️ Como rodar o projeto

### Pré-requisitos

- Java 17+
- PostgreSQL instalado e rodando
- Maven

### 1. Clone o repositório

```bash
git clone https://github.com/RaphaelPursino/apirest-gerenciamento-produtos-com-jwt.git
cd apirest-gerenciamento-produtos-com-jwt
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE produtosdb;
```

### 3. Configure o `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/produtosdb
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=minha-chave-secreta-super-segura-com-256-bits-ok
jwt.expiration=86400000
```

### 4. Rode a aplicação

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 🔐 Autenticação

A API utiliza autenticação via **JWT (JSON Web Token)**.

### Fluxo

1. Registre um usuário em `POST /auth/registrar`
2. Faça login em `POST /auth/login` e copie o token retornado
3. Nas demais requisições, envie o token no header:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📋 Endpoints

### Auth — rotas públicas

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/registrar` | Registra um novo usuário |
| POST | `/auth/login` | Autentica e retorna o token JWT |

#### POST `/auth/registrar`
```json
{
    "email": "raphael@email.com",
    "senha": "123456"
}
```

#### POST `/auth/login`
```json
{
    "email": "raphael@email.com",
    "senha": "123456"
}
```
Resposta:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Produtos — rotas protegidas 🔒

> Todas as rotas abaixo exigem o token JWT no header.

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/produtos` | Cria um novo produto |
| GET | `/api/produtos` | Lista todos os produtos do usuário logado |
| GET | `/api/produtos/{id}` | Busca um produto por ID |
| PUT | `/api/produtos/{id}` | Atualiza um produto |
| DELETE | `/api/produtos/{id}` | Deleta um produto |

#### POST `/api/produtos`
```json
{
    "nome": "Notebook Dell",
    "descricao": "Notebook com 16GB RAM",
    "preco": 3500.00,
    "quantidade": 10
}
```

#### Resposta
```json
{
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook com 16GB RAM",
    "preco": 3500.00,
    "quantidade": 10,
    "usuarioEmail": "raphael@email.com"
}
```

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas:

```
Controller  →  recebe as requisições HTTP
Service     →  contém a lógica de negócio
Repository  →  acessa o banco de dados
Model       →  representa as tabelas do banco
DTO         →  controla o que entra e sai da API
Security    →  autentica e protege as rotas
```

### Estrutura de pastas

```
src/main/java/com/produtos_api/
├── controller/
│   ├── AuthController.java
│   └── ProdutoController.java
├── dto/
│   ├── ProdutoDTO.java
│   |── ProdutoResponse.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
├── model/
│   ├── Produto.java
│   └── Usuario.java
├── repository/
│   ├── ProdutoRepository.java
│   └── UsuarioRepository.java
├── security/
│   ├── JwtAuthFilter.java
│   ├── JwtService.java
│   └── SecurityConfig.java
└── service/
│   ├── AuthService.java
|   ├── ProdutoService.java
|   └── UsuarioService.java
```

---

## 🔒 Segurança

- Senhas criptografadas com **BCrypt**
- Autenticação stateless via **JWT**
- Cada usuário acessa **somente seus próprios produtos**
- Tentativa de acessar produto de outro usuário retorna `404 Not Found`

---

