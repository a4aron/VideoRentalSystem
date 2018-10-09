package dao;

import dbconnection.DatabaseConnection;


abstract class DAO {
    DatabaseConnection databaseConnection;

    DAO() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }
}
