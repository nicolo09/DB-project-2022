create database Hospital;
use Hospital;

create table AMMINISTRATIVI (
     Codice_fiscale char(16) not null,
     Ruolo varchar(70) not null,
     Codice_ospedale char(10) not null,
     constraint FKPERS_AMMINISTRATIVI_ID primary key (Codice_fiscale));

create table APPUNTAMENTO (
     Codice_ospedale char(10) not null,
     Numero_sala int not null,
     Data_ora datetime not null,
     Durata int not null,
     Tipo varchar(20) not null,
     Paziente char(16) not null,
     constraint IDAPPUNTAMENTO_ID primary key (Codice_ospedale, Numero_sala, Data_ora));

create table ASL (
     Codice char(6) not null,
     Nome varchar(30) not null,
     Ind_Citta varchar(35) not null,
     Ind_Via varchar(25) not null,
     Ind_Numero_civico char(8) not null,
     constraint IDASL primary key (Codice));

create table ATTREZZATURA (
     Codice_ospedale char(10) not null,
     Codice_inventario int not null,
     Nome varchar(30) not null,
     Data_manutenzione date not null,
     constraint IDATTREZZATURA primary key (Codice_ospedale, Codice_inventario));

create table COINVOLGIMENTO (
     Referto char(20) not null,
     Medico char(16) not null,
     constraint IDCOINVOLGIMENTO primary key (Referto, Medico));

create table CURA (
     Paziente char(16) not null,
     Codice_ospedale char(10) not null,
     Nome_unita varchar(30) not null,
     Data_ingresso date not null,
     Data_uscita date,
     Motivazione varchar(400) not null,
     constraint IDCURA primary key (Paziente, Codice_ospedale, Nome_unita));

create table LAVORA (
     Codice_ospedale char(10) not null,
     Nome_unita varchar(30) not null,
     Codice_fiscale char(16) not null,
     constraint IDLAVORA primary key (Codice_fiscale, Codice_ospedale, Nome_unita));

create table OSPEDALE (
     Codice_struttura char(10) not null,
     Nome varchar(30) not null,
     Ind_Citta varchar(35) not null,
     Ind_Via varchar(25) not null,
     Ind_Numero_civico char(8) not null,
     Cod_ASL char(6) not null,
     constraint IDOSPEDALE primary key (Codice_struttura));

create table PAZIENTE (
     Codice_fiscale char(16) not null,
     Data_nascita date not null,
     Cod_ASL char(6),
     constraint FKPERS_PAZIENTE_ID primary key (Codice_fiscale));

create table PERSONA (
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Codice_fiscale char(16) not null,
     constraint IDPERSONA_ID primary key (Codice_fiscale));

create table PERSONALE_SANITARIO (
     Codice_fiscale char(16) not null,
     Ruolo varchar(30) not null,
     constraint FKPERS_SANITARI_ID primary key (Codice_fiscale));

create table PRESENZIA (
     Medico char(16) not null,
     Codice_ospedale char(10) not null,
     Numero_sala int not null,
     Data_ora datetime not null,
     constraint IDPRESENZIA primary key (Medico, Codice_ospedale, Numero_sala, Data_ora));

create table REFERTO (
     Codice_referto char(20) not null,
     Data_emissione date not null,
     Descrizione varchar(1000) not null,
     Tipo varchar(10) not null,
     Terapia varchar(500),
     Procedura varchar(500),
     Esito varchar(500),
     Durata int,
     Codice_ospedale char(10) not null,
     Paziente char(16) not null,
     constraint IDREFERTO_ID primary key (Codice_referto));

create table SALA (
     Codice_ospedale char(10) not null,
     Numero int not null,
     constraint IDSALA primary key (Codice_ospedale, Numero));

create table TELEFONO (
     Telefono char(15) not null,
     Persona char(16) not null,
     constraint IDTELEFONO primary key (Telefono, Persona));

create table UNITA_OPERATIVA (
     Codice_ospedale char(10) not null,
     Nome varchar(30) not null,
     Capienza int not null,
     Posti_occupati int not null,
     constraint IDUNITA_OPERATIVA primary key (Codice_ospedale, Nome));


-- Constraints Section
-- ___________________ 

alter table AMMINISTRATIVI add constraint FKPERS_AMMINISTRATIVI_FK
     foreign key (Codice_fiscale)
     references PERSONA (Codice_fiscale);

alter table AMMINISTRATIVI add constraint FKIMPIEGATI
     foreign key (Codice_ospedale)
     references OSPEDALE (Codice_struttura);

alter table APPUNTAMENTO add constraint FKSI_SVOLGE
     foreign key (Codice_ospedale, Numero_sala)
     references SALA (Codice_ospedale, Numero);

alter table APPUNTAMENTO add constraint FKPRENOTA
     foreign key (Paziente)
     references PAZIENTE (Codice_fiscale);

alter table ATTREZZATURA add constraint FKPOSSIEDE
     foreign key (Codice_ospedale)
     references OSPEDALE (Codice_struttura);

alter table COINVOLGIMENTO add constraint FKCOI_PER
     foreign key (Medico)
     references PERSONALE_SANITARIO (Codice_fiscale);

alter table COINVOLGIMENTO add constraint FKCOI_REF
     foreign key (Referto)
     references REFERTO (Codice_referto);

alter table CURA add constraint FKCura_Unita_operativa
     foreign key (Codice_ospedale, Nome_unita)
     references UNITA_OPERATIVA (Codice_ospedale, Nome);

alter table CURA add constraint FKPaziente
     foreign key (Paziente)
     references PAZIENTE (Codice_fiscale);

alter table LAVORA add constraint FKPersonale
     foreign key (Codice_fiscale)
     references PERSONALE_SANITARIO (Codice_fiscale);

alter table LAVORA add constraint FKLavora_Unita_operativa
     foreign key (Codice_ospedale, Nome_unita)
     references UNITA_OPERATIVA (Codice_ospedale, Nome);

alter table OSPEDALE add constraint FKAPPARTENENZA
     foreign key (Cod_ASL)
     references ASL (Codice);

alter table PAZIENTE add constraint FKREGISTRAZIONE
     foreign key (Cod_ASL)
     references ASL (Codice);

alter table PAZIENTE add constraint FKPERS_PAZIENTE_FK
     foreign key (Codice_fiscale)
     references PERSONA (Codice_fiscale); 

alter table PERSONALE_SANITARIO add constraint FKPERS_SANITARI_FK
     foreign key (Codice_fiscale)
     references PERSONA (Codice_fiscale);

alter table PRESENZIA add constraint FKPRE_APP
     foreign key (Codice_ospedale, Numero_sala, Data_ora)
     references APPUNTAMENTO (Codice_ospedale, Numero_sala, Data_ora);

alter table PRESENZIA add constraint FKPRE_PER
     foreign key (Medico)
     references PERSONALE_SANITARIO (Codice_fiscale);

alter table REFERTO add constraint FKEMISSIONE
     foreign key (Codice_ospedale)
     references OSPEDALE (Codice_struttura);

alter table REFERTO add constraint FKASSOCIAZIONE
     foreign key (Paziente)
     references PAZIENTE (Codice_fiscale);

alter table SALA add constraint FKSTRUTTURA
     foreign key (Codice_ospedale)
     references OSPEDALE (Codice_struttura);

alter table TELEFONO add constraint FKUTENZA
     foreign key (Persona)
     references PERSONA (Codice_fiscale);

alter table UNITA_OPERATIVA add constraint FKCOMPOSIZIONE
     foreign key (Codice_ospedale)
     references OSPEDALE (Codice_struttura);
