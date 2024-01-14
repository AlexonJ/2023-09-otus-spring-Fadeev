CREATE TABLE authors
(
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255)
);

CREATE TABLE genres
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE comments
(
    id         BIGSERIAL PRIMARY KEY,
    book_id    BIGSERIAL REFERENCES comments (id) ON DELETE CASCADE,
    content    VARCHAR(1024)
);

CREATE TABLE books
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255),
    author_id  BIGINT REFERENCES authors (id) ON DELETE CASCADE,
    comment_id BIGINT REFERENCES comments (id) ON DELETE CASCADE
);

CREATE TABLE books_genres
(
    book_id  BIGINT REFERENCES books (id) ON DELETE CASCADE,
    genre_id BIGINT REFERENCES genres (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(64),
    password   VARCHAR(1024),
    role       VARCHAR(255)
);