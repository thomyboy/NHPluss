package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.EmployeeDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Employee;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelSurname;

    @FXML
    private TextField textFieldBegin;

    @FXML
    private TextField textFieldEnd;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextArea textAreaRemarks;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> comboBoxEmployeeSelection;

    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;
    private Employee assignedEmployee;
    private EmployeeDao dao;
    private String employeeFullName;
    private ArrayList<Employee> employeeList;
    private final ObservableList<String> employeeSelection = FXCollections.observableArrayList();
    private final ObservableList<Treatment> treatments = FXCollections.observableArrayList();




    public void initialize(AllTreatmentController controller, Stage stage, Patient patient, String employeeFullName) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;
        this.employeeFullName = employeeFullName;
        comboBoxEmployeeSelection.setItems(employeeSelection);
        comboBoxEmployeeSelection.getSelectionModel().select(0);

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        ChangeListener<String> inputNewEmployeeListener = (observableValue, oldText, newText) ->
                NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        this.textFieldBegin.textProperty().addListener(inputNewPatientListener);
        this.textFieldEnd.textProperty().addListener(inputNewPatientListener);
        this.textFieldDescription.textProperty().addListener(inputNewPatientListener);
        this.textAreaRemarks.textProperty().addListener(inputNewPatientListener);
        this.datePicker.valueProperty().addListener((observableValue, localDate, t1) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid()));
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate == null) ? "" : DateConverter.convertLocalDateToString(localDate);
            }

            @Override
            public LocalDate fromString(String localDate) {
                return DateConverter.convertStringToLocalDate(localDate);
            }
        });
        this.showPatientData();
        this.createComboBoxData();
    }

    private void showPatientData(){
        this.labelFirstName.setText(patient.getFirstName());
        this.labelSurname.setText(patient.getSurname());
    }

    @FXML
    public void handleAdd(){
        LocalDate date = this.datePicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText());
        String description = textFieldDescription.getText();
        String remarks = textAreaRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), date, begin, end, description, remarks, patient, assignedEmployee, "in Bearbeitung");
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.create(treatment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



    private void createComboBoxData() {
        EmployeeDao dao = DaoFactory.getDaoFactory().createEmployeeDAO();
        try {
            employeeList = (ArrayList<Employee>) dao.readAll();
            for (Employee employee: employeeList) {
                if (employee.getstatus().toLowerCase().equals("aktiv")){
                    this.employeeSelection.add(employee.getFullName());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @FXML
    public void handleComboBox() {
        String selectedEmployee = this.comboBoxEmployeeSelection.getSelectionModel().getSelectedItem();
        this.dao = DaoFactory.getDaoFactory().createEmployeeDAO();
//
//        if (selectedEmployee.equals("alle")) {
//            try {
//                this.treatments.addAll(this.dao.readAll());
//            } catch (SQLException exception) {
//                exception.printStackTrace();
//            }
//        }
//
        Employee employee = searchInList(selectedEmployee);
        assignedEmployee = employee;
//        if (employee !=null) {
//            try {
//                this.treatments.addAll(this.dao.readTreatmentsByPid(patient.getPid()));
//            } catch (SQLException exception) {
//                exception.printStackTrace();
//            }
//        }
    }

    private Employee searchInList(String employeeFullName) {
        for (Employee employee : this.employeeList) {
            if (employee.getFullName().equals(employeeFullName)) {
                return employee;
            }
        }
        return null;
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }

    private boolean areInputDataInvalid() {
        if (this.textFieldBegin.getText() == null || this.textFieldEnd.getText() == null) {
            return true;
        }
        try {
            LocalTime begin = DateConverter.convertStringToLocalTime(this.textFieldBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(this.textFieldEnd.getText());
            if (!end.isAfter(begin)) {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
        return this.textFieldDescription.getText().isBlank() || this.datePicker.getValue() == null;
    }
}