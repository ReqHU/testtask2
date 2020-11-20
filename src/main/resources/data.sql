DELETE FROM users_roles;
DELETE FROM roles;
DELETE FROM users;

ALTER SEQUENCE roles_seq RESTART WITH 1;

INSERT INTO users (LOGIN, NAME, PASSWORD) VALUES
    ('Vasya', 'Вася', 'Vasya1'),
    ('Petya', 'Петя', 'Petya2');

INSERT INTO roles (NAME) VALUES
    ('Админ'),
    ('Оператор'),
    ('Аналитик');

INSERT INTO users_roles (USER_LOGIN, ROLE_ID) VALUES
    ('Vasya', 1),
    ('Vasya', 2),
    ('Petya', 2),
    ('Petya', 3);