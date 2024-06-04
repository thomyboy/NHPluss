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
        this.roomName =room.getRoomName();
        this.status = status;
        System.out.println("Use new patient");
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
        System.out.println("Use old patient");
    }

    /**
     * Retrieves the lock date in ten years.
     *
     * @return The lock date in ten years as a SimpleStringProperty.
     */
    public SimpleStringProperty getLockDateInTenYears()
    {
        return lockDateInTenYears;
    }

    /**
     * Sets the lock date in ten years with the specified input.
     *
     * @param input The input string to set as the lock date in ten years.
     */
    public void setLockDateInTenYears(String input)
    {
        this.lockDateInTenYears.set(input);
    }

    /**
     * Retrieves the patient ID.
     *
     * @return The patient ID as a long value.
     */
    public long getPatientID() {
        return patientID.get();
    }

    /**
     * Retrieves the property representing the patient ID.
     *
     * @return The property representing the patient ID as a SimpleLongProperty.
     */
    public SimpleLongProperty patientIDProperty() {
        return patientID;
    }

    /**
     * Retrieves the date of birth of the patient.
     *
     * @return The date of birth of the patient as a string.
     */
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    /**
     * Retrieves the property representing the date of birth of the patient.
     *
     * @return The property representing the date of birth of the patient as a SimpleStringProperty.
     */
    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    /**
     * Retrieves the name of the room associated with the patient.
     *
     * @return The name of the room associated with the patient as a string.
     */
    public String getRoomName(){
        return roomName;
    }


    /**
     * Sets the date of birth of the patient.
     *
     * @param dateOfBirth The date of birth to set for the patient as a string.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    /**
     * Retrieves the care level of the patient.
     *
     * @return The care level of the patient as a string.
     */
    public String getCareLevel() {
        return careLevel.get();
    }

    /**
     * Retrieves the property representing the care level of the patient.
     *
     * @return The property representing the care level of the patient as a SimpleStringProperty.
     */
    public SimpleStringProperty careLevelProperty() {
        return careLevel;
    }

    /**
     * Sets the care level of the patient.
     *
     * @param careLevel The care level to set for the patient.
     */
    public void setCareLevel(String careLevel) {
        this.careLevel.set(careLevel);
    }

    /**
     * Retrieves the room associated with the patient.
     *
     * @return The room associated with the patient.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Retrieves the full name of the patient.
     *
     * @return The full name of the patient as a string.
     */
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