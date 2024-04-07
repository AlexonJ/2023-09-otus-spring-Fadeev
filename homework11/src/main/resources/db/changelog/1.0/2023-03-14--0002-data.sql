-- Authors
insert into authors(full_name)
values ('George Orwell'), ('Jane Austen'), ('F. Scott Fitzgerald'), ('Harper Lee'),
       ('Ernest Hemingway'), ('J.K. Rowling'), ('Leo Tolstoy'), ('Agatha Christie');

-- Genres
insert into genres(name)
values ('Science Fiction'), ('Romance'), ('Classic'), ('Mystery'), ('Adventure'),
       ('Fantasy'), ('Historical Fiction'), ('Crime');

-- Books
insert into books(title, author_id)
values ('1984', 1), ('Pride and Prejudice', 2), ('The Great Gatsby', 3),
       ('To Kill a Mockingbird', 4), ('The Old Man and the Sea', 5),
       ('Harry Potter and the Philosopher''s Stone', 6), ('War and Peace', 7),
       ('Murder on the Orient Express', 8), ('The Catcher in the Rye', 5),
       ('Crime and Punishment', 7), ('Harry Potter and the Chamber of Secrets', 6),
       ('Harry Potter and the Prisoner of Azkaban', 6), ('The Adventures of Sherlock Holmes', 8);
-- insert into books(title)
-- values ('1984'), ('Pride and Prejudice'), ('The Great Gatsby'),
--        ('To Kill a Mockingbird'), ('The Old Man and the Sea'),
--        ('Harry Potter and the Philosopher''s Stone'), ('War and Peace'),
--        ('Murder on the Orient Express'), ('The Catcher in the Rye'),
--        ('Crime and Punishment'), ('Harry Potter and the Chamber of Secrets'),
--        ('Harry Potter and the Prisoner of Azkaban'), ('The Adventures of Sherlock Holmes');
-- Comments
insert into comments(book_id, content)
values (1, 'Recommended to read'),
       (2, 'Have already been read');

-- Books Genres
insert into books_genres(book_id, genre_id)
values (1, 1), (2, 2), (2, 3), (3, 3), (4, 4), (5, 5), (5, 3),
       (6, 6), (7, 3), (7, 7), (8, 8), (9, 7), (9, 8), (10, 3), (10, 7),
       (11, 6), (11, 3), (12, 3), (12, 6), (13, 4), (13, 7);
