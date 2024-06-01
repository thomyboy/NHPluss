package de.hitec.nhplus.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.print.DocFlavor;

public class Employee extends Person{
    private SimpleLongProperty employeeID;
    private SimpleStringProperty role;
    private SimpleStringProperty status;
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
    public SimpleLongProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) { this.employeeID.set(employeeID);}
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
