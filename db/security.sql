/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ustadho
 * Created: Mar 14, 2017
 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ustadho
 * Created: Feb 15, 2017
 */



create table c_Security_authority(name varchar (50) primary key);
insert into c_Security_authority(name) VALUES
('ROLE_ADMIN'),('ROLE_USER');

DROP table c_security_user_password;
DROP table c_security_user;
create table c_security_user(
    id bigint primary key, 
    login varchar(50) unique not null, 
    password_hash varchar(60) ,
    first_name varchar(50) ,
    last_name varchar(50) ,
    email varchar(100) unique,
    activated boolean default false, 
    lang_key varchar(5),
    activation_key varchar(20),
    reset_key varchar(20),
    created_by varchar(50),
    created_date timestamp, 
    last_modified_by varchar(50), 
    last_modified_date timestamp default now()
);

insert into c_security_user(
    id, login, password_hash, first_name, last_name, email, activated, lang_key, 
    created_by, last_modified_by) VALUES
(1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','true','en','system','system'),
(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','true','en','system','system'),
(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','true','en','system','system'),
(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','true','en','system','system');

create table c_security_user_authority(
    user_id bigint, 
    authority_name varchar(50), 
    primary key(user_id, authority_name)
);

insert into c_security_user_authority(user_id, authority_name) VALUES
(1,'ROLE_ADMIN'),
(1,'ROLE_USER'),
(3,'ROLE_ADMIN'),
(3,'ROLE_USER'),
(4,'ROLE_USER');

DROP TABLE c_security_persistent_token;
CREATE TABLE c_security_persistent_token
(
  series character varying(255) primary key,
  user_id bigint references c_security_user(id),
  token_value character varying(255) NOT NULL,
  token_date date,
  ip_address character varying(39),
  user_agent character varying(255)
);

alter table t_surat_jalan  add column created_by varchar(50) not null default '';
alter table t_surat_jalan  add column created_date timestamp without time zone;