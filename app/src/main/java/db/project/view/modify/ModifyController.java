package db.project.view.modify;

import java.util.regex.Pattern;
import db.project.Command;
import db.project.controller.Controller;
import db.project.model.OPERATION_OUTCOME;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Alert.AlertType;

public abstract class ModifyController {
	
	protected final static int CFLENGHT = 16;
	
	protected final static String SIMPLE_FORMATTER = "[a-z_A-Z_\\ ]*";
	protected final static String NUMBER_FORMATTER = "[0-9]*";
	protected final static String CF_FORMATTER = "[a-z_A-Z_0-9]*";
	protected final static String DOCTORS_FORMATTER = "[a-z_A-Z_0-9_:]*";
	protected final static String COMPLETE_FORMATTER = ".*";
	
	private final Command exit;
	protected final Controller mainController;
	
	public ModifyController(final Command exit, final Controller mainController) {
		this.exit = exit;
		this.mainController = mainController;
	}
	
	@FXML
	protected void goBack() {
		exit.execute();
	}
	
	protected abstract void addElement();
	
	protected abstract void removeElement();
	
	protected void updateElement() {
		//updatable elements must override this method
	}
	
	protected boolean isInteger(final String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	//TODO add doctorscf to always upper
	protected void setTextFormatter(final TextInputControl txt, final String pattern){
		var p = Pattern.compile(pattern);

		TextFormatter<String> formatter = new TextFormatter<>((change) -> {
			if (p.matcher(change.getControlNewText()).matches() 
					&& !(pattern.equals(CF_FORMATTER) && change.getControlNewText().length() > CFLENGHT)) {
				if(change.getControlNewText().length() == 1 || pattern.equals(CF_FORMATTER)) {
					change.setText(change.getText().toUpperCase());
				}
				return change;
		    } else {
		        return null ;
		    }
		});
		txt.setTextFormatter(formatter);
		
	}
	
	protected void showOutcome(final OPERATION_OUTCOME outcome) {
		
		Alert alert;
		switch(outcome) {
			
		case CAPACITY_REACHED: alert = new Alert(AlertType.ERROR, "Troppi pazienti in cura, capacità insufficiente");
			alert.showAndWait();
			break;
		
		case EXIT_DATE_MISSING: alert = new Alert(AlertType.ERROR, "Impossibile rimuovere la cura selezionata, impostare una data di uscita prima di riprovare");
			alert.showAndWait();
			break;
			
		case EXIT_DATE_PRESENT: alert = new Alert(AlertType.ERROR, "Impossibile aggiornare la cura selezionata, la data di uscita è già stata registrata");
			alert.showAndWait();
			break;
			
		case FAILURE: alert = new Alert(AlertType.ERROR, "Qualcosa è andato storto, verificare la connessione o se i dati inseriti sono corretti");
			alert.showAndWait();
			break;
		
		case MISSING_ARGUMENTS: alert = new Alert(AlertType.ERROR, "I dati inseriti sono errati o non sufficienti, riprovare dopo averli compilati");
			alert.showAndWait();
			break;
		
		case MISSING_PERSON: alert = new Alert(AlertType.ERROR, "Si sta cercando di operare su una persona non registrata" + System.getProperty("line.separator") 
			+ "verificare la correttezza del codice fiscale o compilare i dati mancanti per registrarla");
			alert.showAndWait();
			break;
		
		case PENDING_APPOINTMENTS: alert = new Alert(AlertType.ERROR, "Impossibile eseguire la rimozione, sono presenti ancora appuntamenti associate a questa entità");
			alert.showAndWait();
			break;
			
		case PENDING_CURES: alert = new Alert(AlertType.ERROR, "Impossibile eseguire la rimozione, sono presenti ancora cure associate a questa entità");
			alert.showAndWait();
			break;
		
		case OVERLAPPING: alert = new Alert(AlertType.ERROR, "È già presente un appuntamento nell'orario richiesto, scegliere un'altra data o orario");
			alert.showAndWait();
			break;
		
		case SUCCESS: alert = new Alert(AlertType.INFORMATION, "Operazione terminata con successo");
			alert.showAndWait();
			break;
		
		case WRONG_INSERTION: alert = new Alert(AlertType.ERROR, "I dati inseriti hanno causato un conflitto," + System.getProperty("line.separator")  
			+ " verificare che siano stati rispettati i vincoli riportati nella finestra di modifica");
			alert.showAndWait();
			break;
			
		default:
			break;
		
		
		}
		
	}

}
