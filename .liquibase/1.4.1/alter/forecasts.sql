--liquibase formatted sql

--changeset eekovtun:1.4.1/alter/forecasts
--rollback comment on column geomagnetic.forecasts.forecast_time is 'A forecast time';
--rollback comment on column geomagnetic.forecasts.forecast_date is 'A forecast date';
comment on column geomagnetic.forecasts.forecast_time is 'A forecast time at UTC';
comment on column geomagnetic.forecasts.forecast_date is 'A forecast date at UTC';