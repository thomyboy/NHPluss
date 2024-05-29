package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

public class Employee extends Person{
    private int employeeID;
    private SimpleStringProperty role;
    private SimpleStringProperty status;


    public Employee(int employeeID, String name, String surname, String role, String status) {
        super(name, surname);
        this.employeeID = employeeID;
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
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



}
