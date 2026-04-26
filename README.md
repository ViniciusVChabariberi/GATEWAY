# 🚀 API Gateway - Microservices Orchestra

Este é o ponto de entrada único (Entry Point) do ecossistema. Ele gerencia o roteamento dinâmico para todos os microsserviços e aplica a segurança perimetral.

## 🛠️ Tecnologias
- Java 17 / Spring Boot 3.2.5
- Spring Cloud Gateway
- Spring Security (WebFlux)
- JWT (JSON Web Token)

## ⚙️ Funcionalidades
- **Roteamento:** Encaminha requisições para os serviços de Login, Clientes e Produtos.
- **Segurança:** Valida tokens JWT antes de permitir o acesso às APIs internas.
- **CORS:** Configuração centralizada de acesso.

## 📌 Configuração de Rotas (application.yaml)
- `/api/login/**` e `/api/usuario/**` -> Login Service (8081)
- `/api/cliente/**` -> Cliente Service (8082)
- `/api/produto/**` -> Produto Service (8083)
- `/api/pedido/**` -> Pedido/Venda Service (8083)
