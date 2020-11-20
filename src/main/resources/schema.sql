DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE SEQUENCE roles_seq START WITH 1;

CREATE TABLE users
(
    login       VARCHAR NOT NULL,
    name        VARCHAR NOT NULL,
    password    VARCHAR NOT NULL,

    PRIMARY KEY (login)
);

CREATE TABLE roles
(
    id      INT     DEFAULT nextval('roles_seq'),
    name    VARCHAR NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE users_roles (
    user_login  VARCHAR NOT NULL,
    role_id     INT     NOT NULL,

    PRIMARY KEY (user_login, role_id),
    FOREIGN KEY (user_login) REFERENCES users (login),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);