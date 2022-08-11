package db.project.view.search.referti;



import java.util.function.Consumer;

import db.project.controller.Controller;
import db.project.model.Report;
import db.project.utils.Command;
import db.project.view.search.Selector;
import javafx.fxml.FXML;

public class SelectReportsControllerImpl extends SearchReportsControllerImpl {

    private final Command onSelect;

    public SelectReportsControllerImpl(final Controller mainController, final Command onSelectOrExit, final Selector selector, Consumer<String> errorReporter, Consumer<Report> showReport) {
        super(mainController, selector, onSelectOrExit, errorReporter, showReport);
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
