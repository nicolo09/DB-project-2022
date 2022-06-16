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

public class ModelImpl implements Model{

    private Connection dbConnection;
    private String username;
    private String password;
    private String dbName = "hospital";
    private String tablePersons = "persone";
    private String tableDoctors = "personale_sanitario";
    private String tablePatients = "pazienti";

    /**
     * Creates a simple connection to a local database
     */
    public ModelImpl() {
        dbConnection = new ConnectionProvider(username, password, dbName).getMySQLConnection();
    }
    
    @Override
    public Collection<Person> getPersons(Optional<String> name, Optional<String> surname) {
        String query = "SELECT * FROM " + tablePersons + " ";
        if (name.isPresent()) {
            query += "WHERE Nome='" + name.get() + "' ,";
        }
        if (surname.isPresent()) {
            query += "WHERE Cognome='" + surname.get() + "' ,";
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
            query += "Nome='" + name.get() + "', ";
        }
        if (surname.isPresent()) {
            query += "Cognome='" + surname.get() + "', ";
        }
        if (role.isPresent()) {
            query += "Ruolo='" + role.get() + "', ";
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
                + "ON pazienti.Codice_fiscale = pazienti.Codice_fiscale" + " WHERE ";
        if (name.isPresent()) {
            query += "Nome='" + name.get() + "', ";
        }
        if (surname.isPresent()) {
            query += "Cognome='" + surname.get() + "', ";
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

}
