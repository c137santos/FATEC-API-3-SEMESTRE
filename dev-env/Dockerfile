FROM ubuntu:noble

USER root

RUN apt update -y
RUN apt upgrade -y
RUN apt install -y

# Configure Container
RUN apt install -y locales locales-all tzdata
ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8
ENV TZ America/Sao_Paulo

# Install Dependencies
RUN apt install -y curl xz-utils

# Install Git
RUN apt install -y git

# Install Java
ARG JAVA_VERSION
RUN apt install -y openjdk-${JAVA_VERSION}-jdk

# Install Node
ARG NODE_VERSION
RUN curl -o /root/node.tar.xz https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-x64.tar.xz
RUN tar -xf /root/node.tar.xz -C .
RUN mv node-v${NODE_VERSION}-linux-x64 /opt/node
RUN ln -s /opt/node/bin/node /usr/local/bin/node && ln -s /opt/node/bin/npm /usr/local/bin/npm && ln -s /opt/node/bin/npx /usr/local/bin/npx
RUN npm config set prefix /usr/local
RUN rm /root/node.tar.xz

# Install Postgres
ARG POSTGRES_VERSION
RUN apt install -y postgresql-${POSTGRES_VERSION} postgresql-contrib

# Install Gitmoji
RUN npm i -g gitmoji-cli

# Install OMZ (for flavor)
RUN apt install -y zsh
RUN sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
RUN curl -o /root/.oh-my-zsh/themes/flower-dance.zsh-theme https://raw.githubusercontent.com/MarcyLeite/flower-dance-omzsh/main/flower-dance.zsh-theme
RUN sed -i 's/ZSH_THEME="robbyrussell"/ZSH_THEME="flower-dance"/' /root/.zshrc
RUN cp -r /root/.oh-my-zsh /etc/skel/.oh-my-zsh
RUN cp /root/.zshrc /etc/skel/.zshrc
RUN chsh -s /bin/zsh root

RUN mkdir /app
WORKDIR /app

EXPOSE 8080
EXPOSE 5173
EXPOSE 5433

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh

ENTRYPOINT sh /entrypoint.sh
CMD [ "zsh" ]