package de.hitec.nhplus.model;


import javafx.beans.property.SimpleLongProperty;

public class User {

    private SimpleLongProperty userID;
    private Employee employee;
    private String userName;
    private String userPassword;
    private  long employeeID;

    public User(long userID, Employee employee, String userName, String userPassword) {
        this.userID = new SimpleLongProperty(userID);
        this.employee = employee;
        this.userName = userName;
        this.userPassword = userPassword;
        this.employeeID = employee.getemployeeID();
    }

    public User( Employee employee, String userName, String userPassword) {
        this.employee = employee;
        this.userName = userName;
        this.userPassword = userPassword;
        this.employeeID = employee.getemployeeID();
    }

    public long getUserID() {
        return userID.get();
    }



    public long getemployeeID() {return employeeID;}
    public Employee getemployee(){return  employee;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
