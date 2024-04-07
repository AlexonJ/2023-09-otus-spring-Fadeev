#!/bin/bash

set -e
set -u

function create_user_and_database() {
    local database=$(echo $1 | tr ',' ' ' | awk  '{print $1}')
    local owner=$(echo $1 | tr ',' ' ' | awk  '{print $2}')
    echo "  Creating user '$owner' if it doesn't exist"

    # Check if the user already exists
    if ! psql -U "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_roles WHERE rolname='$owner'" | grep -q 1; then
        # User does not exist, so create it
        psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
            CREATE USER $owner;
EOSQL
        echo "User '$owner' created"
    else
        echo "User '$owner' already exists"
    fi

    echo "  Creating database '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE DATABASE $database;
        GRANT ALL PRIVILEGES ON DATABASE $database TO $owner;
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
    for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ':' ' '); do
        create_user_and_database $db
    done
    echo "Multiple databases created"
fi
