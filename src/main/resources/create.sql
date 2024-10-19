/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     10/17/2024 5:47:48 PM                        */
/*==============================================================*/


drop index UZROKUJU_FK;

drop index AKCIJA_PK;

drop table AKCIJA;

drop index APLIKACIJA_PK;

drop table APLIKACIJA;

drop index INZENJER_PK;

drop table INZENJER;

drop index KORISNIK_PK;

drop table KORISNIK;

drop index PODRSKA_FK;

drop index SE_KUPUJE_FK;

drop index KUPUJE_FK;

drop index KUPOVINA_PK;

drop table KUPOVINA;

drop index NA_PROBLEM_FK;

drop index DAJE_FK;

drop index ODGOVOR_PK;

drop table ODGOVOR;

drop index RELATIONSHIP_17_FK;

drop index PRIJAVLJUJE_FK;

drop index PROBLEM_PK;

drop table PROBLEM;

drop index RELATIONSHIP_20_FK;

drop index RELATIONSHIP_19_FK;

drop index RJESAVALAC_PK;

drop table RJESAVALAC;

drop index VRSTA_PODRSKE_PK;

drop table VRSTA_PODRSKE;

drop index INSTALIRA_FK;

drop index PRAVI_FK;

drop index NA_PROBLEMU_FK;

drop index RELATIONSHIP_18_FK;

drop index ZAKRPA_PK;

drop table ZAKRPA;

/*==============================================================*/
/* Table: AKCIJA                                                */
/*==============================================================*/
create table AKCIJA (
                        BR_AKC               INT4                 not null,
                        ID_PRB               INT4                 not null,
                        OPIS_AKC             VARCHAR(200)         null,
                        constraint PK_AKCIJA primary key (BR_AKC)
);

/*==============================================================*/
/* Index: AKCIJA_PK                                             */
/*==============================================================*/
create unique index AKCIJA_PK on AKCIJA (
                                         BR_AKC
    );

/*==============================================================*/
/* Index: UZROKUJU_FK                                           */
/*==============================================================*/
create  index UZROKUJU_FK on AKCIJA (
                                     ID_PRB
    );

/*==============================================================*/
/* Table: APLIKACIJA                                            */
/*==============================================================*/
create table APLIKACIJA (
                            ID_APP               INT4                 not null,
                            NAZIV_APP            VARCHAR(50)          not null,
                            VERZIJA              VARCHAR(20)          not null,
                            OPIS                 VARCHAR(100)         null,
                            GODINA_IZDAVANJA     DATE                 null,
                            constraint PK_APLIKACIJA primary key (ID_APP)
);

/*==============================================================*/
/* Index: APLIKACIJA_PK                                         */
/*==============================================================*/
create unique index APLIKACIJA_PK on APLIKACIJA (
                                                 ID_APP
    );

/*==============================================================*/
/* Table: INZENJER                                              */
/*==============================================================*/
create table INZENJER (
                          ID_INZ               INT4                 not null,
                          NAZIV_INZ            VARCHAR(50)          null,
                          OBRAZOVANJE          VARCHAR(50)          null,
                          ZAPOSLEN_OD          DATE                 null,
                          ZAPOSLEN_DO          DATE                 null,
                          MJESECNA_PLATA       NUMERIC(10,2)        null,
                          MEJL_ADRESA_INZ      VARCHAR(50)          null,
                          SIFRA_INZ            VARCHAR(30)          null,
                          constraint PK_INZENJER primary key (ID_INZ)
);

/*==============================================================*/
/* Index: INZENJER_PK                                           */
/*==============================================================*/
create unique index INZENJER_PK on INZENJER (
                                             ID_INZ
    );

/*==============================================================*/
/* Table: KORISNIK                                              */
/*==============================================================*/
create table KORISNIK (
                          ID_KOR               INT4                 not null,
                          NAZIV_KOR            VARCHAR(50)          not null,
                          GODINA_RODJENJA      DATE                 null,
                          MEJL_ADRESA_KOR      VARCHAR(50)          null,
                          BROJ_TELEFONA        VARCHAR(20)          null,
                          LOKACIIJA            VARCHAR(50)          null,
                          constraint PK_KORISNIK primary key (ID_KOR)
);

