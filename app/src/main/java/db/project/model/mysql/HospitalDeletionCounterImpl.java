package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import db.project.model.TABLES;

public class HospitalDeletionCounterImpl implements HospitalDeletionCounter {

	private final static String COUNT = "SELECT COUNT(*) FROM ";
	private final static String WHERE_SENTENCE = " WHERE Codice_ospedale LIKE ?";
	
	private final Connection connection;
	private Optional<Integer> hospitalCode;
	
	public HospitalDeletionCounterImpl(final Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void setHospital(final int hospitalCode) {
		this.hospitalCode = Optional.ofNullable(hospitalCode);
	}
	
	@Override
	public int countDeletedEquipments() {
		return genericCounter(TABLES.EQUIPMENT);
	}

	@Override
	public int countDeletedAmministratives() {
		return genericCounter(TABLES.AMMINISTRATIVE);
	}

	@Override
	public int countDeletedReports() {
		return genericCounter(TABLES.REPORT);
	}

	@Override
	public int countDeletedRooms() {
		return genericCounter(TABLES.ROOM);
	}

	@Override
	public int countDeletedAppointments() {
		return genericCounter(TABLES.APPOINTMENT);
	}

	@Override
	public int countDeletedUOs() {
		return genericCounter(TABLES.UO);
	}

	@Override
	public int countDeletedCures() {
		return genericCounter(TABLES.CURE);
	}

	@Override
	public int countDeletedJobs() {
		return genericCounter(TABLES.WORKING);
	}
	
	private int genericCounter(final TABLES table) {
		
		if(hospitalCode.isEmpty()) {
			return -1;
		} else if(!table.equals(TABLES.HOSPITAL) && genericCounter(TABLES.HOSPITAL) <= 0) {
			return -1;
		}
		
		int count;
		String query = !table.equals(TABLES.HOSPITAL) 
				? COUNT + table.get() + WHERE_SENTENCE 
				: COUNT + table.get() + " WHERE Codice_struttura LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, this.hospitalCode.get());
			
			var rs = statement.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return count;
	}

}
