package db.project.view.search;

import db.project.model.Person;

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

}