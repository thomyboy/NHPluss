package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class TreatmentDao extends DaoImp<Treatment> {

    private final PatientDao patientDao;
    private final EmployeeDao employeeDao;
    /**
     * The constructor initiates an object of <code>TreatmentDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public TreatmentDao(Connection connection, PatientDao patientDao, EmployeeDao employeeDao) {
        super(connection);
        this.employeeDao = employeeDao;
        this.patientDao = patientDao;
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>Treatment</code>.
     *
     * @param treatment Object of <code>Treatment</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given patient.
     */
    @Override
    protected PreparedStatement getCreateStatement(Treatment treatment) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO treatment (treatment_date, begin, end, description, remark, patientID, employeeID, state ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ? )";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, treatment.getDate());
            preparedStatement.setString(2, treatment.getBegin());
            preparedStatement.setString(3, treatment.getEnd());
            preparedStatement.setString(4, treatment.getDescription());
            preparedStatement.setString(5, treatment.getRemarks());
            preparedStatement.setLong(6, treatment.getPatient().getPatientID());
            preparedStatement.setLong(7, treatment.getEmployee().getemployeeID());
            preparedStatement.setString(8, treatment.getState());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query a treatment by a given treatment id (tid).
     *
     * @param tid Treatment id to query.
     * @return <code>PreparedStatement</code> to query the treatment.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long tid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM treatment WHERE treatmentID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, tid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one treatment to an object of <code>Treatment</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Treatment</code>.
     * @return Object of class <code>Treatment</code> with the data from the resultSet.
     */
    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString("treatment_date"));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString("begin"));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString("end"));
        return new Treatment(
                result.getLong("treatmentID"),
                patientDao.read(result.getLong("patientID")),
                employeeDao.read(result.getLong("employeeID")),
                date, begin, end,
                result.getString("description"),
                result.getString("remark"),
                result.getString("state"));

        //roomDao.read(result.getInt("roomID")));

    }

    /**
     * Generates a <code>PreparedStatement</code> to query all treatments.
     *
     * @return <code>PreparedStatement</code> to query all treatments.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM treatment";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all treatments to an <code>ArrayList</code> with objects of class
     * <code>Treatment</code>.
     *
     * @param result ResultSet with all rows. The columns will be mapped to objects of class <code>Treatment</code>.
     * @return <code>ArrayList</code> with objects of class <code>Treatment</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        while (result.next()) {
            Treatment treatment = getInstanceFromResultSet(result);
            list.add(treatment);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all treatments of a patient with a given patient id (pid).
     *
     * @param pid Patient id to query all treatments referencing this id.
     * @return <code>PreparedStatement</code> to query all treatments of the given patient id (pid).
     */
    private PreparedStatement getReadAllTreatmentsOfOnePatientByPid(long pid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM treatment WHERE patientID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, pid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Queries all treatments of a given patient id (pid) and maps the results to an <code>ArrayList</code> with
     * objects of class <code>Treatment</code>.
     *
     * @param pid Patient id to query all treatments referencing this id.
     * @return <code>ArrayList</code> with objects of class <code>Treatment</code> of all rows in the
     * <code>ResultSet</code>.
     */
    public List<Treatment> readTreatmentsByPid(long pid) throws SQLException {
        ResultSet result = getReadAllTreatmentsOfOnePatientByPid(pid).executeQuery();
        return getListFromResultSet(result);
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given treatment, identified
     * by the id of the treatment (tid).
     *
     * @param treatment Treatment object to update.
     * @return <code>PreparedStatement</code> to update the given treatment.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Treatment treatment) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE treatment SET " +
                            "treatment_date = ?, " +
                            "begin = ?, " +
                            "end = ?, " +
                            "description = ?, " +
                            "remark = ?, " +
                            "patientID = ?, " +
                            "employeeID = ?, " +
                            "state = ?, " +
                            "WHERE treatmentID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, treatment.getDate());
            preparedStatement.setString(2, treatment.getBegin());
            preparedStatement.setString(3, treatment.getEnd());
            preparedStatement.setString(4, treatment.getDescription());
            preparedStatement.setString(5, treatment.getRemarks());
            preparedStatement.setLong(6, treatment.getPatient().getPatientID());
            preparedStatement.setLong(7, treatment.getEmployee().getemployeeID());
            preparedStatement.setString(8, treatment.getState());
            preparedStatement.setLong(9, treatment.getTreatmentID());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete a treatment with the given id.
     *
     * @param tid Id of the Treatment to delete.
     * @return <code>PreparedStatement</code> to delete treatment with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long tid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "DELETE FROM treatment WHERE treatmentID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, tid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
/**
 *
 * Evenntuell unnötig und aus alten Testes:
 *
 *
    public Patient executeGetPatientFullName(long patientID) throws SQLException{
        ResultSet result = patientDao.getPatientNameByID(patientID).executeQuery();
        return patientDao.getInstanceFromResultSet(result);
    }
    public Employee executeEmployeeGetFullName(long employeeID) throws SQLException{
        ResultSet result = employeeDao.getEmployeeNameByID(employeeID).executeQuery();
        return employeeDao.getInstanceFromResultSet(result);
    }
**/


}