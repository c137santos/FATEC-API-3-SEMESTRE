## Desafio 
Desenvolver uma ferramenta para captura e armazenamento de notícias estratégicas de portais e dados relevantes de API Públicas, para possibilidade de futuras análises usando IA.

### Descrição 
Este desafio propõe a criação de um sistema web para mapear portais de notícias estratégicas e realizar a captura automatizada e contínua de dados para formar um histórico. O sistema deve suportar tanto o registro de portais de notícias quanto de APIs de dados estratégicos, como previsões do tempo. A solução incluirá funcionalidades de cadastro de portais, APIs, tags e jornalistas, além de um processo de web scraping para coleta e armazenamento dos dados. Também deverá oferecer interfaces de consulta com filtros avançados e ser capaz de lidar com um grande volume de informações.

### 🤖 Prazos

| Sprints | Início | Fim |
| ------- | ------ | --- |
| Kick-off geral| 26/08/2024 | 30/08/2024|
| 1ª sprint | 09/09/2024 | 06/09/2024 |
| 2ª sprint | 30/09/2024 | 20/10/2024 |
| 3ª sprint | 21/10/2024 | 10/11/2024 |
| 4ª sprint | 11/11/2024 | 01/12/2024 |
| Feira de Soluções | 12/12/2024 |

**Sprint** | **Feature** |
|------------|-------------|
| Sprint 1 | Cadastrar APIs de terceiros. |
| Sprint 1 | Capturar dados de APIs cadastradas. |
| Sprint 1 | Cadastrar tags para notícias. |
| Sprint 1 | Editar tags cadastradas. |
| Sprint 1 | Excluir tags obsoletas. |
| Sprint 1 | Relacionar tags entre si. |
| Sprint 2 | Ver todos os portais de notícias cadastrados. |
| Sprint 2 | Cadastrar portais de notícias. |
| Sprint 2 | Editar portais de notícias. |
| Sprint 2 | Capturar notícias de portais cadastrados. |
| Sprint 3 | Editar APIs de terceiros. |
| Sprint 3 | Consultar APIs cadastradas com filtros. |
| Sprint 3 | Cadastrar jornalistas. |
| Sprint 3 | Editar informações de jornalistas cadastrados. |
| Sprint 3 | Excluir jornalistas obsoletos. |
| Sprint 4 | Consultar notícias com filtros de pesquisa. |
| Sprint 4 | Visualizar tags relacionadas a uma notícia. |


