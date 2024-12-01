#!/bin/bash
set -e

# Create user if it doesn't exist
# psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
#    DO \$\$
#    BEGIN
#        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname='docker') THEN
#            CREATE USER docker WITH PASSWORD 'pswd-docker';
#            CREATE DATABASE docker;
#            ALTER DATABASE docker OWNER TO docker;
#        ELSE
#            RAISE NOTICE 'User "docker" already exists. Skipping user creation.';
#        END IF;
#   END
#    \$\$;
#EOSQL

# Connect to the docker database and create tables
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER docker WITH PASSWORD 'pswd-docker';
    CREATE DATABASE docker;
    ALTER DATABASE docker OWNER TO docker;
    \c docker

    CREATE TABLE IF NOT EXISTS useracct (
        id SERIAL UNIQUE,
        email VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(60) NOT NULL,
        active BOOLEAN NOT NULL,
        first varchar(20),
        last varchar(20),
        PRIMARY KEY (id, email)
    );

    CREATE TABLE IF NOT EXISTS authority (
        email VARCHAR(50) REFERENCES useracct(email) ON DELETE CASCADE,
        role VARCHAR(20) NOT NULL,
        PRIMARY KEY (email, role)
    );


  ALTER TABLE useracct OWNER TO docker;
  ALTER TABLE authority OWNER TO docker;
EOSQL
