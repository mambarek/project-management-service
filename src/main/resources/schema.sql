create table if not exists project
(
    id          bigint       not null primary key,
    budget      double precision,
    description varchar(500) not null,
    f_date      date,
    name        varchar(100) not null,
    pf_date     date,
    ps_date     date,
    public_id   uuid         not null unique,
    s_date      date,
    status      varchar(20)
);

create table if not exists project_step
(
    id          bigint       not null primary key,
    budget      double precision,
    description varchar(500) not null,
    f_date      date,
    name        varchar(100) not null,
    pf_date     date,
    ps_date     date,
    public_id   uuid         not null unique,
    s_date      date,
    status      varchar(20),
    project_id  uuid         not null
        constraint project_step_project_fk
            references project (public_id)
);

