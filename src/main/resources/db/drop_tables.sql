alter table project_step
    drop constraint project_step_project_fk;
drop table if exists project cascade;
drop table if exists project_step cascade;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 1 increment 1;
