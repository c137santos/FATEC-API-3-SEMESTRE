# Setup Projeto

## Para desenvolvedores:

Este repositório é uma prova de conceito (POC) e um modelo para um ambiente de desenvolvimento saudável e suave para um aplicativo da Web usando [Spring Boot](https://spring.io/guides/gs/spring-boot) como backend e [Vue3](https://vuejs.org/) como frontend.

## Com docker
### Como?

Ele usa uma imagem personalizada do [Ubuntu](https://hub.docker.com/_/ubuntu) do [Docker](https://www.docker.com/) para criar um contêiner minimizado com [Java](https://www.java.com/en/) e [NodeJS](https://nodejs.org/en), para padronizar as versões nos computadores dos desenvolvedores, o pacote [NX](https://nx.dev/) do [NPM](https://www.npmjs.com/) para gerenciamento de tarefas e um contêiner padrão do [Postgres](https://hub.docker.com/_/postgres) como banco de dados.

Você pode ver as versões usadas em [dev-env.config](/dev-env.config)

### Algo mais?

O contêiner também usa o tema personalizado [Oh My ZSH](https://ohmyz.sh/), [Flower Dance](https://github.com/MarcyLeite/flower-dance-omzsh), para tornar o processo de desenvolvimento divertido :D

## Requisitos

- Docker Engine;
- Visual Studio Code.
- sette as variáveis de ambientes necessárias no .env

## Variáveis de ambientes necessárias

Para rodar o projeto é necessário criar um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```
JAVA_VERSION=17
NODE_VERSION=20.16.0
POSTGRES_VERSION=12.20
DB_URL=jdbc:postgresql://localhost:5432/cdd
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cdd
DB_USERNAME=cdd
DB_PASSWORD=cdd
```

## Desenvolvendo o ambiente full docker - devConteiner

1. Configurar o contêiner
   1.1. Crie a imagem e o contêiner usando o comando:

   ```bash
   # Linux
   sh ./scripts/up.sh
   
   # Windows
   .\\scripts\\up.bat
   ```

2. Instale a extensão Docker no VSCode;

   2.1. Clique no ícone de conexão remota (Duas setas no canto inferior esquerdo);

   2.2. Selecione `Attach to Running container...` > `<nome-da-sua-pasta>-dev-env-1`;

   2.3. Abra /root/app/.


### Desenvolvimento dev em localmente e postgresql no docker

1.  Npm install;

   1.1 Rode o comando `npm install` para instalar as dependências do projeto no terminal do VsCode conteiner.

   1.2 `docker compose build postgres`

   1.3 `docker compose up -d postgres`

2. Utilize os gatilhos

   2.1 `npm run dev` para rodar o backend e o frontend;


### Desenvolvimento dev localmente com banco local

1. Voce precisa instalar o postgresql

2. Crie um banco de dados chamado `cdd` com usuário `cdd` e senha `cdd`, conforme as variáveis de ambiente. 

3. Rode o comando `npm install` para instalar as dependências do projeto.

4. Rode o comando `npm run dev` para rodar o backend e o frontend. Realizando assim a migração do banco. 

ps: A criação do DB no windows pode gerar usuários que não tenham permissão para criar tabelas. Caso haja uma recusa de acesso as tabelas, procure dar permissionamento adequado ao usuário cdd.

## Setup do Banco. 

## Subir API Mockada
Esse repositório contém uma simples API feita em express que retorna um json aleatório, na pasta `mock/api`. Esse retorno sempre vai refletir o 
esse o json `mock-data.json`. Para rodar a api mockada, basta usar `npm run up-mock-api`, e para testar o CRON, modificar o `mock-data.json`

## Contexto

Nosso projeto contém o flyway e o hibernate. 

Hibernate é um framework ORM (Object-Relational Mapping) que mapeia objetos Java para tabelas de banco de dados.
Mas qualquer modificação deve ser feito manualmente via script do flyway.

Flyway é uma ferramenta de migração de banco de dados de código aberto. Ele traz ordem e controle para o processo de evolução de banco de dados.

### Para 1º migração

É necessário o postgresql está de pé e com conexões válidas para funcionar.
Portanto, ao rodar o comando `npm run dev` o flyway irá criar o banco de dados e as tabelas necessárias. 
Ou `mvnw clean spring-boot:run`

### Para migrações futuras

Caso esteja tratando de uma migração (mudança no banco), você deve usar o padrão de nomeclatura para cada migração, além de guarda o SQL na pasta adequada. 

* Prefixo: deve iniciar com V em maiúsculo
* Versão: número da versão, deve ser incremental e pode ser separada por . ou _ como 1.1, 1_1, 2, 2.2
* Separador: usamos o __ (2 underscores)
* Descrição: breve descrição do que esta sendo alterador

```
V2__descricao_migracao.sql
```

### Tarefas

Comandos automatizados com `npm run <comando>`, que tornarão sua experiência de desenvolvimento mais fácil.

| Comando     | Descrição                                                                                                                           |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| dev:back    | Executa o servidor da Web Spring Boot na pasta back-end                                                                                        |
| dev:front   | Executa o servidor de desenvolvimento Vue3 com hot-reload na pasta front-end                                                                              |
| dev         | Executa tanto dev:back quanto dev:front                                                                                                      |
| build:back  | Cria uma pasta de build com um arquivo .jar do servidor da Web Spring Boot em `./backend/target`                                                  |
| build:front | Cria uma página Vue3 em /frontend/dist                                                                                               |
| build       | Compila as páginas do frontend, copia para o backend como um Static do Spring Boot e, em seguida, compila o backend                                        |
| bump        | Aumenta a versão do projeto como Major ([Leia mais](https://semver.org/)) nos arquivos `package.json` e `backend/pom.xml`                                         |
| publish     | Aumenta a versão do projeto, compila, faz commit das alterações de versão, cria tags e publica as alterações e tags no remoto `origin main` (nessa ordem) |
