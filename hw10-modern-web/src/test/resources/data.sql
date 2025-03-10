insert into authors(full_name)
values ('Александр Пушкин'),
       ('Лев Толстой'),
       ('Алексей Толстой');


insert into books(title, author_id)
values ('Кавказский Пленник', 1),
       ('Кавказский Пленник', 2),
       ('Репка', 3);


insert into comments(book_id, text)
values (1, 'Comment_1'),
       (2, 'Comment_2'),
       (3, 'Comment_3'),
       (3, 'Comment_4');
