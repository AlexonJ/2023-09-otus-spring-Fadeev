INSERT INTO users (username, role, email, password)
VALUES ('jeff_bezos', 'ADMINISTRATOR', 'jeff.bezos@amazon.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC'), -- adm
       ('satya_nadella', 'DEVELOPER', 'satya.nadella@microsoft.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('larry_page', 'SUPPORT_AGENT', 'larry.page@google.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('marissa_mayer', 'TEAM_LEAD', 'marissa.mayer@yahoo.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('jack_dorsey', 'PROJECT_MANAGER', 'jack.dorsey@twitter.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('elon_musk', 'ADMINISTRATOR', 'elon.musk@spacex.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('bill_gates', 'DEVELOPER', 'bill.gates@microsoft.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('mark_zuckerberg', 'SUPPORT_AGENT', 'mark.zuckerberg@facebook.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('sundar_pichai', 'TEAM_LEAD', 'sundar.pichai@google.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('tim_cook', 'PROJECT_MANAGER', 'tim.cook@apple.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'),
       ('customer', 'CUSTOMER', 'customer@gmail.com', '$2a$12$7jmkn4CHr61te.s6YI2BJO925zzSnTdYPzhDx5skHtoYc4FnOuAAu'), -- usr,
       ('admin', 'ADMINISTRATOR', 'admin@supermail.com', '$2a$12$WgTiyMzOuqjID1J.SWj50ulVD5VYKk9BS9B2YegG/XQ.6p/YaeyXC') -- adm,


;

INSERT INTO roles_authorities(role, authority)
VALUES ('ADMINISTRATOR', 'READ_TICKETS'),
       ('ADMINISTRATOR', 'CREATE_TICKETS');