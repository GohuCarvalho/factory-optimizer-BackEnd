### **# Factory Optimizer API**



API REST desenvolvida em \*\*Spring Boot\*\* para gerenciamento de matérias-primas e otimização da produção industrial.



O sistema calcula automaticamente quais produtos devem ser produzidos para \*\*maximizar o valor total da produção\*\*, considerando as restrições de estoque de matéria-prima.



\---



#### **# Tecnologias utilizadas**



\- Java 17+

\- Spring Boot

\- Spring Data JPA

\- Maven

\- Swagger (OpenAPI)

\- JUnit 5

\- Mockito

\- Jakarta Validation

\- H2 / Banco de dados relacional



\---



#### **# Arquitetura do projeto**



###### **O projeto segue o padrão em camadas:**



Controller → Service → Repository → Database





###### **Estrutura principal:**



Controller |Service |Repository | Model | Dto |Exception





\---



#### **# Funcionalidades**



✔ Cadastro de produtos  

✔ Cadastro de matérias-primas  

✔ Definição de composição de produtos  

✔ Cálculo de plano de produção otimizado  

✔ Validação de dados de entrada  

✔ Tratamento global de exceções  

✔ Documentação da API com Swagger  

✔ Testes unitários com JUnit e Mockito  



\---



#### **# Endpoint principal**



#### **## Gerar plano de produção**



GET /production-plan





###### **Exemplo de resposta:**



```json

\[

&#x20; {

&#x20;   "productName": "Premium Bottle",

&#x20;   "quantityToProduce": 2,

&#x20;   "totalValue": 200.0

&#x20; },

&#x20; {

&#x20;   "productName": "Small Cup",

&#x20;   "quantityToProduce": 2,

&#x20;   "totalValue": 40.0

&#x20; }

]



#### **Documentação da API**



###### **Swagger disponível em:**





http://localhost:8080/swagger-ui.html



#### **Como executar o projeto**



###### **Clone o repositório:**



git clone https://github.com/SEU-USUARIO/factory-optimizer-BackEnd.git



###### **Entre na pasta:** 



cd factory-optimizer-BackEnd



###### **Execute a aplicação:**



./mvnw spring-boot:run



###### **A aplicação estará disponível em:**



http://localhost:8080





#### **Testes**



###### **O projeto possui testes unitários para a lógica de planejamento de produção utilizando:**



* JUnit



* Mockito



###### **Executar testes:**



./mvnw test





#### Autor



##### Projeto desenvolvido por Hugo Avelino de Carvalho.



