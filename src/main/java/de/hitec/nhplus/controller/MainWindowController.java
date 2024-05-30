package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void handleShowAllPatient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    //hier soll bei Click auf den pflegekräfte-Button im Main Window auf das Pflegekräfte-Fenster geleitet werden (Inshallah)
    @FXML
    public void handleSetUpAllEmployees(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try{
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
