package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private SimpleLongProperty pid;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private String fullName = getSurname() + ", " + getFirstName();
    private final Room room;
    private String roomName;
    private final List<Treatment> arrayAllTreatments = new ArrayList<>();
    private final SimpleStringProperty lockDateInTenYears;

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
                   String lockDateInTenYears, Room room) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.room = room;
        this.roomName = room.getRoomName();
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param pid Patient id.
     * @param firstName First name of the patient.
     * @param surname Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel Care level of the patient.
     * @param room Room number of the patient.
     */
    public Patient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel,
                   String lockDateInTenYears, Room room) {
        super(firstName, surname);
        this.pid = new SimpleLongProperty(pid);
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

    public long getPid() {
        return pid.get();
    }

    public SimpleLongProperty pidProperty() {
        return pid;
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
        return "Patient" + "\nMNID: " + this.pid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoom: " + this.room +
                "\n";
    }
}