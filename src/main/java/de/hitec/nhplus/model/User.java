package de.hitec.nhplus.model;


public class User {

    private int userID;
    private int employeeID;
    private String userName;
    private String userPassword;

    public User(int userID, int employeeID, String userName, String userPassword) {
        this.userID = userID;
        this.employeeID = employeeID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getemployeeID() {return employeeID;}

    public void setemployeeID(int employeeID) {this.employeeID = employeeID;}

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
