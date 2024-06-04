package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Employee extends Person{
    private int employeeID;
    private SimpleStringProperty role;
    private String status;
    private String phoneNumber;

    private String fullName = getSurname() + ", " + getFirstName();
    private  SimpleStringProperty lockDateInTenYears;

    public Employee( int id, String name, String surname, String role, String status, String lockDateInTenYears, String phoneNumber) {
        super(name, surname);
        this.employeeID = id;
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = status;
    }

    public Employee(String name, String surname, String role, String status, String lockDateInTenYears) {
        super(name, surname);
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = status;
    }
    public Employee(int employeeID, String name, String surname, String role,String lockDateInTenYears, String status) {
        super(name, surname);
        this.employeeID = employeeID;
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = new SimpleStringProperty(status);
    }

    public SimpleStringProperty getLockDateInTenYears()
    {
        return lockDateInTenYears;
    }

    public SimpleStringProperty getLockDateInTenYearsAsString()
    {
        return lockDateInTenYears;
    }

    public void setLockDateInTenYears(String input)
    {
        this.lockDateInTenYears.set(input);
    }
    public int getemployeeID() {
        return employeeID;
    }
    public int employeeIDProperty() {
        return employeeID;
    }

    public void employeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public String getrole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setrole(String role) {
        this.role.set(role);
    }

    public String getstatus() {
        return status;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String roomNumber) {
        this.status = roomNumber;
    }
    public String getFullName(){
        return fullName;
    }


    public String getphoneNumber() {
        return phoneNumber;
    }
}
