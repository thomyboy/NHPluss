package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.util.Objects;
import de.hitec.nhplus.datastorage.DaoFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TextField txtField_userName;
    @FXML
    private PasswordField txtField_userPassword;

    @FXML
    private VBox vBox_Left;
    @FXML
    private Label lbl_PasswordInformation;
    @FXML
    private GridPane gp_Login;


    private UserDao dao;
    public void initialize() {
        this.dao = DaoFactory.getDaoFactory().createUserDAO();
        /*Button deaktiviern*/
        vBox_Left.setVisible(false);
        lbl_PasswordInformation.setVisible(false);
    }
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
//        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/EmployeesView.fxml"));
        try{
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    public void handleTryLogin() throws SQLException {

        String userNameInput = this.txtField_userName.getText();
        String userPasswordInput = this.txtField_userPassword.getText();
        String userPassword = this.dao.executeGetPassword(userNameInput).getUserPassword();
        System.out.println("Benutzername Input: " + userNameInput);
        System.out.println("Benutzerpasswort Input: " + userPasswordInput);
        System.out.println("Benutzerpasswort DB: " + userPassword);

        if(Objects.equals(userPassword, userPasswordInput)){
            System.out.println("Passwort richtig");
            vBox_Left.setVisible(true);
            gp_Login.setVisible(false);
        } else {
            if(userPassword == null){
                System.out.println("Unbekannter Benutzername");
                lbl_PasswordInformation.setText("Unbekannter Benutzername");
                lbl_PasswordInformation.setVisible(true);

            } else {
                System.out.println("Falsches Passwort");
                lbl_PasswordInformation.setText("Falsches Passwort");
                lbl_PasswordInformation.setVisible(true);
            }
        }


        //.out.println(this.dao.getPasswordFromUsername(this.txtField_userName.getText()));
        //System.out.println(this.dao.getReadByIDStatement(this.txtField_userName.getText()));
    }
}
