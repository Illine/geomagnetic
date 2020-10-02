--liquibase formatted sql

--changeset eekovtun:1.3.0/ddl/shedlock
--rollback drop table geomagnetic.shedlock;
create table geomagnetic.shedlock
(
    name       varchar(64)  not null
        constraint shedlock_id_pk
            primary key,
    lock_until timestamp,
    locked_at  timestamp,
    locked_by  varchar(255) not null
);

comment on table geomagnetic.shedlock is 'Table stores locks for updating of forecasts';