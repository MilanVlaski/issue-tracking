drop table if exists AKCIJA cascade;
drop table if exists APLIKACIJA cascade;
drop table if exists INZENJER cascade;
drop table if exists KORISNIK cascade;
drop table if exists KUPOVINA cascade;
drop table if exists ODGOVOR cascade;
drop table if exists PROBLEM cascade;
drop table if exists RJESAVALAC cascade;
drop table if exists VRSTA_PODRSKE cascade;
drop table if exists ZAKRPA cascade;

create table AKCIJA (
                        BR_AKC INT not null,
                        ID_PRB INT not null,
                        OPIS_AKC VARCHAR(200) null,
                        constraint PK_AKCIJA primary key (BR_AKC)
);

create table APLIKACIJA (
                            ID_APP SERIAL not null,
                            NAZIV_APP VARCHAR(50) not null,
                            VERZIJA VARCHAR(20) not null,
                            OPIS VARCHAR(100) null,
                            GODINA_IZDAVANJA DATE null,
                            LOGO_URL VARCHAR(500) null,
                            constraint PK_APLIKACIJA primary key (ID_APP)
);

create table INZENJER (
                          ID_INZ SERIAL not null,
                          NAZIV_INZ VARCHAR(50) null,
                          OBRAZOVANJE VARCHAR(50) null,
                          ZAPOSLEN_OD DATE null,
                          ZAPOSLEN_DO DATE null,
                          MJESECNA_PLATA NUMERIC(10,2) null,
                          MEJL_ADRESA_INZ VARCHAR(50) null,
                          SIFRA_INZ VARCHAR(60) null,
                          constraint PK_INZENJER primary key (ID_INZ)
);

create table KORISNIK (
                          ID_KOR SERIAL not null,
                          NAZIV_KOR VARCHAR(50) not null,
                          GODINA_RODJENJA DATE null,
                          MEJL_ADRESA_KOR VARCHAR(50) null,
                          SIFRA_KOR VARCHAR(60) null,
                          BROJ_TELEFONA VARCHAR(20) null,
                          LOKACIJA VARCHAR(50) null,
                          constraint PK_KORISNIK primary key (ID_KOR)
);

create table KUPOVINA (
                          ID_APP INT not null,
                          ID_KOR INT not null,
                          ID_POD VARCHAR(10) not null,
                          constraint PK_KUPOVINA primary key (ID_APP, ID_KOR)
);

create table ODGOVOR (
                         ID_ODG SERIAL not null,
                         ID_INZ INT not null,
                         ID_PRB INT not null,
                         OPIS_ODG VARCHAR(500) null,
                         constraint PK_ODGOVOR primary key (ID_ODG)
);

create table PROBLEM (
                         ID_PRB SERIAL not null,
                         ID_KOR INT not null,
                         ID_APP INT not null,
                         STANJE VARCHAR(20) not null
                             constraint CKC_STANJE_PROBLEM check (STANJE in ('Prijavljen', 'Preuzet', 'Rješava se', 'Riješen')),
                         OPIS_PRB VARCHAR(200) null,
                         constraint PK_PROBLEM primary key (ID_PRB)
);

create table RJESAVALAC (
                            ID_PRB INT not null,
                            ID_INZ INT not null,
                            constraint PK_RJESAVALAC primary key (ID_PRB, ID_INZ)
);

create table VRSTA_PODRSKE (
                               ID_POD VARCHAR(10) not null,
                               NAZIV_POD VARCHAR(50) not null,
                               OPIS_POD VARCHAR(100) null,
                               CIJENA NUMERIC(10,2) null,
                               constraint PK_VRSTA_PODRSKE primary key (ID_POD)
);

create table ZAKRPA (
                        ID_KRP SERIAL not null,
                        ID_PRB INT not null,
                        ID_INZ_RJESAVAOCA INT not null,
                        ID_KOR INT not null,
                        ID_INZ_KRPIOCA INT null,
                        VELICINA_KB NUMERIC(10,0) null,
                        DATUM_OBJAVE DATE null,
                        VRSTA_KOMUNIKACIJE VARCHAR(200) null,
                        constraint PK_ZAKRPA primary key (ID_KRP)
);

alter table AKCIJA
    add constraint FK_AKCIJA_UZROKUJU_PROBLEM foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINA_KUPUJE_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINA_PODRSKA_VRSTA_PO foreign key (ID_POD)
        references VRSTA_PODRSKE (ID_POD)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINA_SE_KUPUJE_APLIKACI foreign key (ID_APP)
        references APLIKACIJA (ID_APP)
        on delete restrict on update restrict;

alter table ODGOVOR
    add constraint FK_ODGOVOR_DAJE_INZENJER foreign key (ID_INZ)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table ODGOVOR
    add constraint FK_ODGOVOR_NA_PROBLE_PROBLEM foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table PROBLEM
    add constraint FK_PROBLEM_PRIJAVLJU_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table PROBLEM
    add constraint FK_PROBLEM_RELATIONS_APLIKACI foreign key (ID_APP)
        references APLIKACIJA (ID_APP)
        on delete restrict on update restrict;

alter table RJESAVALAC
    add constraint FK_RJESAVAL_RELATIONS_INZENJER foreign key (ID_INZ)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table RJESAVALAC
    add constraint FK_RJESAVAL_RELATIONS_PROBLEM foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPA_INSTALIRA_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPA_PRAVI_INZENJER foreign key (ID_INZ_KRPIOCA)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPA_RELATIONS_RJESAVAL foreign key (ID_PRB, ID_INZ_RJESAVAOCA)
        references RJESAVALAC (ID_PRB, ID_INZ)
        on delete restrict on update restrict;
