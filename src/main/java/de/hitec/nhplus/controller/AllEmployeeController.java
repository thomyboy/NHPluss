package de.hitec.nhplus.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.SQLException;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.EmployeeDao;
import de.hitec.nhplus.model.Employee;

/**
 * The <code>AllEmployeeController</code> contains the entire logic of the employee view.
 * It determines which data is displayed and how to react to events.
 */
public class AllEmployeeController {

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, Long> columnID;

    @FXML
    private TableColumn<Employee, String> columnName;

    @FXML
    private TableColumn<Employee, String> columnSurname;

    @FXML
   private TableColumn<Employee, String> columnRole;

    @FXML
    private TableColumn<Employee, String> columnStatus;

    @FXML
    private TableColumn<Employee, String> columnPhoneNumber;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldRole;

    @FXML
    private TextField textFieldStatus;
    @FXML
    private TextField textFieldPhoneNumber;

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();
    private EmployeeDao dao;

    private static final String EMPTY = "9999";

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the table view, columns, and listeners for user interaction.
     */

    public void initialize() {
        this.readAllAndShowInTableView();

        this.columnID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        this.columnName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.columnName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.columnRole.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        this.columnStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.columnStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.employees);

        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observableValue, Employee oldEmployee, Employee newEmployee) {
                AllEmployeeController.this.buttonDelete.setDisable(newEmployee == null);
            }
        });

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewEmployeeListener = (observableValue, oldText, newText) ->
                AllEmployeeController.this.buttonAdd.setDisable(!AllEmployeeController.this.areInputDataValid());
        this.textFieldName.textProperty().addListener(inputNewEmployeeListener);
        this.textFieldSurname.textProperty().addListener(inputNewEmployeeListener);
        this.textFieldRole.textProperty().addListener(inputNewEmployeeListener);
        this.textFieldStatus.textProperty().addListener(inputNewEmployeeListener);
        this.textFieldPhoneNumber.textProperty().addListener(inputNewEmployeeListener);
    }
    /**
     * Handles editing of the employee's first name directly in the table view.
     *
     * @param event the edit event triggered by changing the first name
     */
    @FXML
    public void handleOnEditName(TableColumn.CellEditEvent<Employee, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }
    /**
     * Handles editing of the employee's surname directly in the table view.
     *
     * @param event the edit event triggered by changing the surname
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Employee, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }
    /**
     * Handles editing of the employee's role directly in the table view.
     *
     * @param event the edit event triggered by changing the role
     */
    @FXML
    public void handleOnEditRole(TableColumn.CellEditEvent<Employee, String> event) {
        event.getRowValue().setrole(event.getNewValue());
        this.doUpdate(event);
    }
    /**
     * Handles editing of the employee's status directly in the table view.
     *
     * @param event the edit event triggered by changing the status
     */
    @FXML
    public void handleOnEditStatus(TableColumn.CellEditEvent<Employee, String> event) {
        event.getRowValue().setStatus(event.getNewValue());
        this.doUpdate(event);
    }
    /**
     * Updates the employee information in the data source.
     *
     * @param event the edit event containing the new data
     */
    private void doUpdate(TableColumn.CellEditEvent<Employee, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Reads all employees from the data source and displays them in the table view.
     */
    private void readAllAndShowInTableView() {
        this.employees.clear();
        this.dao = DaoFactory.getDaoFactory().createEmployeeDAO();
        try {
            this.employees.addAll(this.dao.readAll());
            String mystring = null;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Handles the deletion of the selected employee from the table view and the data source.
     */
    @FXML
    public void handleDelete() {
        Employee selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                DaoFactory.getDaoFactory().createEmployeeDAO().deleteById(selectedItem.getemployeeID());
                this.tableView.getItems().remove(selectedItem);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
    /**
     * Handles the addition of a new employee using the data entered in the text fields.
     */
    @FXML
    public void handleAdd() {
        String name =        this.textFieldName.        getText();
        String surname =     this.textFieldSurname.     getText();
        String role =        this.textFieldRole.        getText();
        String status =      this.textFieldStatus.      getText();
        String phonenumber = this.textFieldPhoneNumber. getText();
        try {
            this.dao.create(new Employee( name, surname, role, EMPTY, status, phonenumber)); // employeeID is auto-generated
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextFields();
    }
    /**
     * Clears the text fields after an operation.
     */
    private void clearTextFields() {
        this.textFieldName.clear();
        this.textFieldSurname.clear();
       this.textFieldRole.clear();
       this.textFieldStatus.clear();
       this.textFieldPhoneNumber.clear();
    }
    /**
     * Validates if the input data for adding a new employee is valid.
     *
     * @return true if all required text fields are not blank, false otherwise
     */
    private boolean areInputDataValid() {
        return !this.textFieldName.getText().isBlank() &&
                !this.textFieldSurname.getText().isBlank() &&
                !this.textFieldRole.getText().isBlank() &&
                !this.textFieldStatus.getText().isBlank() &&
                !this.textFieldPhoneNumber.getText().isBlank();
    }
}

