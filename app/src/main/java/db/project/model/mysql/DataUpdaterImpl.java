package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.model.OPERATION_OUTCOME;
import db.project.model.TABLES;

public class DataUpdaterImpl implements DataUpdater {
	
	private final static int INVALID_INT = -1;
	
	private final Connection connection;
	
	public DataUpdaterImpl(final Connection connection) {
		this.connection = connection;
	}

	@Override
	public OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode) {
		if(checkNulls(CF) || checkModifies(role, hospitalCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		var check = checkPerson(CF, TABLES.AMMINISTRATIVE);
		if(!check.equals(OPERATION_OUTCOME.SUCCESS)) {
			return check;
		}
		
		String query = "UPDATE " + TABLES.AMMINISTRATIVE.get() + " SET";
		query += role.isPresent() ? " Ruolo = '" + role.get() + "'," : "";
		query += hospitalCode.isPresent() ? " Codice_ospedale = " + hospitalCode.get() + "," : "";
		query = query.substring(0, query.length() - 1);
		query += " WHERE Codice_fiscale LIKE ?";
		
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			statement.setString(1, CF);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME updateASL(int codeASL, Optional<String> name, Optional<String> city, Optional<String> street,
			Optional<Integer> streetNumber) {
		if(checkNulls(codeASL) || checkModifies(name, city, street, streetNumber)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "UPDATE " + TABLES.ASL.get() + " SET";
		
		if(city.isPresent() && street.isPresent() && streetNumber.isPresent()) {
			query += " Ind_Citta = '" + city.get() + "',  Ind_Via = '" + street.get() + "', Ind_Numero_civico = " + streetNumber + ",";
		} else if(!(city.isEmpty() && street.isEmpty() && streetNumber.isEmpty())) {
			return OPERATION_OUTCOME.WRONG_INSERTION;
		}
		
		query += name.isPresent() ? " Nome = '" + name.get() + "'," : "";
		query = query.substring(0, query.length() - 1);
		query += " WHERE Codice LIKE ?";
		
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
	public OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Date ingressDate, Optional<Date> exitDate, Optional<String> description) {
		if(checkNulls(patientCF, hospitalCode, unitName) || checkModifies(exitDate, description)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		if(exitDate.isPresent() && exitDate.get().compareTo(ingressDate) < 0) {
			return OPERATION_OUTCOME.WRONG_INSERTION;
		}
		
		String controlQuery = "SELECT * FROM " + TABLES.CURE.get() + " WHERE Paziente LIKE ? AND Codice_ospedale LIKE ? AND Nome_unita LIKE ? "
				+ "AND Data_ingresso LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
		
			controlStatement.setString(1, patientCF);
			controlStatement.setInt(2, hospitalCode);
			controlStatement.setString(3, unitName);
			controlStatement.setDate(4, new java.sql.Date(ingressDate.getTime()));
			
			var rs = controlStatement.executeQuery();
			if(rs.next()){
				if(rs.getDate("Data_uscita") != null) {
					return OPERATION_OUTCOME.EXIT_DATE_PRESENT;
				}
			} else {
				return OPERATION_OUTCOME.FAILURE;
			}
		
			String query = "UPDATE " + TABLES.CURE.get() + ", " + TABLES.UO.get() + " SET";
			query += exitDate.isPresent() ? " " + TABLES.CURE.get() + ".Data_uscita = '" + new java.sql.Date(exitDate.get().getTime()) + "', " 
					+ TABLES.UO.get() + ".Posti_occupati = " + TABLES.UO.get() + ".Posti_occupati-1," : "";
			query += description.isPresent() ? " " + TABLES.CURE.get() + ".Motivazione = '" + description.get() + "'," : "";
			query = query.substring(0, query.length() - 1);
			query += " WHERE " +  TABLES.CURE.get() + ".Paziente LIKE ? AND " +  TABLES.CURE.get() + ".Codice_ospedale LIKE ? AND " +  TABLES.CURE.get() +".Nome_unita LIKE ? "
					+ "AND " + TABLES.CURE.get() + ".Data_ingresso LIKE ?";
		
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
	public OPERATION_OUTCOME updateEquipment(int hospitalCode, int inventoryCode, Optional<Date> lastMaintenance) {
		if(checkNulls(hospitalCode, inventoryCode) || checkModifies(lastMaintenance)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String controlQuery = "SELECT * FROM " + TABLES.EQUIPMENT.get() + " WHERE Codice_ospedale LIKE ? AND Codice_inventario LIKE ?";
		try(final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			controlStatement.setInt(1, hospitalCode);
			controlStatement.setInt(2, inventoryCode);
			
			var rs = controlStatement.executeQuery();
			var maintanance = new java.sql.Date(lastMaintenance.get().getTime());
			rs.next();
			if(rs.getDate("Data_manutenzione").compareTo(maintanance) < 0) {
				return OPERATION_OUTCOME.WRONG_INSERTION;
			}
		
			String query = "UPDATE " + TABLES.EQUIPMENT.get() + " SET "
					+ "Data_manutenzione = '" + maintanance.toString()
					+ "' WHERE Codice_ospedale LIKE ? AND Codice_inventario LIKE ?";
		
			final PreparedStatement statement = this.connection.prepareStatement(query);
			
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
	public OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role) {
		if(checkNulls(CF) || checkModifies(role)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		var check = checkPerson(CF, TABLES.HEALTHCARE);
		if(!check.equals(OPERATION_OUTCOME.SUCCESS)) {
			return check;
		}
		
		String query = "UPDATE " + TABLES.HEALTHCARE.get() + " SET";
		query += " Ruolo = '" + role.get() + "'";
		query += " WHERE Codice_fiscale LIKE ?";
		
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, CF);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME updateHospital(int structureCode, Optional<String> name, Optional<Integer> codeASL) {
		if(checkNulls(structureCode) || checkModifies(name, codeASL)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "UPDATE " + TABLES.HOSPITAL.get() + " SET";
		query += name.isPresent() ? " Nome = '" + name.get() + "'," : "";
		query += codeASL.isPresent() ? " Cod_ASL = " + codeASL.get() + "," : "";
		query = query.substring(0, query.length() - 1);
		query += " WHERE Codice_struttura LIKE ?";
		
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
	public OPERATION_OUTCOME updatePatient(String CF, Optional<Integer> codASL) {
		if(checkNulls(CF)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		var check = checkPerson(CF, TABLES.PATIENT);
		if(!check.equals(OPERATION_OUTCOME.SUCCESS)) {
			return check;
		}
		
		String query = "UPDATE " + TABLES.PATIENT.get() + " SET";
		query += codASL.isPresent() ? " Cod_ASL = " + codASL.get() : " Cod_ASL = NULL ";
		query += " WHERE Codice_fiscale LIKE ?";
		
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, CF);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
		return OPERATION_OUTCOME.SUCCESS;
	}

	@Override
	public OPERATION_OUTCOME updateUO(int hospitalCode, String name, Optional<Integer> capacity) {
		if(checkNulls(hospitalCode, name) || checkModifies(capacity)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String controlQuery = "SELECT * FROM " + TABLES.UO.get() + " WHERE Codice_ospedale LIKE ? AND Nome LIKE ?";
		try (final PreparedStatement controlStatement = this.connection.prepareStatement(controlQuery)){
			
			controlStatement.setInt(1, hospitalCode);
			controlStatement.setString(2, name);
			
			var rs = controlStatement.executeQuery();
			if(rs.next()) {
				if(capacity.get() < rs.getInt("Posti_occupati")) {
					return OPERATION_OUTCOME.CAPACITY_REACHED;
				}
			} else {
				return OPERATION_OUTCOME.FAILURE;
			}
		
			String query = "UPDATE " + TABLES.UO.get() + " SET"
					+ " Capienza = " + capacity.get() + " WHERE Codice_ospedale LIKE ? AND Nome LIKE ?";
		
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
	
	private OPERATION_OUTCOME checkPerson(String CF, TABLES table) {
		
		String query = "SELECT COUNT(*) FROM " + table.get() + " WHERE Codice_fiscale LIKE ?";
		try(final PreparedStatement controlStatement = this.connection.prepareStatement(query)){
			controlStatement.setString(1, CF);
			
			var rs = controlStatement.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0) {
				return OPERATION_OUTCOME.WRONG_INSERTION;
			} else {
				return OPERATION_OUTCOME.SUCCESS;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return OPERATION_OUTCOME.FAILURE;
		}
		
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
	
	@SafeVarargs
	private boolean checkModifies(Optional<? extends Object> ... args) {
		int count = 0;
		for (Optional<? extends Object> optional : args) {
			count += optional.isEmpty() ? 1 : 0;
		}
		return args.length == count;
	}
	
	

}
