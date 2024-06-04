package de.hitec.nhplus.datastorage;

import java.sql.*;
import java.util.ArrayList;

import de.hitec.nhplus.model.Employee;
import de.hitec.nhplus.model.User;
import javafx.beans.property.SimpleStringProperty;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL statements.
 */
public class EmployeeDao extends DaoImp<Employee> {

    /**
     * The constructor initiates an object of <code>EmployeeDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public EmployeeDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>Employee</code>.
     *
     * @param employee Object of <code>Employee</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given employee.
     */
    @Override
    protected PreparedStatement getCreateStatement(Employee employee) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO Employee (firstname, surname, role,lockDateInTenYears, status, phoneNumber) VALUES ( ?, ?, ?, ?,?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getrole());
            preparedStatement.setString(4, String.valueOf(employee.getLockDateInTenYears()));
            preparedStatement.setString(5, employee.getstatus());
            preparedStatement.setString(6, employee.getphoneNumber());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query an employee by a given employee id (employeeID).
     *
     * @param employeeID Employee id to query.
     * @return <code>PreparedStatement</code> to query the employee.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long employeeID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM Employee WHERE employeeID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, employeeID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one employee to an object of <code>Employee</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Employee</code>.
     * @return Object of class <code>Employee</code> with the data from the resultSet.
     * @throws SQLException if a database access error occurs
     */

    @Override
    protected Employee getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Employee(
                result.getLong(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
                result.getString(7));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all employees.
     *
     * @return <code>PreparedStatement</code> to query all employees.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM employee";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all employees to an <code>ArrayList</code> of <code>Employee</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>Employee</code>.
     * @return <code>ArrayList</code> with objects of class <code>Employee</code> of all rows in the <code>ResultSet</code>.
     * @throws SQLException if a database access error occurs
     */
    @Override
    protected ArrayList<Employee> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Employee> list = new ArrayList<>();
        while (result.next()) {
            Employee employee = new Employee(
                    result.getInt("employeeID"),
                    result.getString("firstName"),
                    result.getString("surname"),
                    result.getString("role"),
                    result.getString("lockDateInTenYears"),
                    result.getString("status"),
                    result.getString("phoneNumber"));
            list.add(employee);
        }
        return list;
    }


    /**
     * Generates a <code>PreparedStatement</code> to update the given employee, identified by the id of the employee (employeeID).
     *
     * @param employee Employee object to update.
     * @return <code>PreparedStatement</code> to update the given employee.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Employee employee) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE employee SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "role = ?, " +
                            "status = ? " +
                            "WHERE employeeID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getrole());
            preparedStatement.setString(4, employee.getstatus());
            preparedStatement.setLong(5, employee.getemployeeID());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete an employee with the given id.
     *
     * @param employeeID Id of the employee to delete.
     * @return <code>PreparedStatement</code> to delete employee with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long employeeID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM Employee WHERE employeeID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, employeeID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
