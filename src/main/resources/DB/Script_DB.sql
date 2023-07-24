CREATE SEQUENCE ticket_id_seq;

CREATE TABLE public."ticket"
(
    id serial PRIMARY KEY,
    usuario character varying(50) not null UNIQUE,
    fecha_creacion timestamp without time zone not null,
    fecha_actualizacion timestamp without time zone not null,
    estatus character varying(10) not null
);

ALTER TABLE IF EXISTS public."ticket"
    OWNER to julianfas20;
    
INSERT INTO ticket VALUES (1,'Julian','2023-07-19 12:34:56','2023-07-22 12:34:56','abierto');
INSERT INTO ticket VALUES (2,'Felipe','2023-06-07 11:34:56','2023-06-17 11:34:56','cerrado');
INSERT INTO ticket VALUES (3,'Andrea17','2023-01-08 09:30:50','2023-02-08 09:30:50','cerrado');
INSERT INTO ticket VALUES (4,'mora23','2022-10-12 09:30:50','2023-05-12 09:30:50','abierto');
INSERT INTO ticket VALUES (5,'1santo2','2022-12-12 12:12:50','2023-02-12 11:30:50','abierto');

ALTER SEQUENCE ticket_id_seq RESTART WITH 6;