package de.hitec.nhplus.datastorage;


import de.hitec.nhplus.model.Employee;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.model.User;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class UserDao extends DaoImp<User> {

    private final EmployeeDao employeeDao;
    /**
     * The constructor initiates an object of <code>UserDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public UserDao(Connection connection, EmployeeDao employeeDao) {

        super(connection);
        this.employeeDao = employeeDao;
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>User</code>.
     *
     * @param user Object of <code>User</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given User.
     */
    @Override
    protected PreparedStatement getCreateStatement(User user) {
        PreparedStatement preparedStatement = null;
        var getemployeeID = user.getemployee().getemployeeID();
        try {
            final String SQL = "INSERT INTO user (employeeID, userName, userPassword) VALUES (?,?,?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, getemployeeID);
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query an User by a given User id (UserID).
     *
     * @param userID User id to query.
     * @return <code>PreparedStatement</code> to query the User.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long userID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT userName FROM user WHERE userID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }


    public PreparedStatement getPasswordFromUsername(String userName) {

        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM user WHERE userName = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, userName);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one User to an object of <code>User</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>User</code>.
     * @return Object of class <code>User</code> with the data from the resultSet.
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getInt("userId"),
                employeeDao.read(result.getLong("employeeID")),
                result.getString("userName"),
                result.getString("userPassword"));
    }



    /**
     * Generates a <code>PreparedStatement</code> to query all Users.
     *
     * @return <code>PreparedStatement</code> to query all Users.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM user";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all Users to an <code>ArrayList</code> of <code>User</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>User</code>.
     * @return <code>ArrayList</code> with objects of class <code>User</code> of all rows in the
     * <code>ResultSet</code>.
     */

    /*
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        while (result.next()) {
            User user = getInstanceFromResultSet(result);
            list.add(user);
        }
        return list;
    }
    */

    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        while (result.next()) {
            User user = new User(
                    result.getInt("userID"),
                    employeeDao.read(result.getInt("employeeID")),
                    result.getString("userName"),
                    result.getString("userPassword"));
            list.add(user);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given User, identified
     * by the id of the User (UserID).
     *
     * @param user User object to update.
     * @return <code>PreparedStatement</code> to update the given User.
     */
    @Override
    protected PreparedStatement getUpdateStatement(User user) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE user SET " +
                            "employeeID = ?," +
                            "userName = ?," +
                            "userPassword = ?" +
                            "WHERE userID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, user.getemployeeID());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());
            preparedStatement.setLong(4, user.getUserID());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
//MOIN
    /**
     * Generates a <code>PreparedStatement</code> to delete an User with the given id.
     *
     * @param UserID Id of the User to delete.
     * @return <code>PreparedStatement</code> to delete User with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long UserID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM user WHERE userID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, UserID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    public User executeGetPassword(String userName) throws SQLException{
        ResultSet result = getPasswordFromUsername(userName).executeQuery();
        return getInstanceFromResultSet(result);
    }
}
