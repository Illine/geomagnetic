--liquibase formatted sql

--changeset eekovtun:1.3.1/grants/shedlock context:!unit-tests
--rollback revoke select, insert, update, delete on table geomagnetic.shedlock from geomagnetic;
grant select, insert, update, delete on table geomagnetic.shedlock to geomagnetic;