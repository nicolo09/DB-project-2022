package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import db.project.model.TABLES;

public class DataUpdaterImpl implements DataUpdater {
	
	private final Connection connection;
	
	public DataUpdaterImpl(final Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode) {
		if(role.isEmpty() && hospitalCode.isEmpty()) {
			return false;
		}
		
		String query = "UPDATE " + TABLES.AMMINISTRATIVE.get() + " SET";
		query += role.isPresent() ? " Ruolo = '" + role.get() + "'," : "";
		query += hospitalCode.isPresent() ? " Codice_ospedale = " + hospitalCode.get() + "," : "";
		query = query.substring(0, query.length() - 1);
		query += "WHERE Codice_fiscale = ?";
		
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setString(1, CF);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean updateAppointment(int hospitalCode, int roomNumber, Timestamp date, Optional<Integer> duration,
			Optional<String> type, Optional<String> patientCF) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateASL(int codeASL, Optional<String> name, Optional<String> city, Optional<String> street,
			Optional<Integer> streetNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCure(String patientCF, int hospitalCode, String unitName, Optional<Date> ingressDate,
			Optional<Date> exitDate, Optional<String> description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEquipment(int hospitalCode, int inventoryCode, Optional<String> name,
			Optional<Date> lastMaintenance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateHealtcare(String CF, Optional<String> role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateHospital(int structureCode, Optional<String> name, Optional<String> city,
			Optional<String> street, Optional<Integer> streetNumber, Optional<Integer> codeASL) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePatient(String CF, Optional<Date> birthDay, Optional<Integer> codASL) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUO(int hospitalCode, String name, Optional<Integer> capacity,
			Optional<Integer> seatsOccupied) {
		// TODO Auto-generated method stub
		return false;
	}

}
