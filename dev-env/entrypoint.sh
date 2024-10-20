git config --global --add safe.directory /app

sed -i 's/peer/trust/g' /etc/postgresql/16/main/pg_hba.conf

service postgresql start
psql -U postgres -c "CREATE USER ${DB_USERNAME} WITH SUPERUSER PASSWORD '${DB_PASSWORD}';"
createdb -U postgres $DB_NAME

echo "export DB_URL=\"jdbc:postgresql://localhost:5432/${DB_NAME}?user=${DB_USERNAME}&password=${DB_USERNAME}\"" >> /root/.zshrc

npm i
zsh