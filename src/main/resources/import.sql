
insert into Country (id, name) values (1, 'Argentina');
insert into country (id, name) values (2, 'Gran Bretaña');
insert into prov (id, name, country_id ) values (1, 'Buenos Aires', 1);
insert into prov (id, name, country_id ) values (2, 'Capital Federal', 1);
insert into prov (id, name, country_id ) values (3, 'Salta', 1);
insert into prov (id, name, country_id ) values (4, 'Chaco', 1);
insert into prov (id, name, country_id ) values (5, 'Formosa', 1);
insert into prov (id, name, country_id ) values (6, 'Santiago del Estero', 1);
insert into prov (id, name, country_id ) values (7, 'Misiones', 1);
insert into prov (id, name, country_id ) values (8, 'Corrientes', 1);
insert into prov (id, name, country_id ) values (9, 'Entre Rios', 1);
insert into prov (id, name, country_id ) values (10, 'Catamarca', 1);
insert into prov (id, name, country_id ) values (11, 'Jujuy', 1);
insert into prov (id, name, country_id ) values (12, 'San Juan', 1);
insert into prov (id, name, country_id ) values (13, 'San Luis', 1);
insert into prov (id, name, country_id ) values (14, 'Santa Fe', 1);
insert into prov (id, name, country_id ) values (15, 'Cordoba', 1);
insert into prov (id, name, country_id ) values (16, 'Tucuman', 1);
insert into prov (id, name, country_id ) values (17, 'La Rioja', 1);
insert into prov (id, name, country_id ) values (18, 'La Pampa', 1);
insert into prov (id, name, country_id ) values (19, 'Mendoza', 1);
insert into prov (id, name, country_id ) values (20, 'Neuquen', 1);
insert into prov (id, name, country_id ) values (21, 'Rio Negro', 1);
insert into prov (id, name, country_id ) values (22, 'Santa Cruz', 1);
insert into prov (id, name, country_id ) values (23, 'Tierra del Fuego, Antártida e Islas del Atlántico Sur', 1);
insert into prov (id, name, country_id ) values (24, 'Chubut', 1);

insert into city (id, cp, name, prov_id ) values (1, '1234', 'Campana', 1);



insert into Rol (name) values ('admin') , ('super');
insert into entitytype (name) values ('book'), ('copy'), ('affiliate'), ('user'), ('genre'), ('location'), ('editorial'), ('author');
insert into lendtype (name) values ('local'), ('foreign');


insert into user (id, deleted , password , number, street, createdate, birth, cel, email, tel, cuil, dni, lastname, name, city_id, rol_id, type_id ) values (1, false, 'hola', 123, 'Avenida Siempre Viva', '2013-10-10', '1988-10-10', 1234567, 'clau@guzman.com', 1234567, 12345567, 12345678, 'Guzman', 'Claudia', 1, 1, 4);
insert into user (id, deleted , password , number, street, createdate, birth, cel, email, tel, cuil, dni, lastname, name, city_id, rol_id, type_id ) values (2, false, 'hola', 123, 'Avenida Siempre Muerta', '2013-10-10', '1988-10-10', 1234567, 'boby@jackson.com', 1234567, 12345567, 12345678, 'Jackson', 'Boby', 1, 2, 4);


insert into affiliate (id, deleted, street, number, createdate, createuser_id, birth, cel, tel, email, cuil, dni, name, lastname, reputation, city_id, type_id ) values (1, false, 'Avenida Del Juego de la Oca', 123, '2013-10-10', 1, '1990-10-10', 1234567,1234567, 'jesus@cielo.com', 1234566,1234567, 'Jesus', 'DeLaFerrere', 10, 1, 3);
insert into affiliate (id, deleted, street, number, createdate, createuser_id, birth, cel, tel, email, cuil, dni, name, lastname, reputation, city_id, type_id ) values (2, false, 'El Pato', 123, '2013-10-10', 1, '1990-10-10', 1234567,1234567, 'dexter@blood.com', 1234566,1234567, 'Dexter', 'Morgan', 10, 1, 3);
-- Add a deleted affiliate for testing purpose
insert into affiliate (id, deleted, street, number, createdate, createuser_id, birth, cel, tel, email, cuil, dni, name, lastname, reputation, city_id, type_id ) values (3, true, 'Borrado', 123, '2013-10-10', 1, '1990-10-10', 1234567,1234567, 'deleted@borrado.com', 1234566,1234567, 'Deleted', 'Borrado', 10, 1, 3);


insert into location (id, deleted, shelf, shelves, type_id ) values (1, false, '2', 'A', 6);

insert into genre (id, deleted, name, type_id ) values (1, false, 'fantasía', 5);

insert into author (id, nick, createdate, createuser_id, deleted, country_id, type_id, birth) values (1, 'Jk Rowling', '2013-10-10', 1, false, 2, 8, '1956-10-10');
insert into editorial (id, legal_name, name, createdate, createuser_id, street, number, tel, email, city_id, type_id, deleted) values (1, 'Salamandra SA', 'Salamandra', '2013-10-10', 1, 'Cordoba', 123, 1234567, 'tu@salamandra.com.ar', 1, 7, false);
insert into book (id, createdate, createuser_id, deleted, title, val, price, lang, isbn, editioncountry_id, type_id, editorial_id) values (1, '2013-10-10', 1, false, 'Harry Potter y La Piedra Filosofal', 7, '10.2', 'Español', 1234556678, 1, 1, 1);
insert into book (id, createdate, createuser_id, deleted, title, val, price, lang, isbn, editioncountry_id, type_id, editorial_id) values (2, '2013-10-10', 1, true, 'Libro Borrado', 7, '10.2', 'Español', 14556678, 1, 1, 1);
insert into book_genre (book_id, genres_id) values (1,1);
insert into book_author (book_id, authors_id) values (1,1);

insert into COPY (id, createdate, createuser_id, deleted, editionYear, state, location_id, type_id, book_id ) values (1, '2013-10-10', 1, false, 2010, 'nuevo', 1, 2, 1);
insert into COPY (id, createdate, createuser_id, deleted, editionYear, state, location_id, type_id, book_id ) values (2, '2013-10-10', 1, false, 2010, 'bueno, un poco desgastado', 1, 2, 1);
insert into copy_lendtype (copy_id, lendtypes_id ) values (1, 1);
insert into copy_lendtype (copy_id, lendtypes_id ) values (1, 2);
insert into copy_lendtype (copy_id, lendtypes_id ) values (2, 1);



insert into LEND (id, actualreturndate, expectedreturndate, lenddate, affiliate_id, copy_id, lendtype_id, lendinguser_id, returninguser_id ) values (1, '2013-10-15', '2013-10-15', '2013-10-10', 1, 1, 2, 1, 1);

insert into hollyday (id, date, comments ) values (1, '2013-12-10', 'Dia de los hombres con acento judio que no son judios');




