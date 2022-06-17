package db.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import db.project.model.mysql.ConnectionProvider;

public class ModelImpl implements Model {

    private Connection dbConnection;
    private String dbName = "hospital";
    private String tablePersons = "persone";
    private String tableDoctors = "personale_sanitario";
    private String tablePatients = "pazienti";
    private String tableReports = "referti";
    private String tableHospital = "ospedali";
    private String tableASL = "asl";

    /**
     * Creates a simple connection to a local database
     */
    public ModelImpl(String username, String password) {
        dbConnection = new ConnectionProvider(username, password, dbName).getMySQLConnection();
    }
    
    @Override
    public Collection<Person> getPersons(Optional<String> name, Optional<String> surname) {
        String query = "SELECT * FROM " + tablePersons + " ";
        if (name.isPresent()) {
            query += "WHERE Nome LIKE '" + name.get() + "' ,";
        }
        if (surname.isPresent()) {
            query += "WHERE Cognome LIKE '" + surname.get() + "' ,";
        }
        query = query.substring(0, query.length() - 2);
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
                + "ON personale_sanitario.Codice_fiscale = persone.Codice_fiscale" + " WHERE ";
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
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readPersonsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Person> getDoctor(String CF) {
        String query = "SELECT COUNT(*) AS total FROM " + tableDoctors + " WHERE Codice_fiscale = " + CF;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            if (statement.getResultSet().getInt("total") != 1) {
                return Optional.empty();
            }
            else {
                return getPerson(CF);
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public Collection<Person> getPatients(Optional<String> name, Optional<String> surname, Optional<Date> birthDate,
            Optional<Integer> ASLCode) {
        String query = "SELECT persone.*, pazienti.Data_nascita, pazienti.Cod_ASL FROM pazienti INNER JOIN persone "
                + "ON pazienti.Codice_fiscale = persone.Codice_fiscale" + " WHERE ";
        if (name.isPresent()) {
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (surname.isPresent()) {
            query += "Cognome LIKE '" + surname.get() + "', ";
        }
        if (birthDate.isPresent()) {
            query += "Data_nascita='" + birthDate.get() + "', ";
        }
        if (ASLCode.isPresent()) {
            query += "Cod_ASL=" + ASLCode.get() + ", ";
        }
        query = query.substring(0, query.length()-2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readPersonsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }
    
    @Override
    public Optional<Person> getPatient(String CF) {
        String query = "SELECT COUNT(*) AS total FROM " + tablePatients  + " WHERE Codice_fiscale = " + CF;
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            if (statement.getResultSet().getInt("total") != 1) {
                return Optional.empty();
            }
            else {
                return getPerson(CF);
            }
        } catch (final SQLException e) {
            return Optional.empty();
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

    @Override
    public Collection<Person> getManagers(Optional<String> name, Optional<String> surname, Optional<String> role,
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
        query = query.substring(0, query.length()-2);
        try (final PreparedStatement statement = this.dbConnection.prepareStatement(query)) {
            statement.executeQuery();
            return readPersonsFromResultSet(statement.getResultSet());
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private Optional<ASL> getASL(final Integer code) {
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

    private Boolean checkHospitalExists(final Integer code) {
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

    private Boolean checkASLExists(final Integer code) {
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
}
