INSERT INTO processes (name)
VALUES ('Process 1'),
       ('Process 2'),
       ('Process 3');

INSERT INTO waypoints (process_id, name)
VALUES (1, 'Start'),
       (1, 'Review'),
       (1, 'Approval'),
       (1, 'Completion'),
       (2, 'Start'),
       (2, 'Review'),
       (2, 'Approval'),
       (2, 'Completion');