package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import db.project.model.OPERATION_OUTCOME;
import db.project.model.TABLES;

public class DataRemoverImpl implements DataRemover {

	private final static int INVALID_INT = -1;
	
	final Connection connection;
	
	public DataRemoverImpl(final Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public OPERATION_OUTCOME removeAmministratives(String CF) {
		return removePersonFromTable(CF, TABLES.AMMINISTRATIVE);
	}

	@Override
	public OPERATION_OUTCOME removeAppointment(int hospitalCode, int roomNumber, Timestamp date) {
		if(checkNulls(hospitalCode, roomNumber, date)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "DELETE FROM " + TABLES.APPOINTMENT.get() + " WHERE Codice_ospedale LIKE ? AND Numero_sala LIKE ? AND Data_ora LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, hospitalCode);
			statement.setInt(2, roomNumber);
			statement.setTimestamp(3, date);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeASL(int codeASL) {
		if(checkNulls(codeASL)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "DELETE FROM " + TABLES.ASL.get() + " WHERE Codice LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, codeASL);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeCure(String patientCF, int hospitalCode, String unitName, Date ingressDate) {
		if(checkNulls(patientCF, hospitalCode, unitName, ingressDate)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String controlQuery = "SELECT COUNT(*) FROM " + TABLES.CURE.get()+ " WHERE Paziente LIKE ? AND Codice_ospedale LIKE ? AND Nome_unita LIKE ?"
				+ " AND Data_ingresso LIKE ? AND Data_uscita LIKE NULL";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			
			controlStatement.setString(1, patientCF);
			controlStatement.setInt(2, hospitalCode);
			controlStatement.setString(3, unitName);
			controlStatement.setDate(4, new java.sql.Date(ingressDate.getTime()));
			
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) {
				return OPERATION_OUTCOME.EXIT_DATE_MISSING;
			}
		
			String query = "DELETE FROM " + TABLES.CURE.get() + " WHERE Paziente LIKE ? AND Codice_ospedale LIKE ? AND Nome_unita LIKE ? AND Data_ingresso LIKE ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, patientCF);
			statement.setInt(2, hospitalCode);
			statement.setString(3, unitName);
			statement.setDate(4, new java.sql.Date(ingressDate.getTime()));
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeEquipment(int hospitalCode, int inventoryCode) {
		if(checkNulls(hospitalCode, inventoryCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "DELETE FROM " + TABLES.EQUIPMENT.get() + " WHERE Codice_ospedale LIKE ? AND Codice_inventario LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, hospitalCode);
			statement.setInt(2, inventoryCode);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeHealtcare(String CF) {
		String controlAppointmentsQuery = "SELECT COUNT(*) FROM " + TABLES.PRESENCE.get() + " WHERE Medico LIKE ?";
		try (final PreparedStatement controlAppointmentStatement = this.connection.prepareStatement(controlAppointmentsQuery)){
			controlAppointmentStatement.setString(1, CF);

			var rs = controlAppointmentStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) {
				return OPERATION_OUTCOME.PENDING_APPOINTMENTS;
			}
			
			String controlReportsQuery = "SELECT COUNT(*) FROM " + TABLES.INVOLVEMENTS.get() + " WHERE Medico LIKE ?";
			final PreparedStatement controlReportsStatement = this.connection.prepareStatement(controlReportsQuery);
			controlReportsStatement.setString(1, CF);
			
			rs = controlReportsStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) {
				return OPERATION_OUTCOME.REPORT_INVOLVMENT;
			}
			
		} catch (SQLException e){
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		return removePersonFromTable(CF, TABLES.HEALTHCARE);
	}

	
	@Override
	public OPERATION_OUTCOME removeHospital(int structureCode) {
		if(checkNulls(structureCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "DELETE FROM " + TABLES.HOSPITAL.get() + " WHERE Codice_struttura LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, structureCode);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removePatient(String CF) {
		return removePersonFromTable(CF, TABLES.PATIENT);
	}

	@Override
	public OPERATION_OUTCOME removePerson(String CF) {
		return removePersonFromTable(CF, TABLES.PERSON);
	}

	@Override
	public OPERATION_OUTCOME removePhone(String phoneNumber, String personCF) {
		if(checkNulls(phoneNumber, personCF)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "DELETE FROM " + TABLES.PHONE.get() + " WHERE Telefono LIKE ? AND Persona LIKE ?";
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
	public OPERATION_OUTCOME removeReport(int reportCode) {
		if(checkNulls(reportCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "DELETE FROM " + TABLES.REPORT.get() + " WHERE Codice_referto LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setInt(1, reportCode);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeRoom(int hospitalCode, int roomNumber) {
		if(checkNulls(hospitalCode, roomNumber)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String controlQuery = "SELECT COUNT(*) FROM " + TABLES.APPOINTMENT.get() + " WHERE Codice_ospedale LIKE ? AND Numero_sala LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			controlStatement.setInt(1, hospitalCode);
			controlStatement.setInt(2, roomNumber);
			
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) {
				return OPERATION_OUTCOME.PENDING_APPOINTMENTS;
			}
		
			String query = "DELETE FROM " + TABLES.ROOM.get() + " WHERE Codice_ospedale LIKE ? AND Numero LIKE ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
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
	public OPERATION_OUTCOME removeUO(int hospitalCode, String name) {
		if(checkNulls(hospitalCode, name)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String controlQuery = "SELECT COUNT(*) FROM " + TABLES.CURE.get() + " WHERE Codice_ospedale LIKE ? AND Nome_unita LIKE ? AND Data_uscita LIKE NULL";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			controlStatement.setInt(1, hospitalCode);
			controlStatement.setString(2, name);
		
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0) {
				return OPERATION_OUTCOME.PENDING_CURES;
			}
		
			String query = "DELETE FROM " + TABLES.UO.get() + " WHERE Codice_ospedale LIKE ? AND Nome LIKE ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setInt(1, hospitalCode);
			statement.setString(2, name);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME removeWorking(String CF, String unitName, int hospitalCode) {
		if(checkNulls(hospitalCode, unitName, hospitalCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "DELETE FROM " + TABLES.WORKING.get() + " WHERE Codice_fiscale LIKE ? AND Nome_unita LIKE ? AND Codice_ospedale LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setString(1, CF);
			statement.setString(2, unitName);
			statement.setInt(3, hospitalCode);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}
	
	private boolean checkNulls(Object ... args) {
		for (Object object : args) {
			if(object instanceof Integer) {
				Integer value = (Integer) object;
				if(value.equals(INVALID_INT)) {
					return true;
				}
			}
			if(Objects.isNull(object)) {
				return true;
			}
		}
		return false;
	}
	
	private OPERATION_OUTCOME removePersonFromTable(final String CF, final TABLES table) {
		if(checkNulls(CF)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "DELETE FROM " + table.get() + " WHERE Codice_fiscale LIKE ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setString(1, CF);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

}
