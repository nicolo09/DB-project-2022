create database Hospital;
use Hospital;

create table AMMINISTRATIVI (
     Codice_fiscale char(16) not null,
     Ruolo varchar(70) not null,
     Codice_ospedale int not null,
     constraint FKPERS_AMMINISTRATIVI_ID primary key (Codice_fiscale));

create table APPUNTAMENTI (
     Codice_ospedale int not null,
     Numero_sala int not null,
     Data_ora datetime not null,
     Durata int not null,
     Tipo varchar(20) not null,
     Paziente char(16) not null,
     constraint IDAPPUNTAMENTO_ID primary key (Codice_ospedale, Numero_sala, Data_ora));

create table ASL (
     Codice int not null AUTO_INCREMENT,
     Nome varchar(30) not null,
     Ind_Citta varchar(35) not null,
     Ind_Via varchar(25) not null,
     Ind_Numero_civico char(8) not null,
     constraint IDASL primary key (Codice));

create table ATTREZZATURE (
     Codice_ospedale int not null,
     Codice_inventario int not null,
     Nome varchar(30) not null,
     Data_manutenzione date not null,
     constraint IDATTREZZATURE primary key (Codice_ospedale, Codice_inventario));

create table COINVOLGIMENTI (
     Referto int not null,
     Medico char(16) not null,
     constraint IDCOINVOLGIMENTI primary key (Referto, Medico));

create table CURE (
     Paziente char(16) not null,
     Codice_ospedale int not null,
     Nome_unita varchar(30) not null,
     Data_ingresso date not null,
     Data_uscita date,
     Motivazione varchar(400) not null,
     constraint IDCURE primary key (Paziente, Codice_ospedale, Nome_unita));

create table LAVORA (
     Codice_ospedale int not null,
     Nome_unita varchar(30) not null,
     Codice_fiscale char(16) not null,
     constraint IDLAVORA primary key (Codice_fiscale, Codice_ospedale, Nome_unita));

create table OSPEDALI (
     Codice_struttura int not null AUTO_INCREMENT,
     Nome varchar(30) not null,
     Ind_Citta varchar(35) not null,
     Ind_Via varchar(25) not null,
     Ind_Numero_civico char(8) not null,
     Cod_ASL int not null,
     constraint IDOSPEDALI primary key (Codice_struttura));

create table PAZIENTI (
     Codice_fiscale char(16) not null,
     Data_nascita date not null,
     Cod_ASL int,
     constraint FKPERS_PAZIENTI_ID primary key (Codice_fiscale));

create table PERSONE (
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Codice_fiscale char(16) not null,
     constraint IDPERSONE_ID primary key (Codice_fiscale));

create table PERSONALE_SANITARIO (
     Codice_fiscale char(16) not null,
     Ruolo varchar(30) not null,
     constraint FKPERS_SANITARI_ID primary key (Codice_fiscale));

create table PRESENZIA (
     Medico char(16) not null,
     Codice_ospedale int not null,
     Numero_sala int not null,
     Data_ora datetime not null,
     constraint IDPRESENZIA primary key (Medico, Codice_ospedale, Numero_sala, Data_ora));

create table REFERTI (
     Codice_referto int not null AUTO_INCREMENT,
     Data_emissione date not null,
     Descrizione varchar(1000) not null,
     Tipo varchar(10) not null,
     Terapia varchar(500),
     Procedura varchar(500),
     Esito varchar(500),
     Durata int,
     Codice_ospedale int not null,
     Paziente char(16) not null,
     constraint IDREFERTI_ID primary key (Codice_referto));

create table SALE (
     Codice_ospedale int not null,
     Numero int not null,
     constraint IDSALE primary key (Codice_ospedale, Numero));

create table TELEFONI (
     Telefono char(15) not null,
     Persona char(16) not null,
     constraint IDTELEFONI primary key (Telefono, Persona));

create table UNITA_OPERATIVE (
     Codice_ospedale int not null,
     Nome varchar(30) not null,
     Capienza int not null,
     Posti_occupati int not null,
     constraint IDUNITA_OPERATIVE primary key (Codice_ospedale, Nome));


