CREATE DATABASE slc_db;

-- Roles table

CREATE TABLE roles
(
  role text NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (role)
)
WITH (
  OIDS=FALSE
);

INSERT INTO roles(
            role)
    VALUES ('ADMIN', 'ATHLETE');


-- Users Table

CREATE TABLE users
(
  id serial NOT NULL,
  email text NOT NULL,
  firstname text NOT NULL,
  lastname text NOT NULL,
  role text NOT NULL DEFAULT 'ATHLETE'::text,
  coach_id integer,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT fk_users_roles_idx FOREIGN KEY (role)
      REFERENCES roles (role) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);


-- Parameters Table

CREATE TABLE parameters
(
  name text NOT NULL,
  value text,
  CONSTRAINT parameters_pkey PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);