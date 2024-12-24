insert into APLIKACIJA (NAZIV_APP, VERZIJA, OPIS, GODINA_IZDAVANJA, LOGO_URL) values
('Dev Dreams', '1.2.0', 'Develop web apps faster!', '2020-01-01', 'https://st3.depositphotos.com/43745012/44906/i/450/depositphotos_449066958-stock-photo-financial-accounting-logo-financial-logo.jpg'),
('Code Sprint', '2.0.1', 'Speed up your coding projects!', '2021-05-15', 'https://png.pngtree.com/png-vector/20220509/ourmid/pngtree-company-logo-design-trademark-design-creative-logo-png-image_4569380.png'),
('Design Studio', '3.5.0', 'Create stunning designs easily!', '2019-08-23', 'https://st5.depositphotos.com/1029305/68399/i/380/depositphotos_683998616-stock-photo-gold-abstract-hexagon-shaped-optical.jpg'),
('TaskMaster', '4.1.14', 'Manage your tasks efficiently!', '2022-03-10', 'https://static8.depositphotos.com/1378583/1010/i/380/depositphotos_10106840-stock-photo-face-logo.jpg'),
('Insight Pro', '1.8.0', 'Get deep insights from your data!', '2020-11-05', 'https://www.shutterstock.com/image-vector/circle-line-simple-design-logo-600nw-2174926871.jpg'),
('Market Pulse', '2.9.0', 'Track market trends in real-time!', '2018-07-30', 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/company-logo-design-template-e089327a5c476ce5c70c74f7359c5898_screen.jpg?ts=1672291305');

insert into VRSTA_PODRSKE (ID_POD, NAZIV_POD, OPIS_POD, CIJENA) values
('NO_SUPP', 'No Support', 'No support service.', 0.00),
('HALF_WORK', 'Half Day Support', 'Support for agreed upon time not exceeding half of the customer working hours.', 1000.00),
('FULL_WORK', 'Full Day Support', 'Support throughout the customer working hours.', 2000.00),
('UNLIMITED', '24/7 Support', '24/7 support.', 70000.00);

insert into KORISNIK (NAZIV_KOR, GODINA_RODJENJA, MEJL_ADRESA_KOR, SIFRA_KOR, BROJ_TELEFONA, LOKACIJA)
VALUES ('sa', '1990-01-01', 'john.doe@example.com', '$2a$10$36JHQEwvmg.veIk08XAW1eCOWDKqv4WeJka32v/Q7gwVOJ.wjw51.', '123456', 'London');

insert into KUPOVINA (ID_APP, ID_KOR, ID_POD) values
(5, 1, 'NO_SUPP');

insert into INZENJER (NAZIV_INZ, OBRAZOVANJE, ZAPOSLEN_OD, ZAPOSLEN_DO, MJESECNA_PLATA, MEJL_ADRESA_INZ, SIFRA_INZ) values
('John Smith', 'Master of Engineering', '2022-01-01', null, 5000.00, 'john.smith@example.com', '$2a$10$36JHQEwvmg.veIk08XAW1eCOWDKqv4WeJka32v/Q7gwVOJ.wjw51.');
