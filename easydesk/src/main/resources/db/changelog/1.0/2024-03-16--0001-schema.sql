CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    username   VARCHAR(30),
    password   VARCHAR(100),
    role       VARCHAR(50),
    email      VARCHAR(1024)
);

CREATE TABLE users_categories
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGSERIAL REFERENCES users (id),
    category VARCHAR(50)
);

CREATE TABLE roles_authorities
(
    id        BIGSERIAL PRIMARY KEY,
    role      VARCHAR(50),
    authority VARCHAR(50)
);

CREATE TABLE processes
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(50),
    category          VARCHAR(50),
    starting_point_id BIGSERIAL REFERENCES processes (id)
);

CREATE TABLE waypoints
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50),
    process_id BIGSERIAL REFERENCES processes (id)

);

CREATE TABLE tickets
(
    id             BIGSERIAL PRIMARY KEY,
    code           VARCHAR(6),
    title          VARCHAR(1024),
    description    TEXT,
    category       VARCHAR(50),
    priority       VARCHAR(50),
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    due_date       TIMESTAMP,
    created_by_id  BIGSERIAL REFERENCES users (id),
    assigned_to_id BIGSERIAL REFERENCES users (id),
    assigned_by_id BIGSERIAL REFERENCES users (id),
    waypoint_id    BIGSERIAL REFERENCES waypoints (id)
);

CREATE TABLE waypoints_relations
(
    id               BIGSERIAL PRIMARY KEY,
    waypoint_id      BIGSERIAL REFERENCES waypoints (id),
    next_waypoint_id BIGSERIAL REFERENCES waypoints (id)
);

CREATE TABLE comments
(
    id            BIGSERIAL PRIMARY KEY,
    created_at    TIMESTAMP,
    created_by_id BIGSERIAL REFERENCES users (id),
    content       TEXT,
    ticket_id     BIGSERIAL REFERENCES tickets (id)
);

CREATE TABLE attachments
(
    id            BIGSERIAL PRIMARY KEY,
    filename      VARCHAR(255),
    type          VARCHAR(255),
    created_at    TIMESTAMP,
    created_by_id BIGSERIAL REFERENCES users (id),
    content       BYTEA,
    size          BIGINT,
    ticket_id     BIGSERIAL REFERENCES tickets (id)
);