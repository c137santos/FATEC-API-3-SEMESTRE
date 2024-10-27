sudo service postgresql start
sudo psql -U postgres -c "CREATE USER ${DB_USERNAME} WITH SUPERUSER PASSWORD '${DB_PASSWORD}';"
sudo createdb -U postgres $DB_NAME


echo "export DB_URL=\"jdbc:postgresql://localhost:5432/${DB_NAME}?user=${DB_USERNAME}&password=${DB_USERNAME}\"" >> /home/ubuntu/.zshrc

npm i
zsh