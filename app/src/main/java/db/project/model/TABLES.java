package db.project.model;

public enum TABLES {
	
	AMMINISTRATIVE("amministrativi"),
	APPOINTMENT("appuntamenti"),
	ASL("ASL"),
	CURE("cure"),
	EQUIPMENT("attrezzature"),
	HEALTHCARE("personale sanitario"),
	HOSPITAL("ospedali"),
	PATIENT("pazienti"),
	PERSON("persone"),
	PHONE("telefoni"),
	PRESENCE("presenzia"),
	REPORT("referti"),
	ROOM("sale"),
	UO("unita operative");
	
	private final String name;
	
	private TABLES(String name) {
		this.name = name;
	}
	
	public String get() {
		return this.name;
	}

}
