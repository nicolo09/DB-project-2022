package db.project.view;

import java.util.Optional;

import javafx.util.Pair;

public interface LoginDialog {
    
    Optional<Pair<String, String>> showAndWait();

}
