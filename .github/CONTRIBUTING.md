
## Vai realizar um commit- ATENÇÃO

Caso esteja tratando de uma migração (mudança no banco), você deve usar o padrão de nomeclatura para cada migração, além de guarda o SQL na pasta adequada. 

* Prefixo: deve iniciar com V em maiúsculo
* Versão: número da versão, deve ser incremental e pode ser separada por . ou _ como 1.1, 1_1, 2, 2.2
* Separador: usamos o __ (2 underscores)
* Descrição: breve descrição do que esta sendo alterador

```
v1__descricao_migracao.sql
```

## Para desenvolvedores:

Este repositório é uma prova de conceito (POC) e um modelo para um ambiente de desenvolvimento saudável e suave para um aplicativo da Web usando [Spring Boot](https://spring.io/guides/gs/spring-boot) como backend e [Vue3](https://vuejs.org/) como frontend.

### Como?

Ele usa uma imagem personalizada do [Ubuntu](https://hub.docker.com/_/ubuntu) do [Docker](https://www.docker.com/) para criar um contêiner minimizado com [Java](https://www.java.com/en/) e [NodeJS](https://nodejs.org/en), para padronizar as versões nos computadores dos desenvolvedores, o pacote [NX](https://nx.dev/) do [NPM](https://www.npmjs.com/) para gerenciamento de tarefas e um contêiner padrão do [Postgres](https://hub.docker.com/_/postgres) como banco de dados.

Você pode ver as versões usadas em [dev-env.config](/dev-env.config)

### Algo mais?

O contêiner também usa o tema personalizado [Oh My ZSH](https://ohmyz.sh/), [Flower Dance](https://github.com/MarcyLeite/flower-dance-omzsh), para tornar o processo de desenvolvimento divertido :D

## Requisitos

- Docker Engine;
- Visual Studio Code.

## Como usar

### Preparar

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