/*==============================================================*/
/* Index: KORISNIK_PK                                           */
/*==============================================================*/
create unique index KORISNIK_PK on KORISNIK (
                                             ID_KOR
    );

/*==============================================================*/
/* Table: KUPOVINA                                              */
/*==============================================================*/
create table KUPOVINA (
                          ID_APP               INT4                 not null,
                          ID_KOR               INT4                 not null,
                          ID_POD               VARCHAR(10)          not null,
                          constraint PK_KUPOVINA primary key (ID_APP, ID_KOR)
);

/*==============================================================*/
/* Index: KUPOVINA_PK                                           */
/*==============================================================*/
create unique index KUPOVINA_PK on KUPOVINA (
                                             ID_APP,
                                             ID_KOR
    );

/*==============================================================*/
/* Index: KUPUJE_FK                                             */
/*==============================================================*/
create  index KUPUJE_FK on KUPOVINA (
                                     ID_KOR
    );

/*==============================================================*/
/* Index: SE_KUPUJE_FK                                          */
/*==============================================================*/
create  index SE_KUPUJE_FK on KUPOVINA (
                                        ID_APP
    );

/*==============================================================*/
/* Index: PODRSKA_FK                                            */
/*==============================================================*/
create  index PODRSKA_FK on KUPOVINA (
                                      ID_POD
    );

/*==============================================================*/
/* Table: ODGOVOR                                               */
/*==============================================================*/
create table ODGOVOR (
                         ID_ODG               INT4                 not null,
                         ID_INZ               INT4                 not null,
                         ID_PRB               INT4                 not null,
                         OPIS_ODG             VARCHAR(50)          null,
                         constraint PK_ODGOVOR primary key (ID_ODG)
);

/*==============================================================*/
/* Index: ODGOVOR_PK                                            */
/*==============================================================*/
create unique index ODGOVOR_PK on ODGOVOR (
                                           ID_ODG
    );

/*==============================================================*/
/* Index: DAJE_FK                                               */
/*==============================================================*/
create  index DAJE_FK on ODGOVOR (
                                  ID_INZ
    );

/*==============================================================*/
/* Index: NA_PROBLEM_FK                                         */
/*==============================================================*/
create  index NA_PROBLEM_FK on ODGOVOR (
                                        ID_PRB
    );

/*==============================================================*/
/* Table: PROBLEM                                               */
/*==============================================================*/
create table PROBLEM (
                         ID_PRB               INT4                 not null,
                         ID_KOR               INT4                 not null,
                         ID_APP               INT4                 not null,
                         STANJE               VARCHAR(20)          not null
                             constraint CKC_STANJE_PROBLEM check (STANJE in ('Prijavljen','Preuzet','Rješava se','Riješen')),
                         OPIS_PRB             VARCHAR(200)         null,
                         constraint PK_PROBLEM primary key (ID_PRB)
);

/*==============================================================*/
/* Index: PROBLEM_PK                                            */
/*==============================================================*/
create unique index PROBLEM_PK on PROBLEM (
                                           ID_PRB
    );

/*==============================================================*/
/* Index: PRIJAVLJUJE_FK                                        */
/*==============================================================*/
create  index PRIJAVLJUJE_FK on PROBLEM (
                                         ID_KOR
    );

/*==============================================================*/
/* Index: RELATIONSHIP_17_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_17_FK on PROBLEM (
                                             ID_APP
    );

/*==============================================================*/
/* Table: RJESAVALAC                                            */
/*==============================================================*/
create table RJESAVALAC (
                            ID_PRB               INT4                 not null,
                            ID_INZ               INT4                 not null,
                            constraint PK_RJESAVALAC primary key (ID_PRB, ID_INZ)
);

/*==============================================================*/
/* Index: RJESAVALAC_PK                                         */
/*==============================================================*/
create unique index RJESAVALAC_PK on RJESAVALAC (
                                                 ID_PRB,
                                                 ID_INZ
    );

