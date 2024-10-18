# Manual do Usuário - Cérberus

## Introdução

O *Cérberus* é uma ferramenta desenvolvida para realizar a captura e armazenamento de notícias estratégicas e dados associados, utilizando portais de notícias e APIs. Ele permite a consulta de notícias filtradas por diferentes critérios, como palavras-chave, jornalistas, portais e datas de publicação. O sistema também realiza a captura automática de informações via *scraping* de notícias e APIs registradas.

---

## Funcionalidades Disponíveis

### 1. Cadastro de APIs

O sistema permite o cadastro de APIs que fornecem dados estratégicos. Ao cadastrar uma API, o usuário pode:

- Definir o *nome da API*.
- Informar a *URL* da API.
- Marcar se a API está *ativa*.
- Definir o *período de captura* (diariamente, semanalmente, etc).

![Exemplo de cadastro de API](#) [Adicionar print aqui]

### 2. Cadastro de Portais

Além das APIs, o sistema permite cadastrar portais de notícias de onde os dados serão coletados via *scraping*. O usuário pode:

- Definir o *nome do portal*.
- Inserir a *URL do portal*.
- Associar *tags* ao portal (ex.: agricultura, tecnologia).

![Exemplo de cadastro de Portal](#) [Adicionar print aqui]

### 3. Cadastro de Tags

O usuário também pode cadastrar *tags* que ajudarão a categorizar e filtrar as notícias. No cadastro de tags, é possível:

- Definir o *nome da tag*.
- Adicionar uma *descrição* da tag.
- Associar *tags relacionadas* (ex.: soja, milho, exportação).

![Exemplo de cadastro de Tag](#) [Adicionar print aqui]

### 4. Consulta de Notícias

O *Cérberus* oferece uma tela de consulta para buscar notícias já capturadas. Os dados são obtidos por meio de *scraping* e armazenados no banco de dados, onde podem ser filtrados pelos seguintes critérios:

- *Palavras-chave*.
- *Data de publicação*.
- *Portal de origem*.
- *Jornalista*.
- *Tags associadas*.

![Exemplo de consulta de Notícias](#) [Adicionar print aqui]

---
