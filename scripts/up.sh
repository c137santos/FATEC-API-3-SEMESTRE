export $(cat dev-env.config)

export GIT_USER=$(git config --global user.name)
export GIT_EMAIL=$(git config --global user.email)

docker compose up -d --build