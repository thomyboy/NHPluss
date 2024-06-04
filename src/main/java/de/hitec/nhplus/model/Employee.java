package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Employee extends Person {
    private int employeeID;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty role;
    private SimpleStringProperty status;
    private SimpleStringProperty phonenumber;

    public String getPhonenumber() {
        return phonenumber.get();
    }


    public Employee(int employeeID, String name, String surname, String role, String status, String phonenumber) {
        super(name, surname);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.employeeID = employeeID;
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
        this.phonenumber = new SimpleStringProperty(phonenumber);
    }

    //MOIN
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
