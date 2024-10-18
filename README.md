![image](/img/equipe_cerberus.png)

## Desafio

Desenvolver uma ferramenta para captura e armazenamento de notícias estratégicas de portais e dados relevantes de API Públicas, para possibilidade de futuras análises usando IA.

Descrição: Este desafio propõe a criação de um sistema web para mapear portais de notícias estratégicas e realizar a captura automatizada e contínua de dados para formar um histórico. O sistema deve suportar tanto o registro de portais de notícias quanto de APIs de dados estratégicos, como previsões do tempo. A solução incluirá funcionalidades de cadastro de portais, APIs, tags e jornalistas, além de um processo de web scraping para coleta e armazenamento dos dados. Também deverá oferecer interfaces de consulta com filtros avançados e ser capaz de lidar com um grande volume de informações.

### 🤖 Prazos

| Sprints           | Início     | Fim        |
| ----------------- | ---------- | ---------- |
| Kick-off geral    | 26/08/2024 | 30/08/2024 |
| 1ª sprint         | 09/09/2024 | 29/09/2024 |
| 2ª sprint         | 30/09/2024 | 20/10/2024 |
| 3ª sprint         | 21/10/2024 | 10/11/2024 |
| 4ª sprint         | 11/11/2024 | 01/12/2024 |
| Feira de Soluções | 12/12/2024 |

### 🤖 Priorização das Sprints

[Board Kanbam com a priorização](https://github.com/users/c137santos/projects/9/views/1)

Ao abrir os cards do kanbam será possível ver a priorização no lado direito como um dos atributos dos cards.

### Como instalar o projeto

Siga as instruções do Contributing!

[Contributing](.github/CONTRIBUTING.md)

# Requisitos Não Funcionais

• Prever um grande volume de notícias armazenadas
• Utilizar softwares livres
• Manual do Usuário
• Guia de instalação
• Java (linguagem de programação, frameworks e APIs)
• Documentação API – Application Programming Interface com OpenID
• Modelagem de Banco de Dados ou Arquivo de dados.
• Deverá ser uma aplicação web.
• O front-end deve ser desenvolvido de forma minimalista.

# Requisitos Funcionais

1. Cadastro de Portais de notícias
2. Cadastro de APIs
3. Cadastro de Tags
4. Cadastro de Jornalistas
5. Processo de web scraping (capturar os dados de notícias e APIs e armazenar em banco de dados)
6. Indicação de tags que estão relacionadas
7. Tela de consulta de notícias, com filtros de pesquisa
8. Tela de APIs, com filtros de pesquisa
9. Deve ser possível relacionar tags com regionalismos.

# Backlog

| Nº  | Sprint   | User Story                                                                                                                                         | Prioridade | Status   | Requisitos |
| --- | -------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | -------- | ---------- |
| 1   | Sprint 1 | Como um administrador, eu quero cadastrar APIs de terceiros, para que eu possa integrar e coletar dados de notícias de diferentes fontes.          | Alta       | Pendente | 2          |
| 2   | Sprint 1 | Como um administrador, eu quero capturar dados de APIs previamente cadastradas, para que as informações sejam armazenadas no banco de dados.       | Alta       | Pendente | 5          |
| 3   | Sprint 1 | Como um administrador, eu quero cadastrar tags, para que as notícias possam ser classificadas e relacionadas.                                      | Média      | Pendente | 3          |
| 4   | Sprint 1 | Como um administrador, eu quero editar tags, para que eu possa atualizar informações previamente cadastradas.                                      | Média      | Pendente | 3          |
| 5   | Sprint 1 | Como um administrador, eu quero desativar tags, para que tags obsoletas não fiquem disponíveis no sistema.                                         | Baixa      | Pendente | 3          |
| 6   | Sprint 2 | Como um administrador, eu quero relacionar tags com regionalismos, para que seja possível buscar notícias relevantes de várias regiões do Brasil.  | Alta       | Pendente | 9          |
| 7   | Sprint 2 | Como um administrador, eu quero ver todos os portais de notícias cadastrados, para que eu possa analisar quais dados serão buscados.               | Alta       | Pendente | 1          |
| 8   | Sprint 2 | Como um administrador, eu quero cadastrar portais de notícias, para que eu possa gerenciar as fontes de onde as notícias serão capturadas.         | Alta       | Pendente | 1          |
| 9   | Sprint 2 | Como um administrador, eu quero editar portais de notícias, para que eu possa atualizar os dados de um portal.                                     | Média      | Pendente | 1          |
| 10  | Sprint 2 | Como um administrador, eu quero capturar notícias de portais previamente cadastrados, para que as informações sejam armazenadas no banco de dados. | Alta       | Pendente | 5 , 4      |
| 11  | Sprint 3 | Como um administrador, eu quero editar APIs de terceiros, para que eu possa atualizar as informações.                                              | Média      | Pendente | 2          |
| 12  | Sprint 3 | Como um administrador, eu quero consultar APIs cadastradas, para que eu possa gerenciar e verificar a integração com as fontes de dados.           | Média      | Pendente | 8          |
| 13  | Sprint 4 | Como um usuário, eu quero consultar notícias por meio de uma tela com filtros de pesquisa, para que eu possa encontrar notícias relevantes.        | Alta       | Pendente | 7          |
| 14  | Sprint 4 | Como um usuário, eu quero visualizar as tags relacionadas a uma notícia, para que eu possa entender melhor o contexto do conteúdo.                 | Baixa      | Pendente | 6          |

## Tecnologias, linguagens de programação e ferramentas utilizadas:

![image](/img/tecnologias_e_ferramentas.png)

## Design telas:

[Design telas](documentação/design_telas.pdf)

## Estruturação do Banco

### Modelo conceitual

![image](/img/Cerberus2.0.jpg)

### Modelo físico

## Burndown do projeto

[Burdown da 1º sprint](https://docs.google.com/spreadsheets/d/e/2PACX-1vTXRHVQpU5eI4LoDabcDIMd5JyNLnffdLF4DwnGZlrQGtpdmEBwshaBsn3KOH8GVLW6xHc2C3y7cMRR/pubhtml)



## Manual do usuário

[Manual do Usuário](../documentação/Manual_do_usuario.md)


## Equipe

| Integrante                                                                                                                             | LinkedIn                                                                                                                                                                |
| -------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Clara Santos ![Static Badge](https://img.shields.io/badge/Scrum_master-pink) ![Static Badge](https://img.shields.io/badge/Dev-black)   | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/c137santos/)                  |
| Marília Borgo ![Static Badge](https://img.shields.io/badge/Product_owner-blue) ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mariliaborgo/)                |
| Matheus Marciano ![Static Badge](https://img.shields.io/badge/Dev-black)                                                               | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/matheus-marciano-leite/)      |
| Guilherme Bezerra Junqueira ![Static Badge](https://img.shields.io/badge/Dev-black)                                                    | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilherme-bezerra-a01035170/) |
| Pedro Henrique Lopes de Souza ![Static Badge](https://img.shields.io/badge/Dev-black)                                                  | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pelopes7/)                    |
| Yan Costa Yamim ![Static Badge](https://img.shields.io/badge/Dev-black)                                                                | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/yan-yamim-185220278/)         |
| Eduardo da Silva Lima ![Static Badge](https://img.shields.io/badge/Dev-black)                                                          | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/edu-datamarketing/)           |
