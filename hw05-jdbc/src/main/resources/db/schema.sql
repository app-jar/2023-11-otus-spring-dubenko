create table Author (
    id bigserial primary key,
    name varchar(128)
);

create table Book (
    id bigserial primary key,
    name varchar(128),
    author_id bigint,
    foreign key (author_id) references Author (id)
);

create table Category (
    id bigserial primary key,
    name varchar(128)
);

create table Book_Category (
     book_id bigint,
     category_id bigint,
     foreign key (book_id) references Book (id) on delete cascade,
     foreign key (category_id) references Category (id) on delete cascade
);