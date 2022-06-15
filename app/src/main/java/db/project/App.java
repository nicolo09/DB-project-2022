/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package db.project;

import db.project.view.View;
import db.project.view.ViewImpl;
import javafx.stage.Stage;

public class App extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final View view = new ViewImpl(primaryStage);
        view.goToMainMenu();
    }

}