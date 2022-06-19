package db.project.model.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.project.model.OPERATION_OUTCOME;
import db.project.model.TABLES;

public class DataUpdaterImpl implements DataUpdater {
	
	private final Connection connection;
	
	public DataUpdaterImpl(final Connection connection) {
		this.connection = connection;
	}

	@Override
	public OPERATION_OUTCOME updateAmministratives(String CF, Optional<String> role, Optional<Integer> hospitalCode) {
		if(checkNulls(CF) || checkModifies(role, hospitalCode)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
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
	public OPERATION_OUTCOME updateCure(String patientCF, int hospitalCode, String unitName, Optional<Date> exitDate, Optional<String> description) {
		if(checkNulls(patientCF, hospitalCode, unitName) || checkModifies(exitDate, description)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		
		String query = "UPDATE " + TABLES.CURE.get() + " SET";
		query += exitDate.isPresent() ? " Data_uscita = " + new java.sql.Date(exitDate.get().getTime()) + "," : "";
		query += description.isPresent() ? " Motivazione = '" + description.get() + "'," : "";
		query = query.substring(0, query.length() - 1);
		query += " WHERE Paziente LIKE ? AND Codice_ospedale LIKE ? AND Nome_unita LIKE ?";
		
		try (final PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, patientCF);
			statement.setInt(2, hospitalCode);
			statement.setString(3, unitName);
			
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
		
		String query = "UPDATE " + TABLES.EQUIPMENT.get() + " SET";
		query += " Data_manutenzione = " + new java.sql.Date(lastMaintenance.get().getTime());
		query += " WHERE Codice_ospedale LIKE ? AND Codice_inventario LIKE ?";
		
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
	public OPERATION_OUTCOME updateHealtcare(String CF, Optional<String> role) {
		if(checkNulls(CF) || checkModifies(role)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "UPDATE " + TABLES.HEALTHCARE.get() + " SET";
		query += " Ruole = '" + role.get() + "'";
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
		if(checkNulls(CF) || checkModifies(codASL)) {
			return OPERATION_OUTCOME.MISSING_ARGUMENTS;
		}
		String query = "UPDATE " + TABLES.PATIENT.get() + " SET";
		query += " Cod_ASL = " + codASL.get();
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
		
		String controlQuery = "SELECT * FROM " + TABLES.UO.get() + " WHERE Codice_ospedale LIKE ? AND Nome = ?";
		try (final Statement controlStatement = this.connection.createStatement()){
			
			var rs = controlStatement.executeQuery(controlQuery);
			if(capacity.get() <= rs.getInt("Posti_occupati")) {
				return OPERATION_OUTCOME.CAPACITY_REACHED;
			}
		
			String query = "UPDATE " + TABLES.UO.get() + " SET";
			query += " Capienza = " + capacity.get();
			query += " WHERE Codice_ospedale LIKE ? AND Nome LIKE ?";
		
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
	
	private boolean checkNulls(Object ... args) {
		for (Object object : args) {
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
