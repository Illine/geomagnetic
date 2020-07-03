FROM postgres:12-alpine

ENV TZ=Europe/Moscow \
    POSTGRES_DB=weather \
    POSTGRES_USER=weather \
    POSTGRES_PASSWORD=weather

COPY geomagnetic_init.sql /docker-entrypoint-initdb.d/geomagnetic_init.sql