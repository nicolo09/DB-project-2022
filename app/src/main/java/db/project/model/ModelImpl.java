package db.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import db.project.model.mysql.ConnectionProvider;
import db.project.model.mysql.DataInserter;
import db.project.model.mysql.DataInserterImpl;
import db.project.model.mysql.DataRemover;
import db.project.model.mysql.DataRemoverImpl;
import db.project.model.mysql.DataUpdater;
import db.project.model.mysql.DataUpdaterImpl;
import db.project.model.mysql.HospitalDeletionCounter;
import db.project.model.mysql.HospitalDeletionCounterImpl;
import javafx.util.Pair;

public class ModelImpl implements Model {

    private Connection dbConnection;
    private final DataInserter inserter;
    private final DataUpdater updater;
    private final DataRemover remover;
    private final HospitalDeletionCounter counter;
    private String dbName = "hospital";
    private String tablePersons = "persone";
    private String tableReports = "referti";
    private String tableHospital = "ospedali";
    private String tableASL = "asl";
    private String tableUo = "uo";
    private String tableAppointment = "appuntamenti";
    private String tablePresence = "presenzia";
    private String tableInvolvements = "coinvolgimenti";
    private String tableCure = "cure";
    private String tableTelephones = "telefoni";
    private String tableEquipments = "attrezzature";

