#!/usr/bin/env bash


create_db () {

    SQL_SCRIPT=$1.sql
    CT_NAME=$1
    CT_PORT=$2
    PROD_DESC=$3

    echo "create table product (id integer primary key, description varchar);" > $SQL_SCRIPT
    # Hibernate needs a sequence to get the PKs from
    echo "create sequence product_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;" >> $SQL_SCRIPT
    echo "insert into product (id, description) values (nextval('product_seq'), '$PROD_DESC');" >> $SQL_SCRIPT
    chmod 777 $SQL_SCRIPT

    # Create the container on the base of postgres 9.6 using our pre-defined SQL file, expose the port and detach from it;
    docker run --name $CT_NAME \
        --volume $(pwd)/$SQL_SCRIPT:/docker-entrypoint-initdb.d/$SQL_SCRIPT \
        --publish $CT_PORT:5432 \
        --env POSTGRES_USER=testuser --env POSTGRES_PASSWORD=password --env POSTGRES_DB=testdb \
        --detach \
        postgres:9.6

}


create_db mt_default 52001 "Je ne sais pas..."

create_db mt_tenant_de 52010 "Ein nützliches Werkzeug um irgendwas zu machen."

create_db mt_tenant_en 52011 "A useful tool to do something."

# Connects:
#   psql -h localhost -p 52001 testdb testuser -c "select * from product;"
#   psql -h localhost -p 52010 testdb testuser -c "select * from product;"
#   psql -h localhost -p 52011 testdb testuser -c "select * from product;"
