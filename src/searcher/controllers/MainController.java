package searcher.controllers;

import common.domain.Graph;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import searcher.Utils;
import searcher.persistence.ExtendedPersistenceController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static searcher.Utils.closeWindow;


public class MainController extends BaseController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TabPane mainTabs;
    @FXML
    private CheckMenuItem terminalToggle;
    @FXML
    private Tab terminalSelector;

    @FXML
    private void backToLandingAction() throws Exception {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Estàs segur de tornar a la finestra principal?");
        alert.setContentText("Fent això, es descartaran els canvis no exportats.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/searcher/layouts/landing.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void handleTerminalToggle() {
        if (terminalToggle.isSelected()) {
            if (!mainTabs.getTabs().contains(terminalSelector)) {
                mainTabs.getTabs().add(terminalSelector);
            }
        } else {
            mainTabs.getTabs().remove(terminalSelector);
        }
    }

    @FXML
    private void closeWindowAction() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        closeWindow(stage);
    }

    @FXML
    private void launchHelpWindow() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/searcher/layouts/popups/help.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage secondStage = new Stage();
        secondStage.setTitle("Finestra d'ajuda");
        secondStage.setScene(new Scene(root));
        secondStage.show();
    }

    public MainController() {
        graph = new Graph();
        semanticPaths = FXCollections.observableArrayList();
        edgeTypes = FXCollections.observableArrayList(Utils.getDefaultRelations());
        pc = new ExtendedPersistenceController(graph, semanticPaths, edgeTypes);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleTerminalToggle();
    }

}
