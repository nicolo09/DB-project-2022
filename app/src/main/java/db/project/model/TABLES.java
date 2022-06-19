package db.project.model;

public enum TABLES {
	
	AMMINISTRATIVE("amministrativi"),
	APPOINTMENT("appuntamenti"),
	ASL("asl"),
	CURE("cure"),
	EQUIPMENT("attrezzature"),
	HEALTHCARE("personale_sanitario"),
	HOSPITAL("ospedali"),
	INVOLVEMENTS("coinvolgimenti"),
	PATIENT("pazienti"),
	PERSON("persone"),
	PHONE("telefoni"),
	PRESENCE("presenzia"),
	REPORT("referti"),
	ROOM("sale"),
	UO("unita_operative"),
	WORKING("lavora");
	
	private final String name;
	
	private TABLES(String name) {
		this.name = name;
	}
	
	public String get() {
		return this.name;
	}

}
