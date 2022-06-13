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