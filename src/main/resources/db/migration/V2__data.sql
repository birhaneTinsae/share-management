INSERT INTO privileges (id, name, created_at)
values (1, 'VIEW_ATTENDANCE', '2021-01-22 21:36:12'),
       (2, 'TAKE_ATTENDANCE', '2021-01-22 21:36:12'),
       (3, 'VIEW_ATTENDANCE_SUMMARY', '2021-01-22 21:36:12'),
       (4, 'VIEW_AGENDA', '2021-01-22 21:36:12'),
       (5, 'CREATE_AGENDA', '2021-01-22 21:36:12'),
       (6, 'DELETE_AGENDA', '2021-01-22 21:36:12'),
       (7, 'VIEW_AGENDA_SUMMARY', '2021-01-22 21:36:12'),
       (8, 'VIEW_REPORT', '2021-01-22 21:36:12'),
       (9, 'CREATE_CANDIDATE', '2021-01-22 21:36:12'),
       (10, 'VIEW_CANDIDATE', '2021-01-22 21:36:12'),
       (11, 'DELETE_CANDIDATE', '2021-01-22 21:36:12'),
       (12, 'CREATE_ROLE', '2021-01-22 21:36:12'),
       (13, 'VIEW_ROLE', '2021-01-22 21:36:12'),
       (14, 'DELETE_ROLE', '2021-01-22 21:36:12'),
       (15, 'UPDATE_ROLE', '2021-01-22 21:36:12'),
       (16, 'CREATE_VOTE', '2021-01-22 21:36:12'),
       (17, 'REVERSE_VOTE', '2021-01-22 21:36:12'),
       (18, 'REVERSE_ATTENDANCE', '2021-01-22 21:36:12'),
       (19, 'CREATE_ATTENDANCE', '2021-01-22 21:36:12');



INSERT INTO roles (id, name, created_at)
values (1, 'ADMIN', '2021-01-22 21:36:12'),
       (2, 'ENCODER', '2021-01-22 21:36:12'),
       (3, 'SUPER_ADMIN', '2021-01-22 21:36:12');

INSERT INTO roles_privileges (role_id, privilege_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (2, 1),
       (2, 2),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 10),
       (2, 16),
       (2, 17),
       (2, 18);
