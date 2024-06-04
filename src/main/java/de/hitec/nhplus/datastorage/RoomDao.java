package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class RoomDao extends DaoImp<Room> {

    /**
     * The constructor initiates an object of <code>RoomDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public RoomDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>Room</code>.
     *
     * @param room Object of <code>Room</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given room.
     */
    @Override
    protected PreparedStatement getCreateStatement(Room room) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO Room ( roomName) VALUES ( ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, room.getRoomName());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query an room by a given room id (roomID).
     *
     * @param roomID Room id to query.
     * @return <code>PreparedStatement</code> to query the room.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long roomID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM Room WHERE roomID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, roomID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one room to an object of <code>Room</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Room</code>.
     * @return Object of class <code>Room</code> with the data from the resultSet.
     */
    @Override
    protected Room getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Room(
                result.getInt(1),
                result.getString(2));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all rooms.
     *
     * @return <code>PreparedStatement</code> to query all rooms.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM room";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all rooms to an <code>ArrayList</code> of <code>Room</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>Room</code>.
     * @return <code>ArrayList</code> with objects of class <code>Room</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Room> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Room> list = new ArrayList<>();
        while (result.next()) {
            Room room = new Room(
                    result.getInt(1),
                    result.getString(2));
            list.add(room);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given room, identified
     * by the id of the room (roomID).
     *
     * @param room Room object to update.
     * @return <code>PreparedStatement</code> to update the given room.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Room room) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE room SET " +
                            "room.roomName = ?" +
                            "WHERE roomID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, room.getRoomName());
            preparedStatement.setLong(2, room.getRoomID());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
//MOIN
    /**
     * Generates a <code>PreparedStatement</code> to delete an room with the given id.
     *
     * @param roomID Id of the room to delete.
     * @return <code>PreparedStatement</code> to delete room with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long roomID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM Room WHERE roomID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, roomID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
