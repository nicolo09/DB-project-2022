package db.project.view.search.referti;



import java.util.function.Consumer;

import db.project.Command;
import db.project.controller.Controller;
import db.project.model.Report;
import db.project.view.search.Selector;
import javafx.fxml.FXML;

public class SelectReportsControllerImpl extends SearchRefertiControllerImpl {

    private final Command onSelect;

    public SelectReportsControllerImpl(final Controller mainController, final Command onSelectOrExit, final Selector selector, Consumer<String> errorReporter) {
        super(mainController, selector, onSelectOrExit, errorReporter);
        this.onSelect = onSelectOrExit;
    }

    @FXML
    protected void onSelect() {
        onSelect.execute();
    }

    public Report getSelectedReport() {
        return this.refertiTableView.getSelectionModel().getSelectedItem();
    }

}
