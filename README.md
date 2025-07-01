# Sistema de Triagem de Pronto Socorro

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-4.0-red)

## Tabela de Conteúdos
1.  [Visão Geral do Projeto](#1-visão-geral-do-projeto)
2.  [Arquitetura e Decisões de Design](#2-arquitetura-e-decisões-de-design)
3.  [Tecnologias Utilizadas](#3-tecnologias-utilizadas)
4.  [Como Executar o Projeto](#4-como-executar-o-projeto)
5.  [Documentação Completa da API](#5-documentação-completa-da-api)
6.  [Estrutura do Projeto](#6-estrutura-do-projeto)
7.  [Autor](#7-autor)

---

## 1. Visão Geral do Projeto

Este projeto consiste em um sistema de backend para o gerenciamento do fluxo de triagem de pacientes em uma unidade de pronto socorro. O objetivo é fornecer uma API RESTful robusta e coesa que permita o registro de médicos e pacientes, o controle de plantões e, principalmente, a implementação de uma lógica de negócio para determinar a ordem de atendimento baseada em critérios de prioridade e gravidade.

A aplicação foi construída com foco em boas práticas de desenvolvimento de software, incluindo uma arquitetura em camadas bem definida, abstração da camada de persistência e um design de API claro e semântico.

## 2. Arquitetura e Decisões de Design

O sistema foi estruturado utilizando uma **arquitetura em camadas**, um padrão que promove a separação de responsabilidades e facilita a manutenção e testabilidade do código.

* **`Controller`**: A camada mais externa, responsável por expor os endpoints da API, receber as requisições HTTP, validar entradas e delegar a execução para a camada de serviço.
* **`Service`**: O coração da aplicação, onde reside toda a lógica de negócio. Orquestra as operações, valida regras e utiliza a camada de repositório para interagir com os dados.
* **`Repository`**: A camada de acesso a dados. Utilizando o Spring Data JPA, esta camada abstrai toda a complexidade da comunicação com o banco de dados.
* **`Model`**: Representa as entidades do domínio do problema (`Paciente`, `Medico`) e seus tipos de apoio (`Enums`), mapeadas para as tabelas do banco de dados.

### Decisões de Design Notáveis:

* **Segurança de Tipos com Enums**: Para campos como `prioridade`, `gravidade` e `status`, foram utilizados `Enums` em vez de `Strings`. Isso garante a integridade dos dados em tempo de compilação, prevenindo a entrada de valores inválidos.
* **Abstração de Persistência**: A utilização do Spring Data JPA e de um banco de dados em memória (H2) para desenvolvimento garante que a aplicação seja totalmente agnóstica ao banco de dados final. A migração para um ambiente de produção com PostgreSQL ou MySQL exigiria apenas alterações no arquivo de configuração `application.properties`.
* **Correção de Lógica de Negócio**: Durante os testes, foi identificado que a simples busca pelo próximo paciente era insuficiente. A lógica foi refatorada para que, ao ser chamado, o status do paciente seja alterado para `EM_ATENDIMENTO`, garantindo que ele não seja chamado novamente e que a fila funcione corretamente.
* **Semântica RESTful (`GET` vs `POST`)**: O endpoint `/atendimento/proximo`, que originalmente poderia ser interpretado como um `GET`, foi implementado como `POST`. Esta decisão foi tomada porque a operação **altera o estado de um recurso** no servidor (o status do paciente), o que, segundo as melhores práticas REST, deve ser feito por métodos não-seguros como `POST`.

## 3. Tecnologias Utilizadas

* **Linguagem Principal:** Java 17
* **Framework:** Spring Boot 3
* **Gerenciador de Dependências:** Apache Maven
* **Persistência de Dados:**
    * Spring Data JPA
    * Hibernate (Implementação JPA)
    * H2 Database (Banco de dados em memória)
* **API:** Spring Web (RESTful)
* **Utilitários:** Lombok

## 4. Como Executar o Projeto

**Pré-requisitos:**
* Java Development Kit (JDK) 17 ou superior.
* Apache Maven 3.8 ou superior (opcional, pois o projeto usa o Maven Wrapper).

**Passos para Execução:**

1.  **Descompacte o arquivo `.zip`** do projeto em um diretório de sua preferência.

2.  **Abra um terminal** (Prompt de Comando, PowerShell, ou Terminal do Linux/macOS).

3.  **Navegue até o diretório raiz** do projeto (a pasta `prontosocorro` que contém o arquivo `pom.xml`).
    ```bash
    cd caminho/para/prontosocorro
    ```

4.  **Execute a aplicação utilizando o Maven Wrapper.** Este comando irá baixar as dependências e iniciar o servidor web embutido.
    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

5.  Aguarde a inicialização. Ao ver a mensagem `Tomcat started on port(s): 8080 (http)`, a API estará pronta para uso.

**Verificação Rápida:**
Para confirmar que o servidor está no ar, envie uma requisição `GET` para `http://localhost:8080/health`. A resposta deve ser um status `200 OK` com um corpo JSON indicando que o sistema está "UP".

---

## 5. Documentação Completa da API

### `GET /health`
Verifica a saúde da aplicação.

* **Método:** `GET`
* **URL:** `http://localhost:8080/health`
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "status": "UP",
        "message": "Sistema de triagem está no ar!"
    }
    ```

### `POST /medicos`
Cadastra um novo médico.

* **Método:** `POST`
* **URL:** `http://localhost:8080/medicos`
* **Corpo da Requisição (Exemplo):**
    ```json
    {
        "nome": "Dra. Ana Costa",
        "especialidade": "Cardiologia",
        "crm": "123456"
    }
    ```
* **Resposta de Sucesso (201 Created):** Retorna o objeto do médico criado, com `id` e `emPlantao: false`.

### `PUT /medicos/{id}/plantao`
Atualiza o status de plantão de um médico.

* **Método:** `PUT`
* **URL:** `http://localhost:8080/medicos/1/plantao`
* **Corpo da Requisição (Exemplo):**
    ```json
    { "emPlantao": true }
    ```
* **Resposta de Sucesso (200 OK):** Retorna o objeto do médico com o status atualizado.
* **Resposta de Erro (404 Not Found):** Se o `id` do médico não existir.

### `POST /triagem`
Registra a triagem de um novo paciente.

* **Método:** `POST`
* **URL:** `http://localhost:8080/triagem`
* **Corpo da Requisição (Exemplo):**
    ```json
    {
        "nome": "Carlos Pereira",
        "idade": 58,
        "sintomas": "Forte dor no peito e falta de ar",
        "prioridade": "VERMELHA",
        "gravidade": "GRAVE"
    }
    ```
* **Resposta de Sucesso (201 Created):** Retorna o objeto do paciente criado com `id`, `dataHoraTriagem` e `status: "AGUARDANDO"`.

### `GET /pacientes/{id}`
Busca os dados de um paciente específico pelo seu ID.

* **Método:** `GET`
* **URL:** `http://localhost:8080/pacientes/1`
* **Resposta de Sucesso (200 OK):** Retorna o objeto do paciente correspondente.
* **Resposta de Erro (404 Not Found):** Se o `id` do paciente não existir.

### `POST /atendimento/proximo`
Chama o próximo paciente da fila (baseado em prioridade, gravidade e tempo de chegada) e atualiza seu status para "em atendimento".

* **Método:** `POST`
* **URL:** `http://localhost:8080/atendimento/proximo`
* **Corpo da Requisição:** Nenhum.
* **Resposta de Sucesso (200 OK):** Retorna o objeto do paciente chamado, agora com `"status": "EM_ATENDIMENTO"`.
* **Resposta de Erro (404 Not Found):** Será retornada caso não haja médicos em plantão ou se a fila de pacientes `AGUARDANDO` estiver vazia.