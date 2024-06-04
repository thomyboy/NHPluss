package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * The Employee class represents an employee with a unique employee ID, role, status, and additional properties.
 * This class extends the Person class, inheriting its properties for name and surname.
 */

public class Employee extends Person{
    private int employeeID;
    private SimpleStringProperty role;
    private String status;
    private String phoneNumber;

    private String fullName = getSurname() + ", " + getFirstName();
    private  SimpleStringProperty lockDateInTenYears;
/* evtl alt und kann weg
    public Employee( int id, String name, String surname, String role, String status, String lockDateInTenYears, String phoneNumber) {
        super(name, surname);
        this.employeeID = id;
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = status;
    }
*/

    /**
     * Constructs a new Employee with the specified details, excluding the employee ID.
     *
     * @param name the first name of the employee
     * @param surname the last name of the employee
     * @param role the role of the employee in the organization
     * @param lockDateInTenYears the lock date in ten years
     * @param status the current status of the employee (e.g., Active, Inactive)
     * @param phoneNumber the phone number of the employee
     */
    public Employee( String name, String surname, String role,String lockDateInTenYears, String status,String phoneNumber) {
        super(name, surname);
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a new Employee with the specified details, including the employee ID.
     *
     * @param employeeID the unique identifier for the employee
     * @param name the first name of the employee
     * @param surname the last name of the employee
     * @param role the role of the employee in the organization
     * @param lockDateInTenYears the lock date in ten years
     * @param status the current status of the employee (e.g., Active, Inactive)
     * @param phoneNumber the phone number of the employee
     */

    public Employee(int employeeID, String name, String surname, String role,String lockDateInTenYears, String status, String phoneNumber) {
        super(name, surname);
        this.employeeID = employeeID;
        this.role = new SimpleStringProperty(role);
        this.lockDateInTenYears = new SimpleStringProperty(lockDateInTenYears);
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the lock date in ten years as a SimpleStringProperty.
     *
     * @return the lock date in ten years
     */

    public SimpleStringProperty getLockDateInTenYears()
    {
        return lockDateInTenYears;
    }

    /**
     * Gets the lock date in ten years as a SimpleStringProperty.
     *
     * @return the lock date in ten years
     */

    public SimpleStringProperty getLockDateInTenYearsAsString()
    {
        return lockDateInTenYears;
    }

    /**
     * Sets the lock date in ten years.
     *
     * @param input the new lock date in ten years
     */

    public void setLockDateInTenYears(String input)
    {
        this.lockDateInTenYears.set(input);
    }

    /**
     * Gets the employee ID.
     *
     * @return the employee ID
     */

    public int getemployeeID() {
        return employeeID;
    }

    /**
     * Property accessor for employee ID.
     *
     * @return the employee ID as a property
     */

    public int employeeIDProperty() {
        return employeeID;
    }

    /**
     * Sets the employee ID.
     *
     * @param employeeID the new employee ID
     */

    public void employeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * Gets the role of the employee.
     *
     * @return the role of the employee
     */

    public String getrole() {
        return role.get();
    }

    /**
     * Property accessor for the role.
     *
     * @return the role as a SimpleStringProperty
     */

    public SimpleStringProperty roleProperty() {
        return role;
    }

    /**
     * Sets the role of the employee.
     *
     * @param role the new role of the employee
     */

    public void setrole(String role) {
        this.role.set(role);
    }

    /**
     * Gets the status of the employee.
     *
     * @return the status of the employee
     */

    public String getstatus() {
        return status;
    }

    /**
     * Gets the status of the employee.
     *
     * @return the status of the employee
     */

    public String getStatus(){
        return status;
    }


    /**
     *
     * @param roomNumber
     */
    public void setStatus(String roomNumber) {
        this.status = roomNumber;
    }

    /**
     * Gets the full name of the employee in the format "surname, first name".
     *
     * @return the full name of the employee
     */

    public String getFullName(){
        return fullName;
    }

    /**
     * Gets the phone number of the employee.
     *
     * @return the phone number of the employee
     */

    public String getphoneNumber() {
        return phoneNumber;
    }
}
