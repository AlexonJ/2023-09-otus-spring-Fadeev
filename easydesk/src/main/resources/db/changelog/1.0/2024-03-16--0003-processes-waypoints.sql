INSERT INTO processes (name, category, starting_point_id)
VALUES ('Technical issues', 'TECHNICAL_ISSUE', 1),
       ('Security issues', 'SECURITY_CONCERN', 1);

INSERT INTO waypoints (process_id, name)
VALUES (1, 'Create'),
       (1, 'Review'),
       (1, 'Approval'),
       (1, 'Completion'),
       (1, 'Close'),
       (1, 'Archive');

INSERT INTO waypoints_relations (waypoint_id, next_waypoint_id)
VALUES (1, 2),
       (1, 3),
       (1, 5),
       (2, 3),
       (2, 5),
       (3, 4),
       (3, 5),
       (4, 5);