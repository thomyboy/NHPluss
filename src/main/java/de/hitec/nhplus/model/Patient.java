package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private SimpleLongProperty patientID;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private String fullName = getSurname() + ", " + getFirstName();
    private final Room room;
    private String roomName;
    private final List<Treatment> arrayAllTreatments = new ArrayList<>();
    private final SimpleStringProperty lockDateInTenYears;
    private String status;

     /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a patient id (pid).
     *
     * @param firstName First name of the patient.
     * @param surname Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel Care level of the patient.
     * @param room Room number of the patient.
     */
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel,
                   String lockDateInTenYears, Room room, String status) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.room = room;
        this.roomName = room.getRoomName();
        this.status = status;
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param patientID Patient id.
     * @param firstName First name of the patient.
     * @param surname Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel Care level of the patient.
     * @param room Room number of the patient.
     */
    public Patient(long patientID, String firstName, String surname, LocalDate dateOfBirth, String careLevel,
                   String lockDateInTenYears, Room room) {
        super(firstName, surname);
        this.patientID = new SimpleLongProperty(patientID);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.room = room;
        this.roomName = room.getRoomName();
    }

    public SimpleStringProperty getLockDateInTenYears()
    {
        return lockDateInTenYears;
    }

    public void setLockDateInTenYears(String input)
    {
        this.lockDateInTenYears.set(input);
    }

    public long getPatientID() {
        return patientID.get();
    }

    public SimpleLongProperty patientIDProperty() {
        return patientID;
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    /**
     * Stores the given string as new <code>birthOfDate</code>.
     *
     * @param dateOfBirth as string in the following format: YYYY-MM-DD.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getCareLevel() {
        return careLevel.get();
    }

    public SimpleStringProperty careLevelProperty() {
        return careLevel;
    }

    public void setCareLevel(String careLevel) {
        this.careLevel.set(careLevel);
    }

    public Room getRoom() {
        return room;
    }

    public String getFullName(){
        return fullName;
    }

    /**
     * Adds a treatment to the list of treatments, if the list does not already contain the treatment.
     *
     * @param treatment Treatment to add.
     * @return False, if the treatment was already part of the list, else true.
     */
    public boolean add(Treatment treatment) {
        if (this.arrayAllTreatments.contains(treatment)) {
            return false;
        }
        this.arrayAllTreatments.add(treatment);
        return true;
    }

    public String toString() {
        return "Patient" + "\nMNID: " + this.patientID +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoom: " + this.room +
                "\n";
    }

    public String getStatus() { return  status;
    }
}