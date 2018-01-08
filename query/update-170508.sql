drop table c_security_persistent_token ;
drop table c_security_user_authority;
drop table c_security_authority;
drop table c_security_user;

create table c_security_user(
    id bigserial primary key, 
    login varchar(50) unique not null,
    password_hash varchar(60),
    first_name varchar(50), 
    last_name varchar(50),
    email varchar(100) unique,
    image_url varchar(256), 
    activated boolean not null default false ,
    lang_key varchar(5),
    activation_key varchar(20),
    reset_key varchar(20),
    created_by varchar(50) not null,
    created_date timestamp not null default  now(),
    reset_date timestamp,
    last_modified_by varchar(50),
    last_modified_date timestamp
);

CREATE UNIQUE INDEX idx_user_login ON c_security_user USING btree  (login COLLATE pg_catalog."default");
CREATE UNIQUE INDEX idx_user_email ON c_security_user USING btree  (email COLLATE pg_catalog."default");

create table c_security_authority(name varchar(50) primary key);
create table c_security_user_authority(
    user_id bigint not null references c_security_user(id),
    authority_name varchar(50) not null references c_security_authority(name),
    primary key(user_id, authority_name)
);

CREATE TABLE c_security_persistent_token
(
  series character varying(255) NOT NULL,
  ip_address character varying(39),
  token_date timestamp,
  token_value character varying(255) NOT NULL,
  user_agent character varying(255),
  user_id bigint,
  CONSTRAINT c_security_persistent_token_pkey PRIMARY KEY (series),
  CONSTRAINT fk_nmplmkyaby1qi60wook34fuk1 FOREIGN KEY (user_id)
      REFERENCES public.c_security_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into c_user(
    id, login, password_hash, first_name, last_name, email, activated, lang_key, 
    created_by, last_modified_by, created_date) VALUES
(1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','true','en','system','system', now()),
(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','true','en','system','system', now()),
(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','true','en','system','system', now()),
(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','true','en','system','system', now());

insert into c_authority(name) VALUES
('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_EKSEKUTIF'), ('ROLE_EMKL');

insert into c_user_authority(user_id, authority_name) VALUES
(1,'ROLE_ADMIN'),
(1,'ROLE_USER'),
(3,'ROLE_ADMIN'),
(3,'ROLE_USER'),
(4,'ROLE_USER');