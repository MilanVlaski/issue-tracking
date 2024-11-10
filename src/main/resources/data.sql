insert into APLIKACIJA (NAZIV_APP, VERZIJA, OPIS, GODINA_IZDAVANJA, LOGO_URL) values
('Dev Dreams', '1.2', 'Develop web apps faster!', '2020-01-01', 'https://st3.depositphotos.com/43745012/44906/i/450/depositphotos_449066958-stock-photo-financial-accounting-logo-financial-logo.jpg'),
('Code Sprint', '2.0', 'Speed up your coding projects!', '2021-05-15', 'https://png.pngtree.com/png-vector/20220509/ourmid/pngtree-company-logo-design-trademark-design-creative-logo-png-image_4569380.png'),
('Design Studio', '3.5', 'Create stunning designs easily!', '2019-08-23', 'https://st5.depositphotos.com/1029305/68399/i/380/depositphotos_683998616-stock-photo-gold-abstract-hexagon-shaped-optical.jpg'),
('TaskMaster', '4.1', 'Manage your tasks efficiently!', '2022-03-10', 'https://static8.depositphotos.com/1378583/1010/i/380/depositphotos_10106840-stock-photo-face-logo.jpg'),
('Insight Pro', '1.8', 'Get deep insights from your data!', '2020-11-05', 'https://www.shutterstock.com/image-vector/circle-line-simple-design-logo-600nw-2174926871.jpg'),
('Market Pulse', '2.9', 'Track market trends in real-time!', '2018-07-30', 'https://www.logodesign.net/logo/line-art-buildings-in-swoosh-1273ld.png?size=2');

insert into VRSTA_PODRSKE (ID_POD, NAZIV_POD, OPIS_POD, CIJENA) values
('BEZ_POD', 'Bez podrške', 'Bez ikakve usluge.', 0.00),
('POLA_RAD', 'Ograničena podrška pola radnog vremena', 'Podrška za dogovoreno vrijeme koje ne premašuje pola radnog vremena kupca.', 1000.00),
('CIJELO_RAD', 'Ograničena podrška cijelo radno vrijeme', 'Podrška tokom cijelog radnog vremena kupca.', 2000.00),
('NEOGRAN', 'Neograničena podrška', 'Podrška 24/7.', 70000.00);

insert into KORISNIK (NAZIV_KOR, GODINA_RODJENJA, MEJL_ADRESA_KOR, SIFRA_KOR, BROJ_TELEFONA, LOKACIJA)
VALUES ('sa', '1990-01-01', 'john.doe@example.com', '$2a$10$36JHQEwvmg.veIk08XAW1eCOWDKqv4WeJka32v/Q7gwVOJ.wjw51.', '123456', 'London');

insert into KUPOVINA (ID_APP, ID_KOR, ID_POD) values
(5, 1, 'BEZ_POD')
