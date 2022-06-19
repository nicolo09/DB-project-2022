package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import db.project.model.TABLES;
import db.project.model.OPERATION_OUTCOME;

//TODO rivedere i tipi inseriti nel database logico e correggerli
public class DataInserterImpl implements DataInserter {
	
	private final static String INSERT_SENTENCE = "INSERT INTO ";
	
	private final Connection connection;
	
	
	public DataInserterImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public OPERATION_OUTCOME insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name, Optional<String> lastName) {
		if(checkNulls(List.of(CF, role, hospitalCode))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String controlQuery = "SELECT COUNT(*) FROM PERSONE WHERE Codice_fiscale LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)) {
			controlStatement.setString(1, CF);
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt("total") > 0) {
				if(name.isEmpty() || lastName.isEmpty()) {
					return OPERATION_OUTCOME.MISSING_ARGUMENTS;
				}
				this.insertPerson(CF, name.get(), lastName.get());
			}
			String query = INSERT_SENTENCE + TABLES.AMMINISTRATIVE.get() + "(Codice_fiscale, Ruolo, Codice_ospedale) VALUES(?, ?, ?)";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
			statement.setString(1, CF);
			statement.setString(2, role);
			statement.setInt(3, hospitalCode);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration,
			String type, String patientCF, Collection<String> doctorCF) {
		
		if(checkNulls(List.of(hospitalCode, roomNumber, date, duration, type, patientCF, doctorCF))){
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String controlQuery = "" 
		+ "SET @newdate = ?;"
		+ "SET @durata = ?;"
		+ "SELECT COUNT(*)"
		+ "FROM "+ TABLES.APPOINTMENT.get() + " "
		+ "INNER JOIN "+ TABLES.PRESENCE.get() + " "
		+ "ON "+ TABLES.APPOINTMENT.get() +".Numero_sala = "+ TABLES.PRESENCE.get() +".Numero_sala AND "+ TABLES.APPOINTMENT.get() +".Codice_ospedale = "+ TABLES.PRESENCE.get() +".Codice_ospedale"
		+ "WHERE (Medico = ? AND "
		+	"(TIMESTAMPDIFF(SECOND, @newdate , "+ TABLES.PRESENCE.get() +".Data_ora) <= 0 AND TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, Durata, "+ TABLES.PRESENCE.get() +".Data_ora), @newdate) < 0) OR"
		+	"(TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, @durata, @newdate), "+ TABLES.PRESENCE.get() +".Data_ora) < 0 AND TIMESTAMPDIFF(SECOND, @newdate, "+ TABLES.PRESENCE.get() +".Data_ora) > 0))"
		+	" OR "
		+	"("+ TABLES.PRESENCE.get() +".Numero_sala = ? AND "
		+	"(TIMESTAMPDIFF(SECOND, @newdate, "+ TABLES.PRESENCE.get() +".Data_ora) <= 0 AND TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, Durata, "+ TABLES.PRESENCE.get() +".Data_ora), @newdate) < 0) OR"
		+	"(TIMESTAMPDIFF(SECOND, TIMESTAMPADD(MINUTE, @durata, @newdate), "+ TABLES.PRESENCE.get() +".Data_ora) < 0 AND TIMESTAMPDIFF(SECOND, @newdate, "+ TABLES.PRESENCE.get() +".Data_ora) > 0))";
		
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			for (String doctor : doctorCF) {
				controlStatement.setTimestamp(1, date);
				controlStatement.setInt(2, duration);
				controlStatement.setString(3, doctor);
				controlStatement.setInt(4, roomNumber);
				var rs = controlStatement.executeQuery();
				rs.next();
				if(rs.getInt("total") > 0) {
					return OPERATION_OUTCOME.OVERLAPPING;
				}
			}
			String query = INSERT_SENTENCE + TABLES.APPOINTMENT.get() + "(Codice_ospedale, Numero_sala, Data_ora, Durata, Tipo, Paziente) VALUES(?, ?, ?, ?, ?, ?)";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
			statement.setInt(1, hospitalCode);
			statement.setInt(2, roomNumber);
			statement.setTimestamp(3, date);
			statement.setInt(4, duration);
			statement.setString(5, type);
			statement.setString(6, patientCF);
			
			statement.executeUpdate();
			
			//TODO return false if cannot add presence or make the user select the doctor/doctors
			doctorCF.forEach(doctor -> insertPresence(doctor, hospitalCode, roomNumber, date));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
				
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertASL(String name, String city, String street, int streetNumber) {
		if(checkNulls(List.of(name, city, street, streetNumber))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.ASL.get() + "(Nome, Ind_Citta, Ind_Via, Ind_Numero_civico) VALUES(?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, name);
			statement.setString(2, city);
			statement.setString(3, street);
			statement.setInt(4, streetNumber);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, String description) {
		
		if(checkNulls(List.of(patientCF, hospitalCode, unitName, ingressDate, description))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.CURE.get() + "(Paziente, Codice_ospedale, Nome_unita, Data_ingresso, Data_uscita, Motivazione) VALUES(?, ?, ?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, patientCF);
			statement.setInt(2, hospitalCode);
			statement.setString(3, unitName);
			statement.setDate(4, new java.sql.Date(ingressDate.getTime()));
			
			if(exitDate.isEmpty()) {
				statement.setNull(5, java.sql.Types.NULL);;
			} else {
				statement.setDate(5, new java.sql.Date(exitDate.get().getTime()));
			}
			
			statement.setString(6, description);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance) {
		if(checkNulls(List.of(hospitalCode, inventoryCode, name, lastMaintenance))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.EQUIPMENT.get() + "(Codice_ospedale, Codice_inventario, Nome, Data_manutenzione) VALUES(?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, hospitalCode);
			statement.setInt(2, inventoryCode);
			statement.setString(3, name);
			statement.setDate(4, new java.sql.Date(lastMaintenance.getTime()));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertHealtcare(String CF, String role, Optional<String> name, Optional<String> lastName) {
		if(checkNulls(List.of(CF, role))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String controlQuery = "SELECT COUNT(*) FROM PERSONE WHERE Codice_fiscale LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)) {
			controlStatement.setString(1, CF);
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt("total") > 0) {
				if(name.isEmpty() || lastName.isEmpty()) {
					return OPERATION_OUTCOME.MISSING_ARGUMENTS;
				}
				this.insertPerson(CF, name.get(), lastName.get());
			}
			
			String query = INSERT_SENTENCE + TABLES.HEALTHCARE.get() + "(Codice_fiscale, Ruolo) VALUES(?, ?)";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
			statement.setString(1, CF);
			statement.setString(2, role);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertHospital(String name, String city, String street, int streetNumber, int codeASL) {
		if(checkNulls(List.of(name, city, street, streetNumber, codeASL))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = INSERT_SENTENCE + TABLES.HOSPITAL.get() + "(Nome, Ind_Citta, Ind_via, Ind_Numero_civico, Cod_ASL) VALUES(?, ?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, name);
			statement.setString(2, city);
			statement.setString(3, street);
			statement.setInt(4, streetNumber);
			statement.setInt(5, codeASL);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertPatient(String CF, Date birthDay, Optional<Integer> codASL, Optional<String> name, Optional<String> lastName) {
		if(checkNulls(List.of(CF, birthDay))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String controlQuery = "SELECT COUNT(*) FROM PERSONE WHERE Codice_fiscale LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)) {
			controlStatement.setString(1, CF);
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt("total") > 0) {
				if(name.isEmpty() || lastName.isEmpty()) {
					return OPERATION_OUTCOME.MISSING_ARGUMENTS;
				}
				this.insertPerson(CF, name.get(), lastName.get());
			}
			
			String query = INSERT_SENTENCE + TABLES.PATIENT.get() + "(Codice_fiscale, Data_nascita, Cod_ASL) VALUES(?, ?, ?)";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
			statement.setString(1, CF);
			statement.setDate(2, new java.sql.Date(birthDay.getTime()));
			if(codASL.isPresent()) {
				statement.setInt(3, codASL.get());
			} else {
				statement.setNull(3, java.sql.Types.NULL);
			}
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertPerson(String CF, String name, String lastName) {
		if(checkNulls(List.of(CF, name, lastName))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.PERSON.get() + "(Codice_fiscale, Nome, Cognome) VALUES(?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, CF);
			statement.setString(2, name);
			statement.setString(3, lastName);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertPhone(String phoneNumber, String personCF) {
		if(checkNulls(List.of(phoneNumber, personCF))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = INSERT_SENTENCE + TABLES.PHONE.get() + "(Telefono, Persona) VALUES(?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, phoneNumber);
			statement.setString(2, personCF);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertReport(Date emissionDate, String description, String type,
			Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration,
			int hospitalCode, String patientCF, Collection<String> doctorCF) {
		
		if(checkNulls(List.of(emissionDate, description, type, hospitalCode, patientCF, doctorCF))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.REPORT.get() + "(Data_emissione, Descrizione, Tipo, Terapia, Procedura, Esito, Durata, Codice_ospedale, Paziente) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			if(therapy.isEmpty()) {
				if(procedure.isEmpty() || outcome.isEmpty() || duration.isEmpty()) {				
					return OPERATION_OUTCOME.WRONG_INSERTION;
				} else {
					statement.setNull(4, java.sql.Types.NULL);
					statement.setString(5, procedure.get());
					statement.setString(6, outcome.get());
					statement.setInt(7, duration.get());
				}
			} else if(procedure.isPresent() || outcome.isPresent() || duration.isPresent()) {
				return OPERATION_OUTCOME.WRONG_INSERTION;
			} else {
				statement.setString(4, therapy.get());
				statement.setNull(5, java.sql.Types.NULL);
				statement.setNull(6, java.sql.Types.NULL);
				statement.setNull(7, java.sql.Types.NULL);
			}
			
			statement.setDate(1, new java.sql.Date(emissionDate.getTime()));
			statement.setString(2, description);
			statement.setString(3, type);
			statement.setInt(8, hospitalCode);
			statement.setString(9, patientCF);
			
			
			statement.executeUpdate();
			
			var rs = statement.getGeneratedKeys();
			rs.next();
			//TODO test if it works
			var reportCode = rs.getInt(1);
			
			//TODO return false if cannot insert involvement or make the user select the doctor/doctors
			for (String doctor : doctorCF) {
				insertInvolvement(reportCode, doctor);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertRoom(int hospitalCode, int roomNumber) {
		if(checkNulls(List.of(hospitalCode, roomNumber))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.ROOM.get() + "(Codice_ospedale, Numero) VALUES(?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, hospitalCode);
			statement.setInt(2, roomNumber);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
		if(checkNulls(List.of(hospitalCode, name, capacity, seatsOccupied))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = INSERT_SENTENCE + TABLES.UO.get() + "(Codice_ospedale, Nome, Capienza, Posti_occupati) VALUES(?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, hospitalCode);
			statement.setString(2, name);
			statement.setInt(3, capacity);
			statement.setInt(4, seatsOccupied);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}
	
	@Override
	public OPERATION_OUTCOME insertWorking(String CF, String unitName, int hospitalCode) {
		if(checkNulls(List.of(CF, unitName, hospitalCode))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = INSERT_SENTENCE + TABLES.WORKING.get() + "(Codice_ospedale, Nome_unita, Codice_fiscale) VALUES(?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, hospitalCode);
			statement.setString(2, unitName);
			statement.setString(3, CF);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return OPERATION_OUTCOME.SUCCESS;
	}
	
	private OPERATION_OUTCOME insertPresence(String doctor, int hospitalCode, int roomNumber, Timestamp date) {
		if(checkNulls(List.of(doctor, hospitalCode, roomNumber, date))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;			
		}
		String query = INSERT_SENTENCE + TABLES.PRESENCE.get() + "(Medico, Codice_ospedale, Numero_sala, Data_ora) VALUES(?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, doctor);
			statement.setInt(2, hospitalCode);
			statement.setInt(3, roomNumber);
			statement.setTimestamp(4, date);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}
	
	private OPERATION_OUTCOME insertInvolvement(int reportCode, String doctor) {
		if(checkNulls(List.of(reportCode, doctor))) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;			
		}
		String query = INSERT_SENTENCE + TABLES.INVOLVEMENTS.get() + "(Referto, Medico) VALUES(?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setLong(1, reportCode);
			statement.setString(2, doctor);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}
	
	private boolean checkNulls(final Collection<Object> args) {
		for (Object object : args) {
			if(Objects.isNull(object)) {
				return true;
			}
		}
		return false;
	}

}
