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
    private TableColumn<Patient, Integer> columnId;

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

//    @FXML
//    private TableColumn<Patient, String> columnAssets;

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
    private TextField textFieldlockingArrayDates;

    @FXML
    private ChoiceBox<Room> choiceBoxRoom;

    @FXML
    private TextField textFieldAssets;

    private final ObservableList<Patient> arrayPatients = FXCollections.observableArrayList();

    private static final String EMPTY = "9999";


    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("pid"));

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

        this.columnRoomName.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.columnRoomName.setCellFactory(TextFieldTableCell.forTableColumn());

//        this.columnAssets.setCellValueFactory(new PropertyValueFactory<>("assets"));
//        this.columnAssets.setCellFactory(TextFieldTableCell.forTableColumn());

        //Anzeigen der Daten
        this.tableView.setItems(this.arrayPatients);

        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
            @Override
            public void changed(ObservableValue<? extends Patient> observableValue, Patient oldPatient, Patient newPatient) {
                AllPatientController.this.buttonDelete.setDisable(newPatient == null);
            }
        });

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
        /** Checks if the selected Patient was locked / is locked */
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            editFirstName(event);
        }
    }

    private void editFirstName(TableColumn.CellEditEvent<Patient, String> event)
    {
        event.getRowValue().setFirstName(event.getNewValue());
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
        /** Checks if the selected Patient was locked / is locked */
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            editSurname(event);
        }
    }

    private void editSurname(TableColumn.CellEditEvent<Patient, String> event)
    {
        event.getRowValue().setSurname(event.getNewValue());
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
        /** Checks if the selected Patient was locked / is locked */
        if (isObjectLocked())
        {
            return;
        }
        else
        {
            editDateOfBirth(event);
        }
    }

    private void editDateOfBirth(TableColumn.CellEditEvent<Patient, String> event)
    {
        event.getRowValue().setDateOfBirth(event.getNewValue());
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
        /** Checks if the selected Patient was locked / is locked */
        if (isObjectLocked())
        {
            return;
        }
        /** If Patient is NOT locked / was NOT locked before */
        else
        {
            editCareLevel(event);
        }
    }

    private void editCareLevel(TableColumn.CellEditEvent<Patient, String> event)
    {
        event.getRowValue().setCareLevel(event.getNewValue());
        this.doUpdate(event);
    }


    /**
     * Updates a patient by calling the method <code>update()</code> of {@link PatientDao}.
     *
     * @param event Event including the changed object and the change.
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads all patients to the table by clearing the list of all patients and filling it again by all persisted
     * patients, delivered by {@link PatientDao}.
     */
    private void readAllAndShowInTableView() {
        arrayPatients.clear();

        this.dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.arrayPatients.addAll(this.dao.readAll());
        } catch (SQLException exception) {
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
        /** pulls the selected patient out of the tableView */
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        /** Checks if the selected Patient was locked / is locked */
        if (isObjectLocked())
        {
            /** Checks if current Date is above/equal the mandatory date to legally delete the patient */
            if (checkIfAboveLegalLockDate())
            {
                deletePatient(currentSelectedPatient);
            }
            /** The patient is not deleted if the current date is before the pre-determined date in 10 years */
            else
            {
                return;
            }
        }
        /** if patient is NOT locked */
        else
        {
            return;
        }
    }

    private void deletePatient(Patient currentSelectedPatient)
    {
        if (currentSelectedPatient != null)
        {
            try
            {
                /** deletes the selected patient from the database */
                DaoFactory.getDaoFactory().createPatientDAO().deleteById(currentSelectedPatient.getPatientID());

                /** deletes the selected patient from the tableView */
                this.tableView.getItems().remove(currentSelectedPatient);
            }
            catch (SQLException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void handleLock()
    {
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        try
        {

            /** the locked Patient gets the two major dates (current Date and current Date but 10 years later
             * assigned */
            calculateLockDateInTenYears(currentSelectedPatient);

            this.dao.update(currentSelectedPatient);
//            archiveObject();
        }
        catch (SQLException exception)
        {
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

        String surname   =  this.textFieldSurname    .getText();
        String firstName =  this.textFieldFirstName  .getText();
        String birthday  =  this.textFieldDateOfBirth.getText();
        String careLevel =  this.textFieldCareLevel  .getText();
        Room room        =  this.choiceBoxRoom      .getValue();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        try {
            this.dao.create(new Patient(firstName, surname, date, careLevel, EMPTY,  room,"active"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * Clears all contents from all <code>TextField</code>s.
     */
    private void clearTextfields() {
        this.textFieldFirstName  .clear();
        this.textFieldSurname    .clear();
        this.textFieldDateOfBirth.clear();
        this.textFieldCareLevel  .clear();
        this.choiceBoxRoom.setValue(null);
    }

    private boolean areInputDataValid()
    {
        if (!this.textFieldDateOfBirth.getText().isBlank())
        {
            try
            {
                DateConverter.convertStringToLocalDate(this.textFieldDateOfBirth.getText());
            } catch (Exception exception)
            {
                return false;
            }
        }

        return !this.textFieldFirstName.getText().isBlank() && !this.textFieldSurname.getText().isBlank() &&
                !this.textFieldDateOfBirth.getText().isBlank() && !this.textFieldCareLevel.getText().isBlank() &&

                this.choiceBoxRoom.getValue() != null;

    }


    @Override
    protected Calendar getCurrentDate()
    {
        /** Saves and extracts the current Date */
        return Calendar.getInstance();
    }

    protected String getActualDateInTenYears()
    {
        /** extracts the current selected Patient from the tableView */
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        /** Saves the current Date AND adds 10 years onto it */
        Calendar DateInTenYears = calculateLockDateInTenYears(currentSelectedPatient);


        /** returns the DateInTenYears as a String */
        return DateInTenYears.toString();
    }

    /**
     * This method calculates the date ten years from the current date and sets it as the lock date for the selected patient in the application.
     * it returns the updated Calendar object.
     * @param patient
     * @return DateInTenYears
     */
    private Calendar calculateLockDateInTenYears(Patient patient)
    {
        Calendar DateInTenYears = getCurrentDate();
        DateInTenYears.add(Calendar.YEAR, TEN_YEARS);

        /** Date where the patients lock will end */
        int TenYearLockYear = DateInTenYears.get(Calendar.YEAR);

        patient.setLockDateInTenYears(String.valueOf(TenYearLockYear));

        return DateInTenYears;
    }

    @Override
    protected boolean checkIfAboveLegalLockDate()
    {
        /** Checks if the current Date is within the legal patients' lock date */
        Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        int currentYear = getCurrentDate().get(Calendar.YEAR);
        int InTenYears = Integer.parseInt(String.valueOf(currentSelectedPatient.getLockDateInTenYears()));

        if (currentYear == InTenYears || currentYear > InTenYears)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    /** Checks if a patient is locked or not and therefore return TRUE or FALSE */
    protected boolean isObjectLocked()
    {
        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();

        /** if the Patient doesn't contain lock dates: return false */
        String compare = currentSelectedPatient.getLockDateInTenYears().get();
        if (compare.equals("StringProperty [value: 9999]"))
        {
            return false;
        }
        /** if the Patient containts lock dates: return true */
        else
        {
            return true;
        }
    }

    @Override
    protected void archiveObject()
    {
//        final Patient currentSelectedPatient = tableView.getSelectionModel().getSelectedItem();
//        tableView.getItems().remove(currentSelectedPatient);
    }
}
