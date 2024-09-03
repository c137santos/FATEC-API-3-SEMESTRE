export $(cat dev-env.config)

export GIT_USER=$(git config --global user.name)
export GIT_EMAIL=$(git config --global user.email)
export D_UID=$(id -u)
export D_GID=$(id -g)

docker compose up -d --build