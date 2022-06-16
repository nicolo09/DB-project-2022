-- 1 Aggiungere un nuovo ospedale
INSERT INTO OSPEDALI(Nome, Ind_Citta, Ind_Via, Ind_Numero_Civico, Cod_ASL)
VALUES(?, ?, ?, ?, ?)

-- 2 Aggiungere una nuova unità operativa
INSERT INTO UNITA_OPERATIVE(Codice_ospedale, Nome, Capienza, Posti_occupati)
VALUES(?, ?, ?, ?)

-- 3 Rimuovere una unità operativa
DELETE FROM UNITA_OPERATIVE
WHERE Codice_ospedale = ? AND Nome = ?

-- 4 Aggiungere un nuovo paziente
-- Controlliamo se il paziente che vogliamo aggiungere è già in PERSONE
SELECT COUNT(*) 
FROM PERSONE
WHERE Codice_fiscale = ?
-- Se questa query restituisce un valore uguale a 0 allora procediamo ad aggiungere la persona, 
-- altrimenti saltiamo al prossimo punto:
INSERT INTO PERSONE(Nome, Cognome, Codice_fiscale)
VALUES(?, ?, ?)
-- Dopodichè si procede con la registrazione del paziente:
INSERT INTO PAZIENTI(Codice_fiscale, Data_nascita, Cod_ASL)
VALUES(?, ?, ?)

-- 5 Aggiungere un impiegato o medico
-- Controlliamo se l'impiegato/medico che vogliamo aggiungere è già in PERSONE
SELECT COUNT(*) 
FROM PERSONE
WHERE Codice_fiscale = ?
-- Se questa query restituisce un valore uguale a 0 allora procediamo ad aggiungere la persona, 
-- altrimenti saltiamo al prossimo punto:
INSERT INTO PERSONE(Nome, Cognome, Codice_fiscale)
VALUES(?, ?, ?)
-- Dopodichè si aggiunge ad Amministrativi
INSERT INTO AMMINISTRATIVI(Codice_fiscale, Ruolo, Codice_ospedale)
VALUES(?, ?, ?)
-- Oppure al Personale Sanitario
INSERT INTO PERSONALE_SANITARIO(Codice_fiscale, Ruolo)
VALUES(?, ?)

-- 6 Fissare un appuntamento
-- Cerco appuntamenti già fissati con lo stesso medico o nella stessa sala che si sovrapporrebbero a quello che si vorrebbe fissare.
-- I parametri della seguente query sono 4, nell'ordine la data e ora, la durata, il medico, e la sala del nuovo appuntamento che si vuole fissare.
SET @newdate = ?;
SET @durata = ?;
SELECT *
FROM APPUNTAMENTI
INNER JOIN PRESENZIA
ON APPUNTAMENTI.Numero_sala = PRESENZIA.Numero_sala AND APPUNTAMENTI.Codice_ospedale = PRESENZIA.Codice_ospedale
WHERE (Medico = ? AND 
    (TIMESTAMPDIFF(SECOND, @newdate , PRESENZIA.Data_ora) <= 0 AND TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, Durata, PRESENZIA.Data_ora), @newdate) < 0) OR
    (TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, @durata, @newdate), PRESENZIA.Data_ora) < 0 AND TIMESTAMPDIFF(SECOND, @newdate, PRESENZIA.Data_ora) > 0))
    OR
    (PRESENZIA.Numero_sala = ? AND 
    (TIMESTAMPDIFF(SECOND, @newdate, PRESENZIA.Data_ora) <= 0 AND TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, Durata, PRESENZIA.Data_ora), @newdate) < 0) OR
    (TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, @durata, @newdate), PRESENZIA.Data_ora) < 0 AND TIMESTAMPDIFF(SECOND, @newdate, PRESENZIA.Data_ora) > 0))
-- Se la query restituisce un numero vuol dire che l'appuntamento si sovrapporrebbe ad un altro già fissato, altrimenti si può procedere a inserirlo 
-- con la seguente query:
INSERT INTO APPUNTAMENTI(Codice_ospedale, Numero_sala, Data_ora, Durata, Tipo, Paziente)
VALUES(?, ?, ?, ?, ?, ?)

INSERT INTO PRESENZIA(Medico, Codice_ospedale, Numero_sala, Data_ora)
VALUES(?, ?, ?, ?)

-- 7 Cancellare un appuntamento
-- Per cancellare un appuntamento è sufficiente eliminarlo dalla tabella appuntamenti, i vincoli si occuperanno di eliminare a cascata
-- le presenze dei medici all'appuntamento dalla tabella presenzia.
DELETE FROM APPUNTAMENTI
WHERE Codice_ospedale = ? AND Numero_sala = ? AND Data_ora = ?

-- 8 Cambiare la data e/o il luogo di un appuntamento
UPDATE APPUNTAMENTI
SET Data_ora = ?, Numero_sala = ?
WHERE Codice_ospedale = ? AND Numero_sala = ? AND Data_ora = ?

-- 9 Aggiungere un nuovo referto
INSERT INTO REFERTI(Data_emissione, Descrizione, Tipo, Terapia, Procedura, Esito, Durata, Codice_ospedale, Paziente)
VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)

-- 10 Ricercare referti per medico o per paziente
-- Per avere tutti i referti associati ad un paziente specifico si utilizza la seguente query:
SELECT *
FROM REFERTI
WHERE Paziente = ?
-- Per avere i referti associati ad un medico invece si usa la seguente:
SELECT *
FROM REFERTI
WHERE Codice_referto IN (
    SELECT Referto
    FROM COINVOLGIMENTI
    WHERE Medico = ?
    )

-- 11 Aggiungere nuova attrezzatura
INSERT INTO ATTREZZATURE(Codice_ospedale, Codice_inventario, Nome, Data_manutenzione)
VALUES(?, ?, ?, ?)

-- 12 Rimuovere attrezzatura
DELETE FROM ATTREZZATURE
WHERE Codice_ospedale = ? AND Codice_inventario = ?

-- 13 Aggiornare la data di manutenzione di un'attrezzatura
UPDATE ATTREZZATURE
SET Data_emissione = ?
WHERE Codice_ospedale = ? AND Codice_inventario = ?

-- 14 Ricercare ospedali con determinate unità operative
SELECT *
FROM OSPEDALI
WHERE Codice_struttura IN (
    SELECT Codice_ospedale
    FROM UNITA_OPERATIVE
    WHERE Nome = ?
)

-- 15 Ricerca di ospedali appartenenti ad un'ASL specifica
SELECT *
FROM OSPEDALI
WHERE Cod_ASL = ?

-- 16 Aggiungere una nuova sala ad un ospedale
INSERT INTO SALE(Codice_ospedale, Numero)
VALUES(?, ?)

-- 17 Rimuovere una sala da un ospedale
DELETE FROM SALE
WHERE Codice_ospedale = ?, Numero = ?

-- 18 Ricercare ospedali con posti liberi in una determinata unità operativa
SELECT *
FROM OSPEDALI
WHERE Codice_struttura IN (
    SELECT Codice_ospedale
    FROM UNITA_OPERATIVE
    WHERE Nome = ? AND Capienza > Posti_occupati
)

-- 19 Aggiungere pazienti in cura presso un ospedale
INSERT INTO CURE(Paziente, Codice_ospedale, Nome_unita, Data_ingresso, Data_uscita, Motivazione)
VALUES(?, ?, ?, ?, ?, ?)

-- 20 Rimuovere un paziente in cura presso un ospedale
UPDATE CURE
SET Data_uscita = ?
WHERE Paziente = ?, Codice_ospedale = ?, Nome_unita = ?