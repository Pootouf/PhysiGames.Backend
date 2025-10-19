#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- Création de la base de données Keycloak si elle n'existe pas
    SELECT 'CREATE DATABASE ${KEYCLOAK_POSTGRES_DB}'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '${KEYCLOAK_POSTGRES_DB}')\gexec
EOSQL

echo "Databases created successfully!"
