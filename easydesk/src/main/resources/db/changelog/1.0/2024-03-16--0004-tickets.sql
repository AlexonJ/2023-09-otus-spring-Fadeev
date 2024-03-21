INSERT INTO tickets (code, title, description, category, priority, created_at, updated_at, due_date, created_by_id,
                     assigned_to_id, waypoint_id)
VALUES ('TCH001', 'Database connection issue', 'Unable to connect to the database server.', 'TECHNICAL_ISSUE','HIGH',
        '2024-03-15 10:00:00', '2024-03-15 10:00:00', '2024-03-18 10:00:00', 1, 2, 1),
       ('FRQ002', 'Feature request: User profile page', 'Requesting a new feature to implement a user profile page.', 'FEATURE_REQUEST',
        'MEDIUM', '2024-03-15 11:00:00', '2024-03-15 11:00:00', '2024-03-22 11:00:00', 2, 3, 2),
       ('INF003', 'Server maintenance required', 'Server maintenance needed to address performance issues.', 'INFRASTRUCTURE','BLOCKED',
        '2024-03-15 12:00:00', '2024-03-15 12:00:00', '2024-03-20 12:00:00', 3, 1, 3);
