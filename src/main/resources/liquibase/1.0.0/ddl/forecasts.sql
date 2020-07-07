--liquibase formatted sql

--changeset eekovtun:1.0.0/ddl/forecasts_seq
--rollback drop sequence geomagnetic.forecasts_seq;
create sequence geomagnetic.forecasts_seq;

--changeset eekovtun:1.0.0/ddl/forecasts
--rollback drop table geomagnetic.forecasts;
create table geomagnetic.forecasts
(
    id            bigint       default nextval('geomagnetic.forecasts_seq'::regclass) not null
        constraint forecasts_pk primary key,
    index         integer                                                 not null,
    forecast_time time                                                    not null,
    forecast_date date                                                    not null,
    created       timestamp(0) default now(),
    updated       timestamp(0) default now(),
    active        boolean      default true
);

comment on table geomagnetic.forecasts is 'A table stores a parsed forecast from SWPC NOAA';
comment on column geomagnetic.forecasts.index is 'A geomagnetic index';
comment on column geomagnetic.forecasts.forecast_time is 'A forecast time';
comment on column geomagnetic.forecasts.forecast_date is 'A forecast date';
comment on column geomagnetic.forecasts.created is 'A created date';
comment on column geomagnetic.forecasts.updated is 'A updated date';
comment on column geomagnetic.forecasts.active is 'A soft deleted flag: true - active, false - deleted';

create index forecasts_time_inx on geomagnetic.forecasts (forecast_time);
create index forecasts_date_inx on geomagnetic.forecasts (forecast_date);