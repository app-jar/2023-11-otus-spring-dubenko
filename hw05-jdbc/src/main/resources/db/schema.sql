create table Author (
    id bigserial primary key,
    name varchar(128)
);

create table Book (
    id bigserial primary key,
    name varchar(128),
    author_id bigint,
    foreign key (author_id) references author (id)
)