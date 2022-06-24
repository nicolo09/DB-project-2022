package db.project.model.mysql;

public interface HospitalDeletionCounter {
	
	void setHospital(int hospitalCode);
	
	int countDeletedEquipments();
	
	int countDeletedAmministratives();
	
	int countDeletedReports();
	
	int countDeletedRooms();
	
	int countDeletedAppointments();
	
	int countDeletedUOs();
	
	int countDeletedCures();
	
	int countDeletedJobs();

}
