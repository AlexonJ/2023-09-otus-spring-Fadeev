insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into comments(book_id, content)
values (1, 'Comment_1_for_book_1'), (1, 'Comment_2_for_book_1'),
       (2, 'Comment_1_for_book_2'), (2, 'Comment_2_for_book_2'),
       (3, 'Comment_1_for_book_3'), (3, 'Comment_2_for_book_3');

insert into users(username, password)
values ('admin', '$2y$10$fsBa.mRJCU7YUC9msaasXOyHQlVIWj0F6i5W.yeUpc7OF..dDNLWe'), -- password: pwd
       ('customer', '$2y$10$FD2GIs/aKP46U5aCD.jKyeJPbxanvN6Tk0NHO2jrBTsO/fvwMPQW.'), -- password: usr
       ('manager', '$2y$10$FD2GIs/aKP46U5aCD.jKyeJPbxanvN6Tk0NHO2jrBTsO/fvwMPQW.'), -- password: usr
       ('auditor', '$2y$10$FD2GIs/aKP46U5aCD.jKyeJPbxanvN6Tk0NHO2jrBTsO/fvwMPQW.'); -- password: usr

-- Roles
insert into roles(role_name)
values ('READ'), ('WRITE'), ('DELETE'), ('BOOKS_ACCESS'), ('AUTHORS_ACCESS'), ('GENRES_ACCESS');

-- users_roles
insert into users_roles(user_id, role_id)
--    + READ  + WRITE +DELETE +BOOKS  +AUTHORS+GENRES+
values (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),       -- admin    - all roles
       (2, 1), (2, 2),         (2, 4),                       -- customer - can read and edit books
       (3, 1), (3, 2),                 (3, 5), (3, 6),       -- manager  - read and edit authors and genres
       (4, 1),                 (4, 4), (4, 5), (4, 6);       -- auditor  - read everything