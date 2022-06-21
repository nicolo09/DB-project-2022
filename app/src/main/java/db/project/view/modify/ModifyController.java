package db.project.view.modify;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.OPERATION_OUTCOME;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class ModifyController {
	
	protected final static int CFLENGHT = 16;
	
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
	
	protected void showOutcome(final OPERATION_OUTCOME outcome) {
		
		Alert alert;
		
		switch(outcome) {
		
		case CAPACITY_REACHED: alert = new Alert(AlertType.ERROR, "Troppi pazienti in cura, capacità insufficiente");
			alert.showAndWait();
			break;
			
		case FAILURE: alert = new Alert(AlertType.ERROR, "Qualcosa è andato storto, verificare la connessione o se i dati sono corretti");
			alert.showAndWait();
			break;
			
		case MISSING_ARGUMENTS: alert = new Alert(AlertType.ERROR, "I dati inseriti sono errati o non sufficienti, riprovare dopo averli compilati");
			alert.showAndWait();
			break;
			
		case MISSING_PERSON: alert = new Alert(AlertType.ERROR, "Si sta cercando di operare su una persona non registrata" + System.getProperty("line.separator") 
			+ "verificare la correttezza del codice fiscale o compilare i dati mancanti per registrarla");
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
