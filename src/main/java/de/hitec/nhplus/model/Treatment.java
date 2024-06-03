package de.hitec.nhplus.model;

import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.utils.DateConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Treatment {
    private long treatmentID;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;
    private Patient patient;
    private Employee employee;
    private long patientID;
    private long employeeID;
    private PatientDao patientDao;
    private String employeeName;
    private String patientName;


    //TODO: umschreiben, ich bin zu fertig mit dem scheiß hier.

    private String state;

    /**
     * Constructor to initiate an object of class <code>Treatment</code> with the given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a treatment id (tid).
     *
     * @param patientID Id of the treated patient.
     * @param date Date of the Treatment.
     * @param begin Time of the start of the treatment in format "hh:MM"
     * @param end Time of the end of the treatment in format "hh:MM".
     * @param description Description of the treatment.
     * @param remarks Remarks to the treatment.
     */
    public Treatment(LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, Patient patient, Employee employee, String state) {
        this.patient = patient;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.employee= employee;
        this.state = state;
        this.employeeID = employee.getEmployeeID();
        this.patientID = patient.getPid();
    }

    public Treatment(LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, String empployeeName, String patientName, String state) {
        this.patient = patient;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.employee= employee;
        this.state = state;
        this.employeeName = empployeeName;
        this.patientName = patientName;
    }

    /**
     * Constructor to initiate an object of class <code>Treatment</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a treatment id (tid).
     *
     * @param treatmentID Id of the treatment.
     * @param patientID Id of the treated patient.
     * @param date Date of the Treatment.
     * @param begin Time of the start of the treatment in format "hh:MM"
     * @param end Time of the end of the treatment in format "hh:MM".
     * @param description Description of the treatment.
     * @param remarks Remarks to the treatment.
     */
    public Treatment(long treatmentID, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, Patient patient, Employee employee,  String state) {
        this.treatmentID = treatmentID;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.patient = patient;
        this.employee = employee;
        this.state = state;
        this.employeeID = employee.getEmployeeID();
        this.patientID = patient.getPid();
    }

    public long getTreatmentID() {
        return treatmentID;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public String getDate() {
        return date.toString();
    }

    public String getBegin() {
        return begin.toString();
    }

    public String getEnd() {
        return end.toString();
    }

    public void setDate(String date) {
        this.date = DateConverter.convertStringToLocalDate(date);
    }

    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);;
    }

    public void setEnd(String end) {
        this.end = DateConverter.convertStringToLocalTime(end);;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.treatmentID +
                "\nPID: " + this.patient.getPid() +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
