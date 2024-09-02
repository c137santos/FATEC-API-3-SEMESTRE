FOR /F "tokens=*" %%i in ('type dev-env.config') do SET %%i

for /f "tokens=*" %%i in ('git config --global user.name') do SET GIT_USER=%%i
for /f "tokens=*" %%i in ('git config --global user.email') do SET GIT_EMAIL=%%i

SET D_UID=1000
SET D_GID=1000

docker compose up -d --build