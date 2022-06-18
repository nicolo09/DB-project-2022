package db.project.model.mysql;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class DataUpdaterImpl implements DataUpdater {

	@Override
	public boolean updateAmministratives(String CF, Optional<String> Role, Optional<Integer> hospitalCode) {
		// TODO Auto-generated method stub
		return false;
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
