--liquibase formatted sql

--changeset eekovtun:1.0.0/grants/geomagnetic context:!unit-tests
--rollback revoke all on schema geomagnetic from geomagnetic;
grant usage on schema geomagnetic to geomagnetic;
grant execute on all functions in schema geomagnetic to geomagnetic;
grant select, insert, update, delete on all tables in schema geomagnetic to geomagnetic;
grant usage, select on all sequences in schema geomagnetic to geomagnetic;