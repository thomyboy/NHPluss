package de.hitec.nhplus.datastorage;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getDaoFactory() {
        if (DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    public TreatmentDao createTreatmentDao() {
        return new TreatmentDao(ConnectionBuilder.getConnection(), createPatientDAO(), createEmployeeDAO());
    }

    public PatientDao createPatientDAO() {
        return new PatientDao(ConnectionBuilder.getConnection(), createRoomDAO());
    }
    public EmployeeDao createEmployeeDAO() {
        return new EmployeeDao(ConnectionBuilder.getConnection());
    }


    public RoomDao createRoomDAO() { return new RoomDao(ConnectionBuilder.getConnection());
    }
    public UserDao createUserDAO() { return new UserDao(ConnectionBuilder.getConnection());
    }
}