### 🤖 Priorização das Sprints
[Board Kanbam com a priorização](https://github.com/users/c137santos/projects/9/views/1)

Ao abrir os cards do kanbam será possível ver a priorização no lado direito como um dos atributos dos cards.


| ID  | User Story                                                                                                             | Tarefas                                                                                                                                                                                                                                                                                                         | Sprint |
| --- | ---------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| 1   | Como um administrador, eu quero cadastrar APIs de terceiros, para que eu possa integrar e coletar dados de notícias.    | - Criar formulário de cadastro de API<br>- Implementar validação de campos obrigatórios<br>- Criar botão de "Salvar" e "Cancelar"<br>- Automatizar salvamento da data de cadastro                                                                                                                                | Sprint 1 |
| 2   | Como um administrador, eu quero capturar dados de APIs previamente cadastradas, para que as informações sejam armazenadas. | - Implementar agendamento de captura de dados<br>- Armazenar os dados capturados no banco<br>- Otimizar sistema para grandes volumes de dados                                                                                                                                                                     | Sprint 1 |
| 3   | Como um administrador, eu quero cadastrar tags, para que as notícias possam ser classificadas e relacionadas.           | - Criar formulário de cadastro de tags<br>- Implementar botão de "Salvar" e "Cancelar"                                                                                                                                                                                                                           | Sprint 1 |
| 4   | Como um administrador, eu quero editar tags, para que eu possa atualizar informações previamente cadastradas.           | - Implementar interface de edição de tags<br>- Permitir edição dos campos nome e descrição<br>- Exibir modal de confirmação ao excluir                                                                                                                                                                           | Sprint 1 |
| 5   | Como um administrador, eu quero excluir tags, considerando que tags podem ficar obsoletas.                             | - Adicionar botão de "Excluir" na listagem de tags<br>- Implementar modal de confirmação de exclusão                                                                                                                                                                                                             | Sprint 1 |
| 6   | Como um administrador, eu quero ver todos os portais de notícias cadastrados, para que eu possa analisar os dados.      | - Criar tela de listagem de portais cadastrados<br>- Exibir nome, URL, status e data de cadastro                                                                                                                                                                                                                 | Sprint 2 |
| 7   | Como um administrador, eu quero relacionar tags com outras tags, para que seja possível construir uma rede estruturada. | - Implementar funcionalidade para escolher tags relacionadas<br>- Permitir múltiplas relações entre tags                                                                                                                                                                                                         | Sprint 2 |
| 8   | Como um administrador, eu quero cadastrar portais de notícias, para que eu possa gerenciar as fontes de notícias.       | - Criar formulário de cadastro de portais<br>- Implementar botão de "Salvar" e "Cancelar"<br>- Automatizar salvamento da data de cadastro                                                                                                                                                                        | Sprint 2 |
| 9   | Como um administrador, eu quero editar portais de notícias, para que eu possa atualizar os dados de um portal.          | - Implementar interface de edição de portais<br>- Permitir edição de nome, URL, status e período de captura                                                                                                                                                                                                      | Sprint 2 |
| 10  | Como um administrador, eu quero capturar notícias de portais cadastrados, para que as informações sejam armazenadas.    | - Implementar agendamento de captura de notícias<br>- Armazenar título, URL, conteúdo, data de publicação, fonte, tags e jornalista<br>- Prevenir duplicação de dados                                                                                                                                             | Sprint 2 |
| 11  | Como um administrador, eu quero editar APIs de terceiros, para que eu possa editar as informações de onde os dados vêm. | - Criar interface de edição de APIs<br>- Permitir edição dos campos nome, URL e status                                                                                                                                                                                                                           | Sprint 3 |
| 12  | Como um administrador, eu quero consultar APIs cadastradas por meio de uma tela com filtros de pesquisa.                | - Criar tela de listagem de APIs com filtros<br>- Exibir nome, URL, status e data de cadastro                                                                                                                                                                                                                    | Sprint 3 |
| 13  | Como um usuário, eu quero consultar notícias por meio de uma tela com filtros de pesquisa, para que eu encontre notícias. | - Criar tela de consulta de notícias com filtros (título, data, tags, portal, jornalista, campo livre)                                                                                                                                                                                                           | Sprint 4 |
| 14  | Como um usuário, eu quero visualizar as tags relacionadas a uma notícia, para que eu possa entender melhor o conteúdo.  | - Exibir tags relacionadas na página de detalhes de notícias                                                                                                                                                                                                                                                    | Sprint 4 |


## Estruturação do Banco 

### Modelo conceitual 

![image](/img/Cerberus.jpg)

### Modelo físico


## Burndown do projeto

## Equipe

| Integrante | LinkedIn |
|------|----------|
| Clara Santos ![Static Badge](https://img.shields.io/badge/Scrum_master-pink)  ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/c137santos/) |
| Marília Borgo ![Static Badge](https://img.shields.io/badge/Product_owner-blue) ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mariliaborgo/) |
| Matheus Marciano ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/matheus-marciano-leite/) |
| Christiane Lima Alvares ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/cristiane-alvares/) |
| Guilherme Bezerra Junqueira ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilherme-bezerra-a01035170/) |
| Pedro Henrique Lopes de Souza ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pelopes7/) |
| Yan Costa Yamim ![Static Badge](https://img.shields.io/badge/Dev-black) | [![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/yan-yamim-185220278/) |
