# Backlog - projeto Cérberus 

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

# Descrição detalhada User Stories

## Sprint 1

- **Como** um administrador, **eu quero** cadastrar APIs de terceiros, **para que** eu possa integrar e coletar dados de notícias de diferentes fontes.
    
    Critérios de aceitação:
    
    - O formulário de cadastro deve incluir os seguintes campos obrigatórios:
        - **Nome da API**: Campo de texto para o nome da API.
        - **URL da API**: Campo de texto que aceita apenas URLs válidas.
        - **Status Ativo/Inativo**: Checkbox ou campo de seleção para definir se a API está ativa.
        - **Período para a captura e dados:** diariamente, semanalmente ou mensalmente**.**
    - O botão "Salvar" deve estar disponível para confirmar o cadastro, e o botão "Cancelar" deve permitir descartar as mudanças e retornar à listagem de APIs.
    - Deve ser salvo automaticamente a data de cadastro, a partir da confirmação de salvamento do usuário.
- **Como** um administrador, **eu quero** capturar dados de Apis previamente cadastradas, **para que** as informações sejam armazenadas no banco de dados para consulta futura.
    - O sistema deve permitir o agendamento de capturas automáticas de dados de APIs em intervalos regulares (diariamente, semanalmente ou mensalmente.)
    - Os dados capturados das APIs devem ser armazenados no banco de dados, incluindo:
        - **Nome da API**
        - **Data e hora da captura**
        - **Conteúdo capturado** (formatado de acordo com os dados fornecidos pela API)
    - O sistema deve ser otimizado para capturar e armazenar grandes volumes de dados de maneira eficiente, minimizando o impacto no desempenho do sistema.
- **Como** um administrador, **eu quero** cadastrar tags, **para que** as notícias possam ser classificadas e relacionadas de forma eficaz.
    
    Critérios de aceitação:
    
    - O formulário de cadastro deve incluir os seguintes campos obrigatórios:
        - **Nome da Tag**: Campo de texto para o nome da tag.
        - **Descrição**: Campo de texto opcional para uma breve descrição da tag.
    - O botão "Salvar" deve estar disponível para confirmar o cadastro, e o botão "Cancelar" deve permitir descartar as mudanças e retornar à listagem de tags.
    - a data de criação da tag deve ser
- **Como** um administrador, **eu quero** editar tags, para que eu possa atualizar informações previamente cadastradas.
    
    Critérios de aceitação:
    
    - O sistema deve permitir que o administrador acesse a interface de edição de tags a partir da tela de listagem de tags, clicando em um botão "Editar" ao lado da tag que deseja modificar.
    - A interface de edição deve exibir um formulário pré-preenchido com as informações atuais da tag, permitindo que o administrador edite os seguintes campos:
        - **Nome da Tag**: Campo de texto para editar o nome da tag.
        - **Descrição**: Campo de texto opcional para editar a descrição da tag.
    - A data de criação deve ser atualizada automaticamente após a confirmação de salvamento do usuário.
    - Ao clicar em "Excluir", o sistema deve exibir um modal de confirmação para evitar exclusões acidentais.
- **Como** um administrador, **eu quero** excluir tags, considerando que tags podem ficar obsoletas e não fazer mais sentido no meu sistema.
    
    Critérios de aceitação:
    
    - O sistema deve permitir que o administrador exclua uma tag a partir da tela de listagem de tags.
    - Deve haver um botão "Excluir" ou um ícone de lixeira ao lado de cada tag na listagem, que, ao ser clicado, permita iniciar o processo de exclusão.

## Sprint 2

- Como um administrador, eu quero ver todos os portais de noticias cadastrados para que eu possa analisar quais dados serão buscados.
    
    Critério de aceitação:
    
    - Deve haver uma tela acessível a partir do menu principal ou de uma área de administração que exiba todos os portais de notícias cadastrados.
    - A listagem deve incluir os seguintes detalhes para cada portal:
        - **Nome do Portal**: Exibido como título ou coluna principal.
        - **URL do Portal**: Exibida como uma coluna ou campo adicional.
        - **Status (Ativo/Inativo)**: Exibido claramente, com uma indicação visual (e.g., cor ou ícone) que diferencie portais ativos de inativos.
        - **Data de Cadastro**: Exibida em uma coluna para permitir análise do histórico.
- Como um administrador eu quero poder relacionar tags com outras tags, para que seja possível construir uma rede de informações estruturadas.
    
    Critérios de aceitação:
    
    - A partir da tela de edição de tags deve-se ser possível escolher em uma lista de tags quais tags serão relacionadas.
    - Uma tag pode ser relacionada com uma, nenhuma ou várias tags.
