insert into Author (name) values
('a1'),      -- 1
('a2');      -- 2

insert into Book (name, author_id) values
('a1b1', 1),            -- 1
('a1b2', 1),            -- 2
('a2b1', 2);            -- 3

insert into Category (name) values
('c1'),             -- 1
('c2'),             -- 2
('c3');            -- 3

insert into Book_Category (book_id, category_id) values
(1, 1), (1, 2),
(3, 3);