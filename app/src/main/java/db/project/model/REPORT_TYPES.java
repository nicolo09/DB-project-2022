package db.project.model;

public enum REPORT_TYPES {
    
    VISIT,
    
    SURGERY;

    public static REPORT_TYPES getReportType(String reportType) {
        switch (reportType) {
            case "Visita":
                return VISIT;
            case "Intervento":
                return SURGERY;
            default:
                return null;
        }
    }
}
