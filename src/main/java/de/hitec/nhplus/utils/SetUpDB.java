package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.*;
import de.hitec.nhplus.model.Employee;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Room;
import de.hitec.nhplus.model.Treatment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalTime;

/**
 * Call static class provides to static methods to set up and wipe the database. It uses the class ConnectionBuilder
 * and its path to build up the connection to the database. The class is executable. Executing the class will build
 * up a connection to the database and calls setUpDb() to wipe the database, build up a clean database and fill the
 * database with some test data.
 */
public class SetUpDB {

    /**
     * This method wipes the database by dropping the tables. Then the method calls DDL statements to build it up from
     * scratch and DML statements to fill the database with hard coded test data.
     */
    public static void setUpDb() {
        Connection connection = ConnectionBuilder.getConnection();
        SetUpDB.wipeDb(connection);
        SetUpDB.setUpTableRoom(connection);
        SetUpDB.setUpTablePatient(connection);
        SetUpDB.setUpTableEmployee(connection);
        SetUpDB.setUpTableUser(connection);
        SetUpDB.setUpTableTreatment(connection);
        SetUpDB.setUpEmployees();
        SetUpDB.setUpRooms();
        SetUpDB.setUpPatients();
        SetUpDB.setUpTreatments();

    }



    /**
     * This method wipes the database by dropping the tables.
     */
    public static void wipeDb(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS patient");
            statement.execute("DROP TABLE IF EXISTS treatment");
            statement.execute("DROP TABLE IF EXISTS room");
            statement.execute("DROP TABLE IF EXISTS employee");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTableRoom(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS room (" +
                "   roomID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   roomName TEXT NOT NULL " +
                " " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    private static void setUpTablePatient(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS patient (" +
                "   patientID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   roomID INTEGER NOT NULL, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   dateOfBirth TEXT NOT NULL, " +
                "   carelevel TEXT NOT NULL, " +
                "   FOREIGN KEY (roomID) REFERENCES room (roomID) " +
                " " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTableTreatment(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS treatment (" +
                "   treatmentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   patientID INTEGER NOT NULL, " +
                "   treatment_date DATE NOT NULL, " +
                "   begin TEXT NOT NULL, " +
                "   end TEXT NOT NULL, " +
                "   description TEXT NOT NULL, " +
                "   remark TEXT NOT NULL, " +
                "   employeeID INTEGER NOT NULL, " +
                "   state TEXT NOT NULL, " +
                "   FOREIGN KEY (patientID) REFERENCES patient (patientID) ON DELETE CASCADE, " +
                "   FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE " +
                " " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTableEmployee(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS employee (" +
                "   employeeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   role TEXT NOT NULL, " +
                "   status TEXT NOT NULL" +
                " " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTableUser(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS user (" +
                "   userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   employeeID INTEGER NOT NULL, " +
                "   userName TEXT NOT NULL, " +
                "   userPassword TEXT NOT NULL, " +
                "   FOREIGN KEY (employeeID) REFERENCES employee (employeeID) ON DELETE CASCADE " +
                " " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    private static void setUpEmployees() {
        try {
            EmployeeDao dao = DaoFactory.getDaoFactory().createEmployeeDAO();
            dao.create(new Employee("Darius", "Vader","Pflegekraft","aktiv"));
            dao.create(new Employee("Marion", "Meier","Ärztin","inaktiv"));
            dao.create(new Employee("Hannelore", "Ingelore","Pflegekraft","aktiv"));
            dao.create(new Employee("Donki", "Xote","Pflegekaft","aktiv"));
            dao.create(new Employee("Evelynn", "Parker","Pflegekraft","Inaktiv"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    private static void setUpRooms() {
        try {
            RoomDao dao = DaoFactory.getDaoFactory().createRoomDAO();
            dao.create(new Room(1, "202"));
            dao.create(new Room(2, "010"));
            dao.create(new Room(3, "002"));
            dao.create(new Room(4, "013"));
            dao.create(new Room(5, "001"));
            dao.create(new Room(6, "110"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void setUpPatients() {
        try {
            PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
            RoomDao roomdao = DaoFactory.getDaoFactory().createRoomDAO();
            dao.create(new Patient("Seppl", "Herberger", convertStringToLocalDate("1945-12-01"), "4", roomdao.read(1)));
            dao.create(new Patient("Martina", "Gerdsen", convertStringToLocalDate("1954-08-12"), "5", roomdao.read(2)));
            dao.create(new Patient("Gertrud", "Franzen", convertStringToLocalDate("1949-04-16"), "3", roomdao.read(3)));
            dao.create(new Patient("Ahmet", "Yilmaz", convertStringToLocalDate("1941-02-22"), "3", roomdao.read(4)));
            dao.create(new Patient("Hans", "Neumann", convertStringToLocalDate("1955-12-12"), "2", roomdao.read(5)));
            dao.create(new Patient("Elisabeth", "Müller", convertStringToLocalDate("1958-03-07"), "5", roomdao.read(6)));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void setUpTreatments() {
        try {
            TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
            EmployeeDao employeeDao = DaoFactory.getDaoFactory().createEmployeeDAO();
            PatientDao patientDao = DaoFactory.getDaoFactory().createPatientDAO();
            dao.create(new Treatment(convertStringToLocalDate("2023-06-03"), convertStringToLocalTime("11:00"), convertStringToLocalTime("15:00"), "Gespräch", "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind.", patientDao.read(4), employeeDao.read(3), "in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-05"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:30"), "Gespräch", "Patient irrt auf der Suche nach gestohlenen Wertsachen durch die Etage und bezichtigt andere Bewohner des Diebstahls.\nPatient wird in seinen Raum zurückbegleitet und erhält Beruhigungsmittel.",patientDao.read(3), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-04"), convertStringToLocalTime("07:30"), convertStringToLocalTime("08:00"), "Waschen", "Patient mit Waschlappen gewaschen und frisch angezogen. Patient gewendet.",patientDao.read(1), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-06"), convertStringToLocalTime("15:10"), convertStringToLocalTime("16:00"), "Spaziergang", "Spaziergang im Park, Patient döst  im Rollstuhl ein",patientDao.read(2), employeeDao.read(3), "in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("16:00"), "Spaziergang", "Parkspaziergang; Patient ist heute lebhafter und hat klare Momente; erzählt von seiner Tochter",patientDao.read(4), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-07"), convertStringToLocalTime("11:00"), convertStringToLocalTime("11:30"), "Waschen", "Waschen per Dusche auf einem Stuhl; Patientin gewendet;",patientDao.read(2), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"), convertStringToLocalTime("15:30"), "Physiotherapie", "Übungen zur Stabilisation und Mobilisierung der Rückenmuskulatur",patientDao.read(1), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-08-24"), convertStringToLocalTime("09:30"), convertStringToLocalTime("10:15"), "KG", "Lymphdrainage", patientDao.read(4), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-08-31"), convertStringToLocalTime("13:30"), convertStringToLocalTime("13:45"), "Toilettengang", "Hilfe beim Toilettengang; Patientin klagt über Schmerzen beim Stuhlgang. Gabe von Iberogast",patientDao.read(1), employeeDao.read(3),"in Bearbeitung"));
            dao.create(new Treatment(convertStringToLocalDate("2023-09-01"), convertStringToLocalTime("16:00"), convertStringToLocalTime("17:00"), "KG", "Massage der Extremitäten zur Verbesserung der Durchblutung",patientDao.read(2), employeeDao.read(3), "in Bearbeitung"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SetUpDB.setUpDb();
    }
}
