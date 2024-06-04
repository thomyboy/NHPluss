package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.model.Room;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.DateConverter;
import de.hitec.nhplus.utils.LockingObjects;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController extends LockingObjects{

    private static final int TEN_YEARS = 10;
    private PatientDao dao;
    @FXML
    private TableView<Patient> tableView;

    @FXML
    private TableColumn<Patient, Long> columnId;

    @FXML
    private TableColumn<Patient, String> columnFirstName;

    @FXML
    private TableColumn<Patient, String> columnSurname;

    @FXML
    private TableColumn<Patient, String> columnDateOfBirth;

    @FXML
    private TableColumn<Patient, String> columnCareLevel;

    @FXML
    private TableColumn<Patient, String> columnRoomName;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldDateOfBirth;

    @FXML
    private TextField textFieldCareLevel;

    @FXML
    private ChoiceBox<Room> choiceBoxRoom;

    private final ObservableList<Patient> arrayPatients = FXCollections.observableArrayList();

    private static final String EMPTY = "9999";


    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize() {
        // Read all patients and display them in the TableView
        this.readAllAndShowInTableView();

        // Configure cell value factories for columns
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("patientID"));

        // CellValueFactory to show property values in TableView
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // CellFactory to write property values from with in the TableView
        this.columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        this.columnDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnCareLevel.setCellValueFactory(new PropertyValueFactory<>("careLevel"));
        this.columnCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        // Display the data in the TableView
        this.tableView.setItems(this.arrayPatients);

        // Disable delete button when no patient is selected in the TableView
        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
            @Override
            public void changed(ObservableValue<? extends Patient> observableValue, Patient oldPatient, Patient newPatient) {
                AllPatientController.this.buttonDelete.setDisable(newPatient == null);
            }
        });

        // Disable add button when input data is not valid
        this.buttonAdd.setDisable(true);
        ChangeListener<Object> inputNewPatientListener = (observableValue, oldText, newText) ->
                AllPatientController.this.buttonAdd.setDisable(!AllPatientController.this.areInputDataValid());
        this.textFieldSurname.textProperty().addListener(inputNewPatientListener);
        this.textFieldFirstName.textProperty().addListener(inputNewPatientListener);
        this.textFieldDateOfBirth.textProperty().addListener(inputNewPatientListener);
        this.textFieldCareLevel.textProperty().addListener(inputNewPatientListener);
        this.choiceBoxRoom.valueProperty().addListener(inputNewPatientListener);
    }

    /**
     * When a cell of the column with first names was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Checks if the selected patient is locked.
        // If the patient is locked, the edit operation is not allowed.
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            // If the patient is not locked, proceed with the first name edit.
            editFirstName(event);
        }
    }

    /**
     * Edits the first name of the selected patient in the table view.
     * This method is triggered by a cell edit event in the table view.
     *
     * @param event The cell edit event containing the patient and the new first name value.
     */
    private void editFirstName(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Update the first name of the patient with the new value from the event.
        event.getRowValue().setFirstName(event.getNewValue());

        // Perform the update operation for the patient in the database.
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with surnames was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Checks if the selected patient is locked.
        // If the patient is locked, the edit operation is not allowed.
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            // If the patient is not locked, proceed with the surname edit.
            editSurname(event);
        }
    }

    /**
     * Edits the surname of the selected patient in the table view.
     * This method is triggered by a cell edit event in the table view.
     *
     * @param event The cell edit event containing the patient and the new surname value.
     */
    private void editSurname(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Update the surname of the patient with the new value from the event.
        event.getRowValue().setSurname(event.getNewValue());

        // Perform the update operation for the patient in the database.
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with dates of birth was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Checks if the selected patient is locked.
        // If the patient is locked, the edit operation is not allowed.
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            // If the patient is not locked, proceed with the date of birth edit.
            editDateOfBirth(event);
        }
    }

    /**
     * Edits the date of birth of the selected patient in the table view.
     * This method is triggered by a cell edit event in the table view.
     *
     * @param event The cell edit event containing the patient and the new date of birth value.
     */
    private void editDateOfBirth(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Update the date of birth of the patient with the new value from the event.
        event.getRowValue().setDateOfBirth(event.getNewValue());

        // Perform the update operation for the patient in the database.
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with care levels was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Checks if the selected patient is locked.
        // If the patient is locked, the edit operation is not allowed.
        if (isObjectLocked())
        {
            return;
        }
        // If the patient is not locked, proceed with the care level edit.
        else
        {
            editCareLevel(event);
        }
    }

    /**
     * Edits the care level of the selected patient in the table view.
     * This method is triggered by a cell edit event in the table view.
     *
     * @param event The cell edit event containing the patient and the new care level value.
     */
    private void editCareLevel(TableColumn.CellEditEvent<Patient, String> event)
    {
        // Update the care level of the patient with the new value from the event.
        event.getRowValue().setCareLevel(event.getNewValue());

        // Perform the update operation for the patient in the database.
        this.doUpdate(event);
    }


    /**
     * Updates a patient by calling the method <code>update()</code> of {@link PatientDao}.
     *
     * @param event Event including the changed object and the change.
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> event) {
        try {
            // Update the database with the edited patient data.
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            // If an SQL exception occurs during the update operation, print the stack trace.
            exception.printStackTrace();
        }
    }

    /**
     * Reloads all patients to the table by clearing the list of all patients and filling it again by all persisted
     * patients, delivered by {@link PatientDao}.
     */
    private void readAllAndShowInTableView() {
        // Clear the existing patient array
        arrayPatients.clear();

        // Get the DAO for patients
        this.dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            // Read all patients from the database and add them to the patient array
            this.arrayPatients.addAll(this.dao.readAll());
        } catch (SQLException exception) {
            // If an SQL exception occurs during reading, print the stack trace
            exception.printStackTrace();
        }
    }

    /**
     * This method handles events fired by the button to delete patients. It calls {@link PatientDao} to delete the
     * patient from the database and removes the object from the list, which is the data source of the
     * <code>TableView</code>.
     */
    @FXML
    public void handleDelete()
    {

        // Pulls the selected patient out of the table view.
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        /**
         * Checks if the selected patient is locked.
         * @return true if the patient is locked, false otherwise.
         */
        if (isObjectLocked())
        {
            /**
             * Checks if the current date is above or equal to the mandatory date to legally delete the patient.
             * @return true if the current date is past the legal lock date, false otherwise.
             */
            if (checkIfAboveLegalLockDate())
            {
                // Deletes the selected patient if the current date is past the legal lock date.
                deletePatient(currentSelectedPatient);
            }
            else
            {
                // The patient is not deleted if the current date is before the pre-determined date in 10 years.
                return;
            }
        }
        else
        {
            // If the patient is not locked, the deletion process is not carried out.
            return;
        }
    }

    /**
     * Deletes the given patient from the database and the table view.
     *
     * @param currentSelectedPatient The patient to be deleted.
     */
    private void deletePatient(Patient currentSelectedPatient)
    {
        // Check if the current selected patient is not null.
        if (currentSelectedPatient != null)
        {
            try
            {
                /**
                 * Deletes the selected patient from the database.
                 * The patient is identified by their unique patient ID (PID).
                 */
                DaoFactory.getDaoFactory().createPatientDAO().deleteById(currentSelectedPatient.getPatientID());

                /**
                 * Deletes the selected patient from the table view.
                 * The patient is removed from the list of items displayed in the table view.
                 */
                this.tableView.getItems().remove(currentSelectedPatient);
            }
            catch (SQLException exception)
            {
                // Print the stack trace of the exception if an SQL exception occurs.
                exception.printStackTrace();
            }
        }
    }

    /**
     * Handles the lock operation for the currently selected patient in the table view.
     * The lock operation assigns a locking date (current date plus 10 years)
     * to the selected patient and updates the patient information in the database.
     */
    public void handleLock()
    {
        // Retrieve the currently selected patient from the table view.
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        try
        {
            // the patient gets the Date from now in 10 years assigned as the locking date
            calculateLockDateInTenYears(currentSelectedPatient);

            // Update the patient's information in the database using the DAO (Data Access Object).
            this.dao.update(currentSelectedPatient);
        }
        catch (SQLException exception)
        {
            // Print the stack trace of the exception if an SQL exception occurs.
            exception.printStackTrace();
        }
    }
    /**
     * This method handles the events fired by the button to add a patient. It collects the data from the
     * <code>TextField</code>s, creates an object of class <code>Patient</code> of it and passes the object to
     * {@link PatientDao} to persist the data.
     */

    @FXML
    public void handleAdd() {
        // Retrieve input data from text fields and choice box
        String surname   = this.textFieldSurname.getText();
        String firstName = this.textFieldFirstName.getText();
        String birthday  = this.textFieldDateOfBirth.getText();
        String careLevel = this.textFieldCareLevel.getText();
        Room room        = this.choiceBoxRoom.getValue();

        // Convert birthday string to LocalDate object
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);

        try {
            // Create a new patient object with the input data, set status to "active", and add it to the database via DAO
            this.dao.create(new Patient(firstName, surname, date, careLevel, EMPTY, room, "active"));
        } catch (SQLException exception) {
            // If an SQL exception occurs during creation, print the stack trace
            exception.printStackTrace();
        }

        // Refresh the TableView to display the updated patient list
        readAllAndShowInTableView();

        // Clear the input text fields
        clearTextfields();
    }

    /**
     * Clears all contents from all <code>TextField</code>s.
     */
    private void clearTextfields() {
        // Clear the input text fields
        this.textFieldFirstName.clear();
        this.textFieldSurname.clear();
        this.textFieldDateOfBirth.clear();
        this.textFieldCareLevel.clear();

        // Reset the choice box value to null
        this.choiceBoxRoom.setValue(null);
    }

    /**
     * Checks if the input data provided by the user is valid.
     *
     * @return true if the input data is valid, false otherwise.
     */
    private boolean areInputDataValid()
    {
        // Check if date of birth is provided and in valid format
        if (!this.textFieldDateOfBirth.getText().isBlank())
        {
            try
            {
                // Attempt to convert the date of birth string to a LocalDate object
                DateConverter.convertStringToLocalDate(this.textFieldDateOfBirth.getText());
            } catch (Exception exception)
            {
                // If an exception occurs during conversion, return false indicating invalid input data
                return false;
            }
        }

        // Check if all required fields are not empty and room selection is made
        return !this.textFieldFirstName.getText().isBlank() &&
                !this.textFieldSurname.getText().isBlank() &&
                !this.textFieldDateOfBirth.getText().isBlank() &&
                !this.textFieldCareLevel.getText().isBlank() &&
                this.choiceBoxRoom.getValue() != null;
    }

    /**
     * Saves the current date in a variable.
     *
     * @return The current date as a Calendar object.
     */
    @Override
    protected Calendar getCurrentDate()
    {
        // Saves and extracts the current Date
        return Calendar.getInstance();
    }

    /**
     * Returns the current date and adds 10 years to it.
     *
     * @return The date in ten years as a string.
     */
    protected String getActualDateInTenYears()
    {
        // extracts the current selected Patient from the tableView
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        // Saves the current Date AND adds 10 years onto it
        Calendar DateInTenYears = calculateLockDateInTenYears(currentSelectedPatient);


        // returns the DateInTenYears as a String
        return DateInTenYears.toString();
    }

    /**
     * Calculates the date ten years from the current date and sets it as the lock date for the selected patient in the application.
     *
     * @param patient The patient for whom to calculate the lock date.
     * @return The updated Calendar object representing the lock date.
     */
    private Calendar calculateLockDateInTenYears(Patient patient) {
        Calendar dateInTenYears = getCurrentDate();
        dateInTenYears.add(Calendar.YEAR, TEN_YEARS);

        // Set the lock date in the patient object
        int tenYearLockYear = dateInTenYears.get(Calendar.YEAR);
        patient.setLockDateInTenYears(String.valueOf(tenYearLockYear));

        return dateInTenYears;
    }

    /**
     * Checks if the current date is within the legal patient's lock date.
     *
     * @return {@code true} if the current date is on or after the lock date
     * {@code false} otherwise.
     */
    @Override
    protected boolean checkIfAboveLegalLockDate()
    {
        // Get the currently selected patient
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        // Get the current year
        int currentYear = getCurrentDate().get(Calendar.YEAR);

        // Get the lock date in ten years from the patient object
        int InTenYears = Integer.parseInt(String.valueOf(currentSelectedPatient.getLockDateInTenYears()));

        // Compare the current year with the lock date
        if (currentYear == InTenYears || currentYear > InTenYears)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks if a patient is locked or not.
     *
     * @return {@code true} if the patient is locked, {@code false} otherwise.
     */
    @Override
    protected boolean isObjectLocked()
    {
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        // If the patient doesn't contain lock dates, return false
        String compare = currentSelectedPatient.getLockDateInTenYears().get();

        // value '9999' means the patient doesn't contain a lock date, returning false
        if (compare.equals("StringProperty [value: 9999]"))
        {
            return false;
        }
        // If the patient contains lock dates, return true
        else
        {
            return true;
        }
    }

    /**
     * Archives the selected patient object.
     * This method is intended to be overridden in subclasses to implement specific archiving logic.
     * Currently, this method does not perform any operations and is left empty.
     */
    @Override
    protected void archiveObject()
    {
        // This method is intentionally left empty.
    }
}
