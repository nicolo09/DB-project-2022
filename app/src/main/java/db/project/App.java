package db.project;

import java.util.Optional;

import db.project.controller.Controller;
import db.project.controller.ControllerImpl;
import db.project.model.Model;
import db.project.model.ModelImpl;
import db.project.utils.DatabaseCreator;
import db.project.view.LoginDialog;
import db.project.view.LoginDialogImpl;
import db.project.view.View;
import db.project.view.ViewImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;

public class App extends javafx.application.Application {

    private String dbUser = null;
    private String dbPass = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parameters parameters = getParameters();
        if (!parameters.getRaw().isEmpty()) {
            dbUser = parameters.getRaw().get(0);
            dbPass = parameters.getRaw().get(1);
        } else {
            while (dbUser == null || dbPass == null) {
                LoginDialog loginDialog = new LoginDialogImpl();
                Optional<Pair<String, String>> credentials = loginDialog.showAndWait();
                if (credentials.isPresent()) {
                    dbUser = credentials.get().getKey();
                    dbPass = credentials.get().getValue();
                } else {
                    Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.CLOSE);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Nessuna credenziale fornita");
                    alert.setContentText("Riprovare a effettuare il login?");
                    Optional<ButtonType> choose = alert.showAndWait();
                    if (!choose.isPresent() || choose.get() != ButtonType.OK) {
                        System.exit(0);
                    }
                }
            }
        }
        Model model = null;
        boolean exit = false;
        while (!exit) {
            try {
                model = new ModelImpl(dbUser, dbPass);
                exit = true;
            } catch (IllegalStateException e) {
                if (e.getCause().getMessage() != null && e.getCause().getMessage().contains("Unknown database")) {
                    Alert alert = new Alert(AlertType.WARNING,
                            "Il database \"hospital\" non è stato trovato, desideri crearlo?", ButtonType.YES,
                            ButtonType.NO);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Database non trovato");
                    Optional<ButtonType> choose = alert.showAndWait();
                    if (choose.get() == ButtonType.YES) {
                        DatabaseCreator creator = new DatabaseCreator(dbUser, dbPass);
                        creator.createDatabase();
                    } else {
                        System.exit(1);
                    }
                } else {
                    Alert al = new Alert(AlertType.ERROR,
                            "Impossibile connettersi al DB. Controllare le credenziali inserite e riprovare.");
                    al.showAndWait();
                    System.exit(1);
                }
            }
        }
        final Controller controller = new ControllerImpl(model);
        final View view = new ViewImpl(controller, primaryStage);
        view.goToMainMenu();
    }

}
