package db.project.view.search;

import db.project.model.ASL;
import db.project.model.Hospital;
import db.project.model.Person;
import db.project.model.Uo;

public interface SearchMainView {

    void goToASL();

    void goToAppuntamenti();

    void goToOspedali();

    void goToPazienti();

    void goToPersonaleAmministrativo();

    void goToPersonaleSanitario();

    void goToReferti();

    void goToRicoveri();

    void goToUnitaOperative();

    void goToMainMenu();

    void show();

    Person selectPerson();
    
    Hospital selectHospital();

    ASL selectAsl();

    void showError(String errorMessage);

    Uo selectUo();

    void goToImpieghi();

}