/*==============================================================*/
/* Index: RELATIONSHIP_19_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_19_FK on RJESAVALAC (
                                                ID_INZ
    );

/*==============================================================*/
/* Index: RELATIONSHIP_20_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_20_FK on RJESAVALAC (
                                                ID_PRB
    );

/*==============================================================*/
/* Table: VRSTA_PODRSKE                                         */
/*==============================================================*/
create table VRSTA_PODRSKE (
                               ID_POD               VARCHAR(10)          not null,
                               NAZIV_POD            VARCHAR(50)          not null,
                               OPIS_POD             VARCHAR(50)          null,
                               CIJENA               NUMERIC(10,2)        null,
                               constraint PK_VRSTA_PODRSKE primary key (ID_POD)
);

/*==============================================================*/
/* Index: VRSTA_PODRSKE_PK                                      */
/*==============================================================*/
create unique index VRSTA_PODRSKE_PK on VRSTA_PODRSKE (
                                                       ID_POD
    );

/*==============================================================*/
/* Table: ZAKRPA                                                */
/*==============================================================*/
create table ZAKRPA (
                        ID_KRP               INT4                 not null,
                        ID_PRB               INT4                 not null,
                        ID_INZ               INT4                 not null,
                        ID_KOR               INT4                 not null,
                        VELICINA_KB          NUMERIC(10,0)        null,
                        DATUM_OBJAVE         DATE                 null,
                        VRSTA_KOMUNIKACIJE   VARCHAR(200)         null,
                        constraint PK_ZAKRPA primary key (ID_KRP)
);

/*==============================================================*/
/* Index: ZAKRPA_PK                                             */
/*==============================================================*/
create unique index ZAKRPA_PK on ZAKRPA (
                                         ID_KRP
    );

/*==============================================================*/
/* Index: RELATIONSHIP_18_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_18_FK on ZAKRPA (
                                            ID_PRB,
                                            ID_INZ
    );

/*==============================================================*/
/* Index: NA_PROBLEMU_FK                                        */
/*==============================================================*/
create  index NA_PROBLEMU_FK on ZAKRPA (
                                        ID_PRB
    );

/*==============================================================*/
/* Index: PRAVI_FK                                              */
/*==============================================================*/
create  index PRAVI_FK on ZAKRPA (
                                  ID_INZ
    );

/*==============================================================*/
/* Index: INSTALIRA_FK                                          */
/*==============================================================*/
create  index INSTALIRA_FK on ZAKRPA (
                                      ID_KOR
    );

alter table AKCIJA
    add constraint FK_AKCIJA_UZROKUJE_PROBLEM foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINU_KUPUJE_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINA_UZ_PODRSKU foreign key (ID_POD)
        references VRSTA_PODRSKE (ID_POD)
        on delete restrict on update restrict;

alter table KUPOVINA
    add constraint FK_KUPOVINA_APLIKACIJE foreign key (ID_APP)
        references APLIKACIJA (ID_APP)
        on delete restrict on update restrict;

alter table ODGOVOR
    add constraint FK_ODGOVOR_DAJE_INZENJER foreign key (ID_INZ)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table ODGOVOR
    add constraint FK_ODGOVOR_NA_PROBLEM foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table PROBLEM
    add constraint FK_PROBLEM_PRIJAVLJUJE_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table PROBLEM
    add constraint FK_PROBLEM_NA_APLIKACIJI foreign key (ID_APP)
        references APLIKACIJA (ID_APP)
        on delete restrict on update restrict;

alter table RJESAVALAC
    add constraint FK_RJESAVALAC_RADI_KAO_INZENJER foreign key (ID_INZ)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table RJESAVALAC
    add constraint FK_RJESAVALAC_PROBLEMA foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPU_INSTALIRA_KORISNIK foreign key (ID_KOR)
        references KORISNIK (ID_KOR)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPA_NA_PROBLEMU foreign key (ID_PRB)
        references PROBLEM (ID_PRB)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPU_PRAVI_INZENJER foreign key (ID_INZ)
        references INZENJER (ID_INZ)
        on delete restrict on update restrict;

alter table ZAKRPA
    add constraint FK_ZAKRPA_ODGOVORAN_RJESAVALAC foreign key (ID_PRB, ID_INZ)
        references RJESAVALAC (ID_PRB, ID_INZ)
        on delete restrict on update restrict;

