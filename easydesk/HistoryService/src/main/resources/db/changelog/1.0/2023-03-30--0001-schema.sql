CREATE TABLE objects
(
    id            BIGSERIAL PRIMARY KEY,
    type          VARCHAR(1024),
    date          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ext_object_id UUID
);

CREATE TABLE fields
(
    id         BIGSERIAL PRIMARY KEY,
    object_id  BIGSERIAL REFERENCES objects(id),
    field_name VARCHAR(1024),
    value      VARCHAR(1024)
);