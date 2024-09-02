FOR /F "tokens=*" %%i in ('type dev-env.config') do SET %%i

for /f "tokens=*" %%i in ('git config --global user.name') do SET GIT_USER=%%i
for /f "tokens=*" %%i in ('git config --global user.email') do SET GIT_EMAIL=%%i

docker compose up -d --build