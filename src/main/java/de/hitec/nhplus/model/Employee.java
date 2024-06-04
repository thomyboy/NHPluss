package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Employee extends Person{
    private int employeeID;
    private SimpleStringProperty role;
    private SimpleStringProperty status;

    private String fullName = getSurname() + ", " + getFirstName();
    private  SimpleStringProperty lockDateInTenYears;

    public Employee( String name, String surname, String role,String lockDateInTenYears, String status) {
        super(name, surname);
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
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String roomNumber) {
        this.status.set(roomNumber);
    }
    public String getFullName(){
        return fullName;
    }


}