- **Como** um administrador, **eu quero** cadastrar portais de notícias, **para que** eu possa gerenciar as fontes de onde as notícias serão capturadas.
    
    Critérios de aceitação:
    
    - O formulário de cadastro deve conter os seguintes campos obrigatórios:
        - **Nome do Portal**: Campo de texto.
        - **URL do Portal**: Campo de texto que aceita apenas URLs válidas.
        - **Status Ativo/Inativo**: Campo de seleção ou checkbox para definir se o portal está ativo.
    - A data de criação do portal, deve ser salvo automaticamente a partir da confirmação do usuário.
    - O botão de "Salvar" deve estar presente e ativado apenas se todos os campos obrigatórios estiverem preenchidos corretamente.
    - O botão de "Cancelar" deve estar disponível para permitir ao usuário descartar as alterações e retornar à lista de portais sem salvar.
- **Como** um administrador, **eu quero** editar portais de notícias, para que eu possa atualizar os dados de um determinado portal.
    
    Critérios de aceitação:
    
    - A funcionalidade de edição deve estar acessível a partir da tela de listagem dos portais de notícias, com um botão ou link "Editar" ao lado de cada portal.
    - Ao clicar em "Editar", o administrador deve ser redirecionado para um formulário pré-preenchido com os dados atuais do portal selecionado.
    - O formulário de edição deve conter os seguintes campos editáveis:
        - **Nome do Portal**: Campo de texto.
        - **URL do Portal**: Campo de texto que aceita apenas URLs válidas.
        - **Status Ativo/Inativo**: Campo de seleção ou checkbox para definir se o portal está ativo.
        - **Período para a captura e dados:** diariamente, semanalmente ou mensalmente**.**
    - Após atualização o campo da data de criação deve ser automaticamente atualizado após a confirmação do usuário.
- **Como** um administrador, **eu quero** capturar notícias de portais previamente cadastradas, **para que** as informações sejam armazenadas no banco de dados para consulta futura.
    - O sistema deve permitir que o administrador agende capturas automáticas em intervalos regulares (diariamente, semanalmente ou mensalmente)
    - As notícias capturadas devem ser armazenadas no banco de dados, incluindo os seguintes dados:
        - **Título da Notícia**
        - **URL da Notícia**
        - **Conteúdo da Notícia** (texto completo ou resumo)
        - **Data de Publicação**
        - **Fonte/Portal**
        - **Tags** (se disponíveis)
        - **Jornalista** (Que deve ser criado no momento do salvamento da notícia)
    - As notícias duplicadas (já capturadas previamente) devem ser identificadas e ignoradas, evitando duplicação no banco de dados.
    - O sistema deve ser otimizado para capturar e armazenar grandes volumes de notícias de maneira eficiente, minimizando o impacto no desempenho do sistema.

## Sprint 3

- **Como** um administrador, **eu quero** Editar APIs de terceiros, para que eu possa editar as infomações de onde os dados estão vindo.
    
    Critérios de aceitação:
    
    - O sistema deve permitir que o administrador acesse a interface de edição de APIs a partir da tela de listagem de APIs, clicando em um botão "Editar" ao lado da API que deseja modificar.
    - A interface de edição deve exibir um formulário pré-preenchido com as informações atuais da API, permitindo que o administrador edite os seguintes campos:
        - **Nome da API**: Campo de texto para editar o nome da API.
        - **URL da API**: Campo de texto para editar a URL da API, aceitando apenas URLs válidas.
        - **Status Ativo/Inativo**: Checkbox ou campo de seleção para alterar o status da API (ativa ou inativa).
- **Como** um administrador, **eu quero** consultar as APIs cadastradas por meio de uma tela com filtros de pesquisa, **para que** eu possa gerenciar e verificar a integração com as fontes de dados.
    
    Critérios de aceitação:
    
    - A listagem deve incluir os seguintes detalhes para cada API:
        - **Nome da API**: Exibido como título ou coluna principal.
        - **URL da API**: Exibida em uma coluna ou campo adicional.
        - **Status (Ativo/Inativo)**: Exibido claramente, com uma indicação visual (e.g., cor ou ícone) que diferencie APIs ativas de inativas.
        - **Data de Cadastro**: Exibida em uma coluna para permitir análise do histórico.

## Sprint 4

- **Como** um usuário, **eu quero** consultar notícias por meio de uma tela com filtros de pesquisa, **para que** eu possa encontrar notícias relevantes de maneira rápida e eficiente.
    - Dentro o sistema existe uma tela dedicada a consulta de notícias que opssuem os seguintes sistemas de filtro:
        - **Título**: Campo de texto para buscar por palavras-chave no título das notícias.
        - **Data de Publicação**: Filtro para selecionar um intervalo de datas.
        - **Tags**: Campo de seleção ou de múltipla escolha para filtrar notícias por tags específicas.
        - **Portal**: Campo de seleção para filtrar notícias por portal de origem.
        - **Jornalista**: Campo de seleção para buscar notícias por jornalista específico.
        - **Campo livre:** onde é possível buscar palavras contidas na notícia.
- **Como** um usuário, **eu quero** visualizar as tags que estão relacionadas a uma notícia, **para que** eu possa entender melhor o contexto e a categorização do conteúdo.
    - As tags relacionadas a uma notícia devem ser exibidas na página de detalhes da notícia.
    - As tags devem ser exibidas logo abaixo do título ou no final do artigo, de forma destacada e de fácil visualização.