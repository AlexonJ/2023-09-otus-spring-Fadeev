INSERT INTO users (first_name, last_name, username, role, email, password)
VALUES ('Jeff', 'Bezos', 'jeff_bezos', 'ADMINISTRATOR', 'jeff.bezos@amazon.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC'),              -- adm
       ('Satya', 'Nadella', 'satya_nadella', 'DEVELOPER', 'satya.nadella@microsoft.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),         -- usr
       ('Larry', 'Page', 'larry_page', 'SUPPORT_AGENT', 'larry.page@google.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),              -- usr
       ('Marissa', 'Mayer', 'marissa_mayer', 'TEAM_LEAD', 'marissa.mayer@yahoo.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),             -- usr
       ('Jack', 'Dorsey', 'jack_dorsey', 'PROJECT_MANAGER', 'jack.dorsey@twitter.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),         -- usr
       ('Elon', 'Musk', 'elon_musk', 'ADMINISTRATOR', 'elon.musk@spacex.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC'),                -- adm
       ('Bill', 'Gates', 'bill_gates', 'DEVELOPER', 'bill.gates@microsoft.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),               -- usr
       ('Mark', 'Zuckerberg', 'mark_zuckerberg', 'SUPPORT_AGENT', 'mark.zuckerberg@facebook.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),  -- usr
       ('Sundar', 'Pichai', 'sundar_pichai', 'TEAM_LEAD', 'sundar.pichai@google.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),            -- usr
       ('Tim', 'Cook', 'tim_cook', 'PROJECT_MANAGER', 'tim.cook@apple.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),                 -- usr
       ('Customer', 'Customer', 'customer', 'CUSTOMER', 'customer@gmail.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),                        -- usr
       ('Admin', 'Admin', 'admin', 'ADMINISTRATOR', 'admin@supermail.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC'),                      -- adm
       ('Alexey', 'Fadeev', 'alexeyyf', 'ADMINISTRATOR', 'fadeevay84@gmail.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC')                        -- usr
;

INSERT INTO roles_authorities(role, authority)
VALUES ('ADMINISTRATOR', 'READ_TICKETS'),
       ('ADMINISTRATOR', 'CREATE_TICKETS'),
       ('ADMINISTRATOR', 'DELETE_TICKETS'),
       ('ADMINISTRATOR', 'READ_COMMENTS'),
       ('ADMINISTRATOR', 'CREATE_COMMENTS'),
       ('ADMINISTRATOR', 'DELETE_COMMENTS'),
       ('ADMINISTRATOR', 'READ_ATTACHMENTS'),
       ('ADMINISTRATOR', 'CREATE_ATTACHMENTS'),
       ('ADMINISTRATOR', 'DELETE_ATTACHMENTS'),
       ('DEVELOPER', 'READ_TICKETS'),
       ('DEVELOPER', 'MODIFY_TICKETS')

;
INSERT INTO users_categories(user_id, category)
VALUES
    (1, 'TECHNICAL_ISSUE'),
    (1, 'INFRASTRUCTURE'),
    (1, 'SECURITY_CONCERN'),

    (2, 'BUG_REPORT'),
    (2, 'FEATURE_REQUEST'),

    (12, 'TECHNICAL_ISSUE'),
    (12, 'INFRASTRUCTURE'),
    (12, 'SECURITY_CONCERN')
    ;