
insert into Country (id, name) values (1, 'Argentina');
insert into country (id, name) values (2, 'Gran Bretaña');


insert into prov (id, name, country_id ) values (1, 'Buenos Aires', 1);


insert into city (id, cp, name, prov_id ) values (1, '1234', 'Campana', 1);

insert into Rol (name) values ('admin') , ('super');

insert into entitytype (type) values ('book'), ('copy'), ('affiliate'), ('user'), ('genre'), ('location'), ('editorial'), ('author');

insert into lendtype (type) values ('local'), ('foreign');

insert into PERSON (id, name, lastname,  street, number, birth, cel, email, deleted, dni, city_id,  createdate) values (1, 'Claudia', 'Guzman', 'La Pampa', 90210, '1900-01-01', 1234566, 'clau@guzman.com', false, 1234567, 1, '2013-10-10');
insert into PERSON (id, name, lastname,  street, number, birth, cel, email, deleted, dni, city_id,  createdate) values (2, 'Boby', 'Jackson', 'Avenida Siempre Viva', 90210, '1900-01-01', 1234566231, 'boby@jackson.com', false, 122334567, 1, '2013-10-10');
insert into PERSON (id, name, lastname,  street, number, birth, cel, email, deleted, dni, city_id,  createdate) values (3, 'Jesus', 'DeLaFerrere', 'Libertador', 90210, '1900-01-01', 1234233341, 'jesus@paseobulrich.com', false, 122331237, 1, '2013-10-10');

insert into user (id, password, person_id, rol_id, type_id, deleted) values (1, 'hola', 1, 1, 4, false);
insert into user (id, password, person_id, rol_id, type_id, deleted) values (2, 'hola', 2, 2, 4, false);

insert into affiliate (id, person_id, type_id, reputation, deleted ) values (1, 3, 3, 10, false);

update person set createuser_id=1 where id=3;

insert into location (id, deleted, shelf, shelves, type_id ) values (1, false, '2', 'A', 6);

insert into genre (id, deleted, name, type_id ) values (1, false, 'fantasia', 5);

insert into author (id, nick, createdate, createuser_id, deleted, country_id, type_id, birth) values (1, 'Jk Rowling', '2013-10-10', 1, false, 2, 8, '1956-10-10');

insert into editorial (id, legal_name, name, createdate, createuser_id, street, number, tel, email, city_id, type_id, deleted) values (1, 'Salamandra SA', 'Salamandra', '2013-10-10', 1, 'Cordoba', 123, 1234567, 'tu@salamandra.com.ar', 1, 7, false);

insert into book (id, createdate, createuser_id, deleted, title, val, price, lang, isbn, editioncountry_id, type_id, editorial_id) values (1, '2013-10-10', 1, false, 'Harry Potter y La Piedra Filosofal', 7, '10.2', 'Español', 1234556678, 1, 1, 1);

insert into book_genre (book_id, genres_id) values (1,1);

insert into book_author (book_id, authors_id) values (1,1);

insert into author_book  (author_id, books_id) values (1,1);

insert into hollyday (id, date, comments ) values (1, '2013-12-10', 'Dia de los hombres con acento judio que no son judios');


insert into COPY (id, createdate, createuser_id, deleted, editionYear, state, book_id, location_id, type_id ) values (1, '2013-10-10', 1, false, 2010, 'nuevo', 1, 1, 2);

insert into COPY (id, createdate, createuser_id, deleted, editionYear, state, book_id, location_id, type_id ) values (2, '2013-10-10', 1, false, 2010, 'bueno, un poco desgastado', 1, 1, 2);

insert into copy_lendtype (copy_id, lendtypes_id ) values (1, 1);
insert into copy_lendtype (copy_id, lendtypes_id ) values (1, 2);
insert into copy_lendtype (copy_id, lendtypes_id ) values (2, 1);

insert into LEND (id, actualreturndate, expectedreturndate, lenddate, affiliate_id, copy_id, lendtype_id, lendinguser_id, returninguser_id ) values (1, '2013-10-15', '2013-10-15', '2013-10-10', 1, 1, 2, 1, 1);

insert into affiliate_lend ( affiliate_id, lends_id ) values (1, 1);







