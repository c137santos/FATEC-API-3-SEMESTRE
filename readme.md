# Spring Boot and Vue3 App Monorepo

## Description

This repository is a proof of concept (POC) and a template for a health and smooth development enviroment for a Web App using [Spring Boot](https://spring.io/guides/gs/spring-boot) as backend and [Vue3](https://vuejs.org/) as frontend.

### How?

It uses a customized [Ubuntu Image](https://hub.docker.com/_/ubuntu) from [Docker](https://www.docker.com/) to create a minimazed container with [Java](https://www.java.com/en/) and [NodeJS](https://nodejs.org/en), to standarize versions across developer computers, [NPM](https://www.npmjs.com/) package [NX](https://nx.dev/) for task management, and a default [Postgres container](https://hub.docker.com/_/postgres) as database.

You can see versions used in [.env](/.env)

### Something else?

The container also uses the custom [Oh My ZSH](https://ohmyz.sh/) theme, [Flower Dance](https://github.com/MarcyLeite/flower-dance-omzsh), to make the development process fun :D

## Requirements

- Docker Engine;
- Visual Studio Code.

## How to use it

### Prepare

1. Setup Container

   1.1. Create image and container using command:

   ```bash
   docker compose up -d
   ```

   This will create image from `Dockerfile.dev`, and create containers using `docker-compose.yml`

   1.2. If `Dockerfile.dev` changes, execute:

   ```bash
   docker compose up -d --build
   ```

   This will rebuild the image.

2. Install Docker extension in VSCode;

   2.1. Click the remote icon icon (Two Arrows at the bottom-left corner);

   2.2. Select `Attach to Running container...` > `<your-folder-name>-dev-env-1`;

   2.3. Open /home/`<your-username>`/app/.

3. Install npm packages.

```bash
npm i
```

### Tasks

From there, you have some tasks predefined with `npm run <command>`, that will make you development exprience easier.

| Command     | Description                                                                                                                           |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| dev:back    | Runs Spring Boot Web Server in back-end folder                                                                                        |
| dev:front   | Runs Vue3 Dev server with hot-reload in front-end folder                                                                              |
| dev         | Runs both dev:back and dev:front                                                                                                      |
| build:back  | Creates a build folder with a .jar from Spring Boot Web server in `./backend/target`                                                  |
| build:front | Creates a Vue3 wepage in /frontend/dist                                                                                               |
| build       | Build the frontend pages, copy's it to backend as a Spring Boot Static and then builds backend                                        |
| bump        | Increses project version as a Major ([Read More](https://semver.org/)) in files `package.json` and `backend/pom.xml`                                         |
| publish     | Bumps project, build it, commit's version changes, creates tags and publishes changes and tag to remote `origin main` (In that order) |
