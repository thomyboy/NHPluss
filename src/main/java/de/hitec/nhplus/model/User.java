package de.hitec.nhplus.model;


public class User {

    private int userID;
    private Employee employee;
    private String userName;
    private String userPassword;

    public User(int userID, Employee employee, String userName, String userPassword) {
        this.userID = userID;
        this.employee = employee;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User( Employee employee, String userName, String userPassword) {
        this.employee = employee;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getemployeeID() {return employee.getemployeeID();}


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
