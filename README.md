# Desenvolvimento de Sistema de Triagem de Pronto Socorro

## 1. Objetivo

O objetivo deste projeto é um sistema de backend orientado a objetos, desenvolvido em Java com Spring Boot e Maven. O sistema gerencia a triagem de pacientes em um pronto socorro, expondo uma API RESTful para comunicação via JSON e utilizando o Spring Data JPA para abstração da camada de persistência, garantindo independência do banco de dados relacional utilizado.

---

## 2. Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework Core:** Spring Boot 3.x
* **Acesso a Dados:** Spring Data JPA / Hibernate
* **Gerenciador de Dependências:** Maven
* **Banco de Dados (Desenvolvimento):** H2 Database (em memória)
* **Servidor:** Tomcat Embutido

---

## 3. Arquitetura

O projeto foi desenvolvido seguindo uma arquitetura em camadas para garantir a separação de responsabilidades, coesão e baixo acoplamento entre os componentes do sistema.

* **`model`**: Contém as entidades JPA que representam os dados do domínio (`Paciente`, `Medico`) e os Enums de apoio (`Prioridade`, `Gravidade`, `StatusAtendimento`).
* **`repository`**: Interfaces que estendem `JpaRepository`, responsáveis pela abstração da comunicação com o banco de dados.
* **`service`**: Camada que contém a lógica de negócio da aplicação.
* **`controller`**: Camada responsável por expor a API RESTful, recebendo requisições HTTP e retornando respostas em JSON.

---

## 4. Instruções de Build e Execução

### Pré-requisitos
* Java Development Kit (JDK) 17 ou superior.
* Apache Maven 3.8 ou superior.
* Git.

### Passos para Execução
1.  **Clone o repositório:**
    ```bash
    git clone [COLE A URL DO SEU REPOSITÓRIO GIT AQUI]
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd prontosocorro
    ```

3.  **Execute a aplicação com o Maven:**
    O Maven se encarregará de baixar as dependências e iniciar o servidor.
    ```bash
    mvn spring-boot:run
    ```

4.  Após a inicialização, a aplicação estará disponível e pronta para receber requisições em `http://localhost:8080`.

---

## 5. Documentação da API

A seguir, estão detalhados todos os endpoints disponíveis na API.

### 5.1. Saúde do Sistema

Verifica se a aplicação está no ar e respondendo.

* **Método:** `GET`
* **URL:** `/health`
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "status": "UP",
        "message": "Sistema de triagem está no ar!"
    }
    ```

### 5.2. Cadastrar Médico

Cadastra um novo médico no sistema.

* **Método:** `POST`
* **URL:** `/medicos`
* **Corpo da Requisição (Exemplo):**
    ```json
    {
        "nome": "Dra. Ana Costa",
        "especialidade": "Cardiologia",
        "crm": "123456"
    }
    ```
* **Resposta de Sucesso (201 Created):** Retorna o objeto do médico recém-criado, incluindo seu `id` gerado pelo sistema.

### 5.3. Atualizar Status de Plantão do Médico

Altera o status de um médico para "em plantão" ou "fora de plantão".

* **Método:** `PUT`
* **URL:** `/medicos/{id}/plantao`
* **Corpo da Requisição (Exemplo):**
    ```json
    {
        "emPlantao": true
    }
    ```
* **Resposta de Sucesso (200 OK):** Retorna o objeto completo do médico com o status de plantão atualizado.

### 5.4. Realizar Triagem de Paciente

Registra a chegada de um novo paciente no sistema.

* **Método:** `POST`
* **URL:** `/triagem`
* **Corpo da Requisição (Exemplo):**
    ```json
    {
        "nome": "Carlos Pereira",
        "idade": 58,
        "sintomas": "Forte dor no braço esquerdo e suor frio",
        "prioridade": "VERMELHA",
        "gravidade": "GRAVE"
    }
    ```
* **Resposta de Sucesso (201 Created):** Retorna o objeto do paciente criado, com os campos `id`, `dataHoraTriagem` e `status` (definido como "AGUARDANDO") preenchidos pelo sistema.

### 5.5. Buscar Paciente por ID

Retorna os dados de um paciente específico a partir de seu ID.

* **Método:** `GET`
* **URL:** `/pacientes/{id}`
* **Resposta de Sucesso (200 OK):** Retorna o objeto JSON do paciente correspondente.

### 5.6. Chamar Próximo Paciente para Atendimento

Busca o próximo paciente na fila de acordo com as regras de prioridade e o marca como "em atendimento".

* **Método:** `POST`
* **URL:** `/atendimento/proximo`
* **Nota de Design:** Este endpoint foi implementado como `POST` para seguir as melhores práticas de APIs RESTful. Uma requisição `GET` deve ser segura e não alterar o estado dos dados no servidor. Como esta operação modifica o status do paciente de "AGUARDANDO" para "EM_ATENDIMENTO", o uso de `POST` é o semanticamente correto.
* **Resposta de Sucesso (200 OK):** Retorna o objeto do paciente chamado, agora com o campo `"status": "EM_ATENDIMENTO"`.
* **Resposta de Falha (404 Not Found):** Será retornada caso não haja médicos em plantão ou não haja pacientes na fila de espera.

Projeto Triagem