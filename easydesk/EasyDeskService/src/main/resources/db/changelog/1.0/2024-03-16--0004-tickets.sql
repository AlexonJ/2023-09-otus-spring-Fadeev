INSERT INTO tickets (code, title, description, category, priority, created_at, updated_at, due_date, created_by_id,
                     assigned_to_id, assigned_by_id, waypoint_id, external_id)
VALUES ('TCH001', 'Database connection issue', 'Unable to connect to the database server.', 'TECHNICAL_ISSUE','HIGH',
        '2024-03-15 10:00:00', '2024-03-15 10:00:00', '2024-03-18 10:00:00', 1, 1, 1, 1, '6b6a288e-a087-4e96-8548-d05f37458235'),
       ('FRQ001', 'Feature request: User profile page', 'Requesting a new feature to implement a user profile page.', 'FEATURE_REQUEST',
        'MEDIUM', '2024-03-15 11:00:00', '2024-03-15 11:00:00', '2024-03-22 11:00:00', 2, 1, 3, 2, '80c440fa-6d42-438e-9dc0-142d9552ee98'),
       ('INF001', 'Server maintenance required', 'Server maintenance needed to address performance issues.', 'INFRASTRUCTURE','BLOCKED',
        '2024-03-15 12:00:00', '2024-03-15 12:00:00', '2024-03-20 12:00:00', 3, 1, 4, 3, 'e6fbd173-32a0-4b94-a91b-aeb07114f322');