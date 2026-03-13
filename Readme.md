# 🏭 Factory Optimizer API

> API REST desenvolvida em **Spring Boot** para gerenciamento de matérias-primas e otimização da produção industrial.

O sistema calcula automaticamente quais produtos devem ser produzidos para **maximizar o valor total da produção**, considerando as restrições de estoque de matéria-prima.

---

## 🛠 Tecnologias utilizadas

* **Java 17+**
* **Spring Boot 3**
* **Spring Data JPA**
* **Maven**
* **Swagger (OpenAPI)**
* **JUnit 5 & Mockito**
* **H2 Database** (ou Banco de dados relacional)

---

## 🏗 Arquitetura do projeto

O projeto segue o padrão de camadas para garantir a separação de responsabilidades:

`Controller` → `Service` → `Repository` → `Database`

**Estrutura de pacotes:**
`Controller` | `Service` | `Repository` | `Model` | `Dto` | `Exception`

---

## ✅ Funcionalidades

* [x] Cadastro de produtos e matérias-primas.
* [x] Definição de composição técnica de produtos.
* [x] **Cálculo de plano de produção otimizado.**
* [x] Validação de dados e tratamento global de exceções.
* [x] Documentação interativa com Swagger.
* [x] Testes unitários de lógica de negócio.

## 🧪 Testes Unitários

A suíte de testes foi rigorosamente implementada para garantir a precisão do cálculo de produção. 

**Cenários validados:**
* ✅ Cálculo básico de produção por limite de estoque.
* ✅ Priorização de produtos mais valiosos.
* ✅ Resolução de conflitos de matéria-prima (Pular produtos inviáveis e buscar o próximo possível).
* ✅ Integridade do valor total calculado.

* Para rodar os testes, utilize:

./mvnw test

---

## 🚀 Como executar o projeto

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/HUGO-AVELINO/factory-optimizer-BackEnd.git](https://github.com/HUGO-AVELINO/factory-optimizer-BackEnd.git)

2. Entre na pasta:
    cd factory-optimizer-BackEnd

3.  Execute a aplicação:
     ./mvnw spring-boot:run
 A API estará disponível em: http://localhost:8080

📖 Documentação da API

Com a aplicação rodando, acesse o Swagger para visualizar e testar os endpoints:

🔗 http://localhost:8080/swagger-ui.html

Exemplo de Resposta (Plano de Produção)
GET /production-plan

[
  {
    "productName": "Premium Bottle",
    "quantityToProduce": 2,
    "totalValue": 200.0
  },
  {
    "productName": "Small Cup",
    "quantityToProduce": 2,
    "totalValue": 40.0
  }
]


👤 Autor
Desenvolvido por Hugo Avelino de Carvalho.