-- Constraints Section
-- ___________________ 

alter table AMMINISTRATIVI add constraint FKPERS_AMMINISTRATIVI_FK
     foreign key (Codice_fiscale)
     references PERSONE (Codice_fiscale)
     ON DELETE CASCADE;

alter table AMMINISTRATIVI add constraint FKIMPIEGATI
     foreign key (Codice_ospedale)
     references OSPEDALI (Codice_struttura)
     ON DELETE CASCADE;

alter table APPUNTAMENTI add constraint FKSI_SVOLGE
     foreign key (Codice_ospedale, Numero_sala)
     references SALE (Codice_ospedale, Numero);

alter table APPUNTAMENTI add constraint FKPRENOTA
     foreign key (Paziente)
     references PAZIENTI (Codice_fiscale)
     ON DELETE CASCADE;

alter table ATTREZZATURE add constraint FKPOSSIEDE
     foreign key (Codice_ospedale)
     references OSPEDALI (Codice_struttura)
     ON DELETE CASCADE;

alter table COINVOLGIMENTI add constraint FKCOI_PER
     foreign key (Medico)
     references PERSONALE_SANITARIO (Codice_fiscale);

alter table COINVOLGIMENTI add constraint FKCOI_REF
     foreign key (Referto)
     references REFERTI (Codice_referto)
     ON DELETE CASCADE;

alter table CURE add constraint FKCura_Unita_operativa
     foreign key (Codice_ospedale, Nome_unita)
     references UNITA_OPERATIVE (Codice_ospedale, Nome);

alter table CURE add constraint FKPaziente
     foreign key (Paziente)
     references PAZIENTI (Codice_fiscale);

alter table LAVORA add constraint FKPersonale
     foreign key (Codice_fiscale)
     references PERSONALE_SANITARIO (Codice_fiscale)
     ON DELETE CASCADE;

alter table LAVORA add constraint FKLavora_Unita_operativa
     foreign key (Codice_ospedale, Nome_unita)
     references UNITA_OPERATIVE (Codice_ospedale, Nome)
     ON DELETE CASCADE;

alter table OSPEDALI add constraint FKAPPARTENENZA
     foreign key (Cod_ASL)
     references ASL (Codice);

alter table PAZIENTI add constraint FKREGISTRAZIONE
     foreign key (Cod_ASL)
     references ASL (Codice)
     ON DELETE SET NULL;

alter table PAZIENTI add constraint FKPERS_PAZIENTI_FK
     foreign key (Codice_fiscale)
     references PERSONE (Codice_fiscale)
     ON DELETE CASCADE; 

alter table PERSONALE_SANITARIO add constraint FKPERS_SANITARI_FK
     foreign key (Codice_fiscale)
     references PERSONE (Codice_fiscale)
     ON DELETE CASCADE;

alter table PRESENZIA add constraint FKPRE_APP
     foreign key (Codice_ospedale, Numero_sala, Data_ora)
     references APPUNTAMENTI (Codice_ospedale, Numero_sala, Data_ora)
     ON DELETE CASCADE;

alter table PRESENZIA add constraint FKPRE_PER
     foreign key (Medico)
     references PERSONALE_SANITARIO (Codice_fiscale);

alter table REFERTI add constraint FKEMISSIONE
     foreign key (Codice_ospedale)
     references OSPEDALI (Codice_struttura);

alter table REFERTI add constraint FKASSOCIAZIONE
     foreign key (Paziente)
     references PAZIENTI (Codice_fiscale)
     ON DELETE CASCADE;

alter table SALE add constraint FKSTRUTTURA
     foreign key (Codice_ospedale)
     references OSPEDALI (Codice_struttura)
     ON DELETE CASCADE;

alter table TELEFONI add constraint FKUTENZA
     foreign key (Persona)
     references PERSONE (Codice_fiscale)
     ON DELETE CASCADE;

alter table UNITA_OPERATIVE add constraint FKCOMPOSIZIONE
     foreign key (Codice_ospedale)
     references OSPEDALI (Codice_struttura)
     ON DELETE CASCADE;
