package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee extends Person{
    private SimpleLongProperty employeeID;
    private SimpleStringProperty role;
    private SimpleStringProperty status;
    private SimpleStringProperty callNumber;
    private String fullName = getSurname() + ", " + getFirstName();


    public Employee(long employeeID, String name, String surname, String role, String status) {
        super(name, surname);
        this.employeeID = new SimpleLongProperty(employeeID);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
        this.fullName = getFullName();

    }

    public Employee(String name, String surname, String role, String status) {
        super(name, surname);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
        this.fullName = getFullName();
    }
//MOIN
    public long getEmployeeID() {
        return employeeID.get();
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

   public String getFullName(){
        return fullName;
   }

}