    /**
     * Creates a simple connection to a local database
     */
    public ModelImpl(String username, String password) {
        dbConnection = new ConnectionProvider(username, password, dbName).getMySQLConnection();

        inserter = new DataInserterImpl(dbConnection);
        updater = new DataUpdaterImpl(dbConnection);
        remover = new DataRemoverImpl(dbConnection);
        counter = new HospitalDeletionCounterImpl(dbConnection);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Person> getPersons(Optional<String> name, Optional<String> surname) {
        String query = "SELECT * FROM " + tablePersons + " ";
        if (name.isPresent() || surname.isPresent()) {
            query += "WHERE ";
            if (name.isPresent()) {
                query += "Nome LIKE '" + name.get() + "' ,";
            }
            if (surname.isPresent()) {
                query += "Cognome LIKE '" + surname.get() + "' ,";
            }
            query = query.substring(0, query.length() - 2);
        }
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readPersonsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Person> getPerson(String CF) {
        String query = "SELECT * FROM " + tablePersons + " ";
        query += "WHERE Codice_fiscale='" + CF + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            Collection<Person> result = readPersonsFromResultSet(statement.getResultSet());
            if (result.size() == 1) {
                return Optional.of(result.iterator().next());
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Person> getDoctors(Optional<String> name, Optional<String> surname, Optional<String> role) {
        String query = "SELECT persone.*, personale_sanitario.Ruolo FROM personale_sanitario INNER JOIN persone "
                + "ON personale_sanitario.Codice_fiscale = persone.Codice_fiscale";
        if (name.isPresent() || surname.isPresent() || role.isPresent()) {
            query += " WHERE ";
            if (name.isPresent()) {
                query += "Nome LIKE '" + name.get() + "', ";
            }
            if (surname.isPresent()) {
                query += "Cognome LIKE '" + surname.get() + "', ";
            }
            if (role.isPresent()) {
                query += "Ruolo LIKE '" + role.get() + "', ";
            }
            query = query.substring(0, query.length() - 2);
        }
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readDoctorsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Person> getDoctor(String CF) {
        String query = "SELECT persone.*, personale_sanitario.Ruolo FROM personale_sanitario INNER JOIN persone "
                + "ON personale_sanitario.Codice_fiscale = persone.Codice_fiscale"
                + " WHERE personale_sanitario.Codice_fiscale = '" + CF + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(new DoctorImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale"), resultSet.getString("Ruolo")));
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode) {
        String query = "SELECT persone.*, pazienti.Data_nascita, pazienti.Cod_ASL FROM pazienti INNER JOIN persone "
                + "ON pazienti.Codice_fiscale = persone.Codice_fiscale";
        if (name.isPresent() || surname.isPresent() || birthDate.isPresent() || ASLCode.isPresent()) {
            query += " WHERE ";
            if (name.isPresent()) {
                query += "Nome LIKE '" + name.get() + "', ";
            }
            if (surname.isPresent()) {
                query += "Cognome LIKE '" + surname.get() + "', ";
            }
            if (birthDate.isPresent()) {
                query += "Data_nascita='" + new java.sql.Date(birthDate.get().getTime()) + "', ";
            }
            if (ASLCode.isPresent()) {
                query += "Cod_ASL=" + ASLCode.get() + ", ";
            }
            query = query.substring(0, query.length() - 2);
        }
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readPatientsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Person> getPatient(String CF) {
        String query = "SELECT persone.*, pazienti.Data_nascita, pazienti.Cod_ASL FROM pazienti INNER JOIN persone "
                + "ON pazienti.Codice_fiscale = persone.Codice_fiscale" + " WHERE pazienti.Codice_fiscale = '" + CF
                + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(new PatientImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale"), new Date(resultSet.getDate("Data_nascita").getTime()),
                        resultSet.getInt("Cod_ASL") == 0 ? Optional.empty()
                                : this.getASL(resultSet.getInt("Cod_ASL"))));
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Person> getAdministratives(Optional<String> name, Optional<String> surname, Optional<String> role,
            Optional<Integer> hospitalCode) {
        String query = "SELECT persone.*, amministrativi.Codice_fiscale, amministrativi.Ruolo, "
                + "amministrativi.Codice_ospedale FROM amministrativi INNER JOIN persone "
                + "ON amministrativi.Codice_fiscale = persone.Codice_fiscale" + " WHERE ";
        if (name.isPresent()) {
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (surname.isPresent()) {
            query += "Cognome LIKE '" + surname.get() + "', ";
        }
        if (role.isPresent()) {
            query += "Ruolo LIKE '" + role.get() + "', ";
        }
        if (hospitalCode.isPresent()) {
            query += "Codice_ospedale=" + hospitalCode.get() + ", ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readAdministrativesFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Person> getAdministrative(String CF) {
        String query = "SELECT persone.*, amministrativi.Codice_fiscale, amministrativi.Ruolo, "
                + "amministrativi.Codice_ospedale FROM amministrativi INNER JOIN persone "
                + "ON amministrativi.Codice_fiscale = persone.Codice_fiscale" + " WHERE Codice_fiscale LIKE '" + CF
                + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            var result = readAdministrativesFromResultSet(statement.getResultSet());
            if (result.size() == 1) {
                return Optional.of(result.iterator().next());
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private Collection<Report> readReportsFromResultSet(ResultSet resultSet) {
        Set<Report> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                // Discrimina tra visite e interventi
                switch (REPORT_TYPES.getReportType(resultSet.getString("Tipo"))) {
                case SURGERY -> result.add(new SurgeryReportImpl(resultSet.getInt("Codice_referto"),
                        resultSet.getDate("Data_emissione"), resultSet.getString("Descrizione"),
                        this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                        this.getPatient(resultSet.getString("Paziente")).get(), resultSet.getString("Procedura"),
                        resultSet.getString("Esito"), Duration.ofMinutes(resultSet.getInt("Durata")),
                        this.getDoctorsFromReferto(resultSet.getInt("Codice_referto"))));
                case VISIT -> result.add(new VisitReportImpl(resultSet.getInt("Codice_referto"),
                        resultSet.getDate("Data_emissione"), resultSet.getString("Descrizione"),
                        this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                        this.getPatient(resultSet.getString("Paziente")).get(), resultSet.getString("Terapia"),
                        this.getDoctorsFromReferto(resultSet.getInt("Codice_referto"))));
                default -> throw new IllegalArgumentException("Unexpected value: " + resultSet.getString("Tipo"));
                }
            }
        } catch (SQLException e) {
            return Set.of();
        }
        return result;
    }

    @Override
    public Collection<Report> getReportsFromPatient(final Person patient) {
        String query = "SELECT * " + "FROM " + tableReports + " WHERE " + "Paziente LIKE '" + patient.getCF() + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            return readReportsFromResultSet(rs);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Collection<Report> getReportsFromDoctor(final Person doctor) {
        String query = "SELECT * FROM " + tableReports + "WHERE Codice_referto IN " + "(SELECT Referto FROM "
                + tableInvolvements + " WHERE Medico LIKE '" + doctor.getCF() + "')";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readReportsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    public Collection<Person> getDoctorsFromReferto(Integer reportCode) {
        String query = "SELECT persone.*, personale_sanitario.Ruolo FROM" + tableInvolvements + " INNER JOIN persone "
                + "ON persone.Codice_fiscale = " + tableInvolvements + ".Medico "
                + "INNER JOIN personale_sanitario ON persone.Codice_fiscale = personale_sanitario.Codice_fiscale "
                + "WHERE Referto = " + reportCode;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readDoctorsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Person> readPersonsFromResultSet(ResultSet resultSet) {
        Set<Person> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new PersonImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Collection<Person> readDoctorsFromResultSet(ResultSet resultSet) {
        Set<Person> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new DoctorImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale"), resultSet.getString("Ruolo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Collection<Person> readAdministrativesFromResultSet(ResultSet resultSet) {
        Set<Person> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new AdministrativeImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale"), resultSet.getString("Ruolo"),
                        this.getHospital(resultSet.getInt("Codice_ospedale")).get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Collection<Person> readPatientsFromResultSet(ResultSet resultSet) {
        Set<Person> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new PatientImpl(resultSet.getString("Nome"), resultSet.getString("Cognome"),
                        resultSet.getString("Codice_fiscale"), new Date(resultSet.getDate("Data_nascita").getTime()),
                        resultSet.getInt("Cod_ASL") == 0 ? Optional.empty()
                                : this.getASL(resultSet.getInt("Cod_ASL"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Hospital> getHospital(final Integer code) {
        if (checkHospitalExists(code) == false) {
            return Optional.empty();
        }
        String query = "SELECT * FROM " + tableHospital + " " + "WHERE Codice_struttura = " + code;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            Collection<Hospital> result = readHospitalsFromResultSet(statement.getResultSet());
            if (result.size() == 1) {
                return Optional.of(result.iterator().next());
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private Collection<Hospital> readHospitalsFromResultSet(final ResultSet resultSet) {
        Set<Hospital> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new HospitalImpl(resultSet.getInt("Codice_struttura"), resultSet.getString("Nome"),
                        resultSet.getString("Ind_Citta"), resultSet.getString("Ind_Via"),
                        resultSet.getString("Ind_Numero_civico"), this.getASL(resultSet.getInt("Cod_ASL")).get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<ASL> getASL(final Integer code) {
        if (checkASLExists(code) == false) {
            return Optional.empty();
        }
        String query = "SELECT * FROM " + tableASL + " " + "WHERE Codice = " + code;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            Collection<ASL> result = readASLFromResultSet(statement.getResultSet());
            if (result.size() == 1) {
                return Optional.of(result.iterator().next());
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private Collection<ASL> readASLFromResultSet(ResultSet resultSet) {
        Set<ASL> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new ASLImpl(resultSet.getInt("Codice"), resultSet.getString("Nome"),
                        resultSet.getString("Ind_Citta"), resultSet.getString("Ind_Via"),
                        resultSet.getString("Ind_Numero_civico")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean checkHospitalExists(final Integer code) {
        String query = "SELECT COUNT(*) AS total FROM " + tableHospital + " WHERE Codice_struttura = " + code;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            if (resultSet.getInt("total") != 1) {
                return false;
            } else {
                return true;
            }
        } catch (final SQLException e) {
            return false;
        }
    }

    private boolean checkASLExists(final Integer code) {
        String query = "SELECT COUNT(*) AS total FROM " + tableASL + " WHERE Codice = " + code;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            if (resultSet.getInt("total") != 1) {
                return false;
            } else {
                return true;
            }
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public Collection<ASL> getASL(Optional<String> name, Optional<String> city, Optional<String> way,
            Optional<String> number) {
        String query = "SELECT * FROM asl" + " WHERE ";
        if (name.isPresent()) {
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (city.isPresent()) {
            query += "Ind_Citta LIKE '" + city.get() + "', ";
        }
        if (way.isPresent()) {
            query += "Ind_Via LIKE '" + way.get() + "', ";
        }
        if (number.isPresent()) {
            query += "Ind_Numero_civico LIKE '" + number.get() + "', ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readASLFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Collection<Hospital> getHospitals(Optional<String> name, Optional<String> city, Optional<String> way,
            Optional<String> number, Optional<ASL> asl) {
        String query = "SELECT * FROM " + tableHospital + " WHERE ";
        if (name.isPresent()) {
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (city.isPresent()) {
            query += "Ind_Citta LIKE '" + city.get() + "', ";
        }
        if (way.isPresent()) {
            query += "Ind_Via LIKE '" + way.get() + "', ";
        }
        if (number.isPresent()) {
            query += "Ind_Numero_civico LIKE '" + number.get() + "', ";
        }
        if (asl.isPresent()) {
            query += "Cod_ASL = " + asl.get().getCode() + ", ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readHospitalsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Uo> getUo(Hospital hospital, String name) {
        String query = "SELECT * FROM " + tableUo + " WHERE " + "Codice_ospedale = " + hospital.getCode() + " AND "
                + "Nome = '" + name + "'";
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            Collection<Uo> result = readUoFromResultSet(statement.getResultSet());
            if (result.size() == 1) {
                return Optional.of(result.iterator().next());
            } else {
                return Optional.empty();
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private Collection<Uo> readUoFromResultSet(ResultSet resultSet) {
        Set<Uo> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new UoImpl(this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                        resultSet.getString("Nome"), resultSet.getInt("Capacita"), resultSet.getInt("Posti_occupati")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Uo> getUos(Optional<String> name, Optional<Boolean> freeSpace, Optional<Hospital> hospital) {
        String query = "SELECT * FROM " + tableHospital + " WHERE ";
        if (name.isPresent()) {
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (freeSpace.isPresent()) {
            if (freeSpace.get()) {
                query += "Capienza > Posti_occupati, ";
            } else {
                query += "Capienza <= Posti_occupati, ";
            }
        }
        if (hospital.isPresent()) {
            query += "Codice_ospedale = " + hospital.get().getCode() + ", ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readUoFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Collection<Appointment> getAppointments(Optional<Person> doctor, Optional<Person> patient,
            Optional<Hospital> hospital, Optional<LocalDate> date) {
        String query = "SELECT DISTINCT " + tableAppointment + ".*" + " FROM " + tableAppointment + " INNER JOIN "
                + tablePresence + " ON " + tableAppointment + ".Numero_sala = " + tablePresence + ".Numero_sala"
                + " AND " + tableAppointment + ".Codice_ospedale = " + tablePresence + ".Codice_ospedale" + " AND "
                + tableAppointment + ".Data_ora = " + tablePresence + ".Data_ora " + "WHERE ";
        if (doctor.isPresent()) {
            query += "Medico = '" + doctor.get().getCF() + "'', ";
        }
        if (patient.isPresent()) {
            query += "Paziente = '" + patient.get().getCF() + "', ";
        }
        if (hospital.isPresent()) {
            query += "Codice_ospedale = " + hospital.get().getCode() + ", ";
        }
        if (date.isPresent()) {
            query += "Data = '" + java.sql.Date.valueOf(date.get()) + "', ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            return readAppointmentsFromResultSet(rs);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Appointment> readAppointmentsFromResultSet(final ResultSet resultSet) {
        Set<Appointment> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                final Room room = new RoomImpl(this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                        resultSet.getInt("Numero_sala"));
                result.add(new AppointmentImpl(room, resultSet.getTimestamp("Data_ora").toLocalDateTime(),
                        Duration.ofMinutes(resultSet.getInt("Durata")),
                        this.getPatient(resultSet.getString("Paziente")).get(), resultSet.getString("Tipo"),
                        getAppointmentDoctors(room, resultSet.getTimestamp("Data_ora").toLocalDateTime())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection<Person> getAppointmentDoctors(final Room room, final LocalDateTime dateTime) {
        String query = "SELECT * FROM " + tablePresence + " WHERE " + "Numero_sala = " + room.getRoomNumber() + " AND "
                + "Data_ora = '" + java.sql.Timestamp.valueOf(dateTime) + "'";
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            Set<Person> result = new HashSet<>();
            while (resultSet.next()) {
                result.add(this.getDoctor(resultSet.getString("Medico")).get());
            }
            return result;
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Collection<Cure> getCures(Optional<Person> patient, Optional<Uo> uo,
            Optional<Pair<LocalDate, LocalDate>> dateInInterval, Optional<Pair<LocalDate, LocalDate>> dateOutInterval,
            Optional<String> reason) {
        String query = "SELECT * FROM " + tableCure + " WHERE ";
        if (patient.isPresent()) {
            query += "Paziente = '" + patient.get().getCF() + "', ";
        }
        if (uo.isPresent()) {
            query += "Nome_unita = " + uo.get().getName() + ", " + "Codice_ospedale = "
                    + uo.get().getHospital().getCode() + ", ";
        }
        if (dateInInterval.isPresent()) {
            query += "Data_inizio BETWEEN '" + java.sql.Date.valueOf(dateInInterval.get().getKey()) + "' AND '"
                    + java.sql.Date.valueOf(dateInInterval.get().getValue()) + "', ";
        }
        if (dateOutInterval.isPresent()) {
            query += "Data_fine BETWEEN '" + java.sql.Date.valueOf(dateOutInterval.get().getKey()) + "' AND '"
                    + java.sql.Date.valueOf(dateOutInterval.get().getValue()) + "', ";
        }
        if (reason.isPresent()) {
            query += "Motivazione LIKE '" + reason.get() + "', ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readCuresFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Cure> readCuresFromResultSet(ResultSet resultSet) {
        Set<Cure> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new CureImpl(this.getPatient(resultSet.getString("Paziente")).get(),
                        this.getUo(this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                                resultSet.getString("Nome_unita")).get(),
                        resultSet.getDate("Data_ingresso").toLocalDate(),
                        resultSet.getDate("Data_uscita").toLocalDate(), resultSet.getString("Motivazione")));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Set.of();
        }
    }

    @Override
    public Collection<Pair<Person, String>> getTelephones(Person person) {
        String query = "SELECT * FROM " + tableTelephones + " WHERE " + "Persona = " + person.getCF();
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readTelephonesFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Pair<Person, String>> readTelephonesFromResultSet(ResultSet resultSet) {
        Set<Pair<Person, String>> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new Pair<>(this.getPerson(resultSet.getString("Persona")).get(),
                        resultSet.getString("Telefono")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Equipment> getEquipment(Hospital hospital) {
        String query = "SELECT * FROM " + tableEquipments + " WHERE " + " Codice_ospedale = " + hospital.getCode();
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readEquipmentsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Equipment> readEquipmentsFromResultSet(ResultSet resultSet) {
        Set<Equipment> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new EquipmentImpl(resultSet.getInt("Codice_inventario"), resultSet.getString("Nome"),
                        resultSet.getDate("Data_manutenzione").toLocalDate(),
                        this.getHospital(resultSet.getInt("Codice_ospedale")).get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Room> getRooms(Hospital hospital) {
        String query = "SELECT * FROM " + TABLES.ROOM.get() + " WHERE " + " Codice_ospedale = " + hospital.getCode();
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readRoomsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Room> readRoomsFromResultSet(ResultSet resultSet) {
        Set<Room> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new RoomImpl(this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                        resultSet.getInt("Numero")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Pair<Uo, Person>> getImpieghi(Optional<Person> doctor, Optional<Uo> uo) {
        String query = "SELECT * FROM " + TABLES.WORKING.get() + " WHERE ";
        if (doctor.isPresent()) {
            query += "Codice_fiscale LIKE '" + doctor.get().getCF() + "', ";
        }
        if (uo.isPresent()) {
            query += "Codice_ospedale = " + uo.get().getHospital().getCode() + ", ";
            query += "Nome_unita = " + uo.get().getName() + ", ";
        }
        query = query.substring(0, query.length() - 2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readImpieghiFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Collection<Pair<Uo, Person>> readImpieghiFromResultSet(ResultSet resultSet) {
        Set<Pair<Uo, Person>> result = new HashSet<>();
        try {
            while (resultSet.next()) {
                result.add(new Pair<>(
                        this.getUo(this.getHospital(resultSet.getInt("Codice_ospedale")).get(),
                                resultSet.getString("Nome_unita")).get(),
                        this.getDoctor(resultSet.getString("Codice_fiscale")).get()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name,
            Optional<String> lastName) {
        return inserter.insertAmministratives(CF, role, hospitalCode, name, lastName);
    }

    @Override
    public OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration,
            String type, String patientCF, Collection<String> doctorCF) {
        return inserter.insertAppointment(hospitalCode, roomNumber, date, duration, type, patientCF, doctorCF);
    }

    @Override
    public OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber) {
        return inserter.insertASL(name, city, street, streetNumber);
    }

    @Override
    public OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
            Optional<Date> exitDate, String description) {
        return inserter.insertCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
    }

    @Override
    public OPERATION_OUTCOME insertEquipment(int hospitalCode, String name, Date lastMaintenance) {
        return inserter.insertEquipment(hospitalCode, name, lastMaintenance);
    }

    @Override
    public OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName) {
        return inserter.insertHealtcare(CF, role, name, lastName);
    }

    @Override
    public OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL) {
        return inserter.insertHospital(name, city, street, streetNumber, codeASL);
    }

    @Override
    public OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name,
            Optional<String> lastName) {
        return inserter.insertPatient(CF, birthDay, codASL, name, lastName);
    }

    @Override
    public OPERATION_OUTCOME insertPerson(String CF, String name, String lastName) {
        return inserter.insertPerson(CF, name, lastName);
    }

    @Override
    public OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF) {
        return inserter.insertPhone(phoneNumber, personCF);
    }

    @Override
    public OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type, Optional<String> therapy,
            Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration, int hospitalCode,
            String patientCF, Collection<String> doctorCF) {
        return inserter.insertReport(emissionDate, description, type, therapy, procedure, outcome, duration,
                hospitalCode, patientCF, doctorCF);
    }

    @Override
    public OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber) {
        return inserter.insertRoom(hospitalCode, roomNumber);
    }

    @Override
    public OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
        return inserter.insertUO(hospitalCode, name, capacity, seatsOccupied);
    }

    @Override
    public OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode) {
        return inserter.insertWorking(CF, unitName, hospitalCode);
    }

    @Override
    public OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode) {
        return updater.updateAmministratives(CF, role, hospitalCode);
    }

    @Override
    public OPERATION_OUTCOME updateASL(int codeASL, Optional<String> name, Optional<String> city,
            Optional<String> street, Optional<Integer> streetNumber) {
        return updater.updateASL(codeASL, name, city, street, streetNumber);
    }

    @Override
    public OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
            Optional<Date> exitDate, Optional<String> description) {
        return updater.updateCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
    }

    @Override
    public OPERATION_OUTCOME updateEquipment(int hospitalCode, int inventoryCode, Optional<Date> lastMaintenance) {
        return updater.updateEquipment(hospitalCode, inventoryCode, lastMaintenance);
    }

    @Override
    public OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role) {
        return updater.updateHealtcare(CF, role);
    }

    @Override
    public OPERATION_OUTCOME updateHospital(int structureCode, Optional<String> name, Optional<Integer> codeASL) {
        return updater.updateHospital(structureCode, name, codeASL);
    }

    @Override
    public OPERATION_OUTCOME updatePatient(String CF, Optional<Integer> codASL) {
        return updater.updatePatient(CF, codASL);
    }

    @Override
    public OPERATION_OUTCOME updateUO(int hospitalCode, String name, Optional<Integer> capacity) {
        return updater.updateUO(hospitalCode, name, capacity);
    }

    @Override
    public OPERATION_OUTCOME removeAmministratives(String CF) {
        return remover.removeAmministratives(CF);
    }

    @Override
    public OPERATION_OUTCOME removeAppointment(int hospitalCode, int roomNumber, Timestamp date) {
        return remover.removeAppointment(hospitalCode, roomNumber, date);
    }

    @Override
    public OPERATION_OUTCOME removeASL(int codeASL) {
        return remover.removeASL(codeASL);
    }

    @Override
    public OPERATION_OUTCOME removeCure(String patientCF, int hospitalCode, String unitName, Date ingressDate) {
        return remover.removeCure(patientCF, hospitalCode, unitName, ingressDate);
    }

    @Override
    public OPERATION_OUTCOME removeEquipment(int hospitalCode, int inventoryCode) {
        return remover.removeEquipment(hospitalCode, inventoryCode);
    }

    @Override
    public OPERATION_OUTCOME removeHealtcare(String CF) {
        return remover.removeHealtcare(CF);
    }

    @Override
    public OPERATION_OUTCOME removeHospital(int structureCode) {
        return remover.removeHospital(structureCode);
    }

    @Override
    public OPERATION_OUTCOME removePatient(String CF) {
        return remover.removePatient(CF);
    }

    @Override
    public OPERATION_OUTCOME removePerson(String CF) {
        return remover.removePerson(CF);
    }

    @Override
    public OPERATION_OUTCOME removePhone(String phoneNumber, String personCF) {
        return remover.removePhone(phoneNumber, personCF);
    }

    @Override
    public OPERATION_OUTCOME removeReport(int reportCode) {
        return remover.removeReport(reportCode);
    }

    @Override
    public OPERATION_OUTCOME removeRoom(int hospitalCode, int roomNumber) {
        return remover.removeRoom(hospitalCode, roomNumber);
    }

    @Override
    public OPERATION_OUTCOME removeUO(int hospitalCode, String name) {
        return remover.removeUO(hospitalCode, name);
    }

    @Override
    public OPERATION_OUTCOME removeWorking(String CF, String unitName, int hospitalCode) {
        return remover.removeWorking(CF, unitName, hospitalCode);
    }

    @Override
    public void setHospital(int hospitalCode) {
        this.counter.setHospital(hospitalCode);
    }

    @Override
    public int countDeletedEquipments() {
        return this.counter.countDeletedEquipments();
    }

    @Override
    public int countDeletedAmministratives() {
        return this.counter.countDeletedAmministratives();
    }

    @Override
    public int countDeletedReports() {
        return this.counter.countDeletedReports();
    }

    @Override
    public int countDeletedRooms() {
        return this.counter.countDeletedRooms();
    }

    @Override
    public int countDeletedAppointments() {
        return this.counter.countDeletedAppointments();
    }

    @Override
    public int countDeletedUOs() {
        return this.counter.countDeletedUOs();
    }

    @Override
    public int countDeletedCures() {
        return this.counter.countDeletedCures();
    }

    @Override
    public int countDeletedJobs() {
        return this.counter.countDeletedJobs();
    }

}
