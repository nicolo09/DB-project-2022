package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import db.project.model.TABLES;

public class DataInserterImpl implements DataInserter {
	
	private final static String INSERT_SENTENCE = "INSERT INTO ";
	
	private final Connection connection;
	
	
	public DataInserterImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean insertAmministratives(String CF, String role, int hospitalCode, Optional<String> name, Optional<String> lastName) {
		if(Objects.isNull(CF) || Objects.isNull(role) || Objects.isNull(hospitalCode)) {
			return false;
		}
		String controlQuery = "SELECT COUNT(*) FROM PERSONE WHERE Codice_fiscale LIKE '?'";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)) {
			controlStatement.setString(1, CF);
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt("total") > 0) {
				if(name.isEmpty() || lastName.isEmpty()) {
					return false;
				}
				this.insertPerson(CF, name.get(), lastName.get());
			}
			String query = INSERT_SENTENCE + TABLES.AMMINISTRATIVE.get() + "(Codice_fiscale, Ruolo, Codice_ospedale) VALUES('?', '?', ?)";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
			statement.setString(1, CF);
			statement.setString(2, role);
			statement.setInt(3, hospitalCode);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean insertAppointment(int hospitalCode, int roomNumber, Timestamp date, int duration,
			String type, String patientCF, Collection<String> doctorCF) {
		if(Objects.isNull(hospitalCode) || Objects.isNull(roomNumber) || Objects.isNull(date) || Objects.isNull(duration) 
				|| Objects.isNull(type) || Objects.isNull(patientCF) || Objects.isNull(doctorCF)){
			return false;
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
					return false;
				}
			}
			String query = INSERT_SENTENCE + TABLES.APPOINTMENT.get() + "(Codice_ospedale, Numero_sala, Data_ora, Durata, Tipo, Paziente) VALUES(?, ?, ?, ?, '?', '?')";
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
			return false;
		}
				
		
		return true;
	}

	@Override
	public boolean insertASL(int codeASL, String name, String city, String street, int streetNumber) {
		if(Objects.isNull(codeASL) || Objects.isNull(name) || Objects.isNull(city) || Objects.isNull(street) || Objects.isNull(streetNumber)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean insertCure(String patientCF, int hospitalCode, String unitName, Date ingressDate,
			Optional<Date> exitDate, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertEquipment(int hospitalCode, int inventoryCode, String name, Date lastMaintenance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertHealtcare(String CF, String Role, Optional<String> unitName, Optional<Integer> hospitalCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertHospital(int structureCode, String name, String city, String street, String streetNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertPatient(String CF, Date birthDay, Optional<Integer> codASL) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertPerson(String CF, String name, String lastName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertPhone(String phoneNumber, String personCF) {
		// TODO Auto-generated method stub
		return false;
	}

	//TODO remove report code from arguments
	@Override
	public boolean insertReport(int reportCode, Date emissionDate, String description, String type,
			Optional<String> therapy, Optional<String> procedure, Optional<String> outcome, Optional<Integer> duration,
			int hospitalCode, String patientCF, Collection<String> doctorCF) {
		
		if(checkNulls(List.of(reportCode, emissionDate, description, type, hospitalCode, patientCF, doctorCF))) {
			return false;
		}
		
		String query = INSERT_SENTENCE + TABLES.REPORT.get() + "(Data_emissione, Descrizione, Tipo, Terapia, Procedura, Esito, Durata, Codice_ospedale, Paziente) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			if(therapy.isEmpty()) {
				if(procedure.isEmpty() || outcome.isEmpty() || duration.isEmpty()) {				
					return false;
				} else {
					statement.setNull(4, java.sql.Types.NULL);
					statement.setString(5, procedure.get());
					statement.setString(6, outcome.get());
					statement.setInt(7, duration.get());
				}
			} else if(procedure.isPresent() || outcome.isPresent() || duration.isPresent()) {
				return false;
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
			
			//TODO return false if cannot insert involvement or make the user select the doctor/doctors
			//TODO how to get the report code ?
			for (String doctor : doctorCF) {
				insertInvolvement(reportCode, doctor);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean insertRoom(int hospitalCode, int roomNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertUO(int hospitalCode, String name, int capacity, int seatsOccupied) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean insertPresence(String doctor, int hospitalCode, int roomNumber, Timestamp date) {
		//TODO
		return false;
	}
	
	private boolean insertInvolvement(int reportCode, String doctor) {
		if(checkNulls(List.of(reportCode, doctor))) {
			return false;			
		}
		String query = INSERT_SENTENCE + TABLES.INVOLVEMENTS.get() + "(Referto, Medico) VALUES(?, ?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, reportCode);
			statement.setString(2, doctor);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
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
