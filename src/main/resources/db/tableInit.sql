drop table if exists Book;
create table Book (
   id bigint generated by default as identity,
    author varchar(20) not null,
    title varchar(50) not null,
    isbn varchar(30) not null,
    primary key (id)
);