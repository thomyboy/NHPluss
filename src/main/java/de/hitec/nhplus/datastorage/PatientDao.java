package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class PatientDao extends DaoImp<Patient> {

    private final RoomDao roomDao;

    /**
     * The constructor initiates an object of <code>PatientDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public PatientDao(Connection connection, RoomDao roomDao) {
        super(connection);
        this.roomDao = roomDao;
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>Patient</code>.
     *
     * @param patient Object of <code>Patient</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given patient.
     */
    @Override
    protected PreparedStatement getCreateStatement(Patient patient)
    {
        PreparedStatement preparedStatement = null;
        var getPatientRoomID = patient.getRoom().getRoomID();
        try
        {
            final String SQL = "INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomid, lockDateInTenYears) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setInt(5, getPatientRoomID);
            preparedStatement.setString(6, String.valueOf(patient.getLockDateInTenYears()));
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return preparedStatement;
    }



    /**
     * Generates a <code>PreparedStatement</code> to query a patient by a given patient id (pid).
     *
     * @param pid Patient id to query.
     * @return <code>PreparedStatement</code> to query the patient.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM patient WHERE patientID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one patient to an object of <code>Patient</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Patient</code>.
     * @return Object of class <code>Patient</code> with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Patient(
                result.getInt("patientID"),
                result.getString("firstname"),
                result.getString("surname"),
                DateConverter.convertStringToLocalDate(result.getString("dateOfBirth")),
                result.getString("carelevel"),
                result.getString("lockingArrayDates"),
                roomDao.read(result.getInt("roomID")));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all patients.
     *
     * @return <code>PreparedStatement</code> to query all patients.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM patient";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all patients to an <code>ArrayList</code> of <code>Patient</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>Patient</code>.
     * @return <code>ArrayList</code> with objects of class <code>Patient</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<>();
        while (result.next()) {
            Patient patient = new Patient(
                    result.getInt("patientID"),
                    result.getString("firstname"),
                    result.getString("surname"),
                    DateConverter.convertStringToLocalDate(result.getString("dateOfBirth")),
                    result.getString("carelevel"),
                    result.getString("lockDateInTenYears"),
                    roomDao.read(result.getInt("roomID")));
            list.add(patient);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given patient, identified
     * by the id of the patient (pid).
     *
     * @param patient Patient object to update.
     * @return <code>PreparedStatement</code> to update the given patient.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Patient patient) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE patient SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "dateOfBirth = ?, " +
                            "carelevel = ?, " +
                            "roomID = ? " +
                            "WHERE patientID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getDateOfBirth());
            preparedStatement.setString(4, patient.getCareLevel());
            preparedStatement.setInt(5, patient.getRoom().getRoomID());
            preparedStatement.setLong(6, patient.getPid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete a patient with the given id.
     *
     * @param pid Id of the patient to delete.
     * @return <code>PreparedStatement</code> to delete patient with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM patient WHERE patientID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
