package db.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import db.project.model.mysql.ConnectionProvider;
import db.project.model.mysql.DataInserter;
import db.project.model.mysql.DataInserterImpl;

public class ModelImpl implements Model{

    private Connection dbConnection;
    private String dbName = "hospital";
    private String tablePersons = "persone";
    private String tableDoctors = "personale_sanitario";
    private String tablePatients = "pazienti";
    private final DataInserter inserter;

    /**
     * Creates a simple connection to a local database
     */
    public ModelImpl(String username, String password) {
        dbConnection = new ConnectionProvider(username, password, dbName).getMySQLConnection();
        inserter = new DataInserterImpl(dbConnection);
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
            query += "Nome LIKE '" + name.get() + "', ";
        }
        if (surname.isPresent()) {
            query += "Cognome LIKE '" + surname.get() + "', ";
        }
        if (role.isPresent()) {
            query += "Ruolo LIKE '" + role.get() + "', ";
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

	@Override
	public boolean insertAmministratives(String CF, String Role, int hospitalCode, Optional<String> name, Optional<String> lastName) {
		return inserter.insertAmministratives(CF, Role, hospitalCode, name, lastName);
	}

	@Override
	public boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration,
			String type, String patientCF, Collection<String> doctorCF) {
		return inserter.insertAppointment(hospitalCode, roomNumber, date, duration, type, patientCF, doctorCF);
	}

	@Override
	public boolean insertASL(String name, String city, String street, int streetNumber) {
		return inserter.insertASL(name, city, street, streetNumber);
	}

	@Override
	public boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, String description) {
		return inserter.insertCure(patientCF, hospitalCode, unitName, ingressDate, exitDate, description);
	}

	@Override
	public boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance) {
		return inserter.insertEquipment(hospitalCode, inventoryCode, name, lastMaintenance);
	}

	@Override
	public boolean insertHealtcare(String CF, String Role, Optional<String> unitName, Optional<Integer> hospitalCode) {
		return inserter.insertHealtcare(CF, Role, unitName, hospitalCode);
	}

	@Override
	public boolean insertHospital(int structureCode, String name, String city, String street, String streetNumber) {
		return inserter.insertHospital(structureCode, name, city, street, streetNumber);
	}

	@Override
	public boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL) {
		return inserter.insertPatient(CF, birthDay, codASL);
	}

	@Override
	public boolean insertPerson(String CF, String name, String lastName) {
		return inserter.insertPerson(CF, name, lastName);
	}

	@Override
	public boolean insertPhone(String phoneNumber, String personCF) {
		return inserter.insertPhone(phoneNumber, personCF);
	}

	@Override
	public boolean insertReport(Date emissionDate, String description, String type,
			Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration,
			int hospitalCode, String patientCF, Collection<String> doctorCF) {
		return inserter.insertReport(emissionDate, description, type, therapy, procedure, outcome, duration, hospitalCode, patientCF, doctorCF);
	}

	@Override
	public boolean insertRoom(int hospitalCode, int roomNumber) {
		return inserter.insertRoom(hospitalCode, roomNumber);
	}

	@Override
	public boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
		return inserter.insertUO(hospitalCode, name, capacity, seatsOccupied);
	}

}
