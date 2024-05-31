package de.hitec.nhplus.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee extends Person{
    private SimpleIntegerProperty employeeID;
    private SimpleStringProperty role;
    private SimpleStringProperty status;


    public Employee(int employeeID, String name, String surname, String role, String status) {
        super(name, surname);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);

    }
//MOIN
    public int getemployeeID() {
        return employeeID.get();
    }
    public SimpleIntegerProperty employeeIDProperty() {
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

}
