package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    public static final String SERVER_NAME = "localhost";
    public static final int PORT = 3306;
    public static final String DATABASE = "video_rental_system";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private static final String URL_TEMPLATE = "jdbc:mysql://%s:%d/%s";

    private Connection connection;

    // singleton
    private static DatabaseConnection instance;
    public static DatabaseConnection getInstance() {
        if(instance == null) {
            instance =  new DatabaseConnection(SERVER_NAME, PORT, DATABASE, USERNAME, PASSWORD);
        }
        return instance;
    }

    private DatabaseConnection(String server, int port, String database, String user, String password) throws DatabaseException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(
                    String.format(URL_TEMPLATE, server, port, database),
                    user, password
            );
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new DatabaseException("Fail to initialize driver class!", e);
        } catch (SQLException e) {
            throw new DatabaseException("Cannot connect to database!", e);
        }
    }

    public ResultSet select(String sql, Object[] parameterValues) throws DatabaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            this.setQueryValues(parameterValues, preparedStatement);
            return preparedStatement.executeQuery();
        } catch(SQLException e) {
            throw new DatabaseException("Select failed!", e);
        }
    }

    public List<Long> insert(String sql, Object[] parameterValues) throws DatabaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.setQueryValues(parameterValues, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            List<Long> newIds = new ArrayList<>();
            while(generatedKeys.next()) {
                newIds.add(generatedKeys.getLong(1));
            }
            return newIds;
        } catch(SQLException ex) {
            throw new DatabaseException("Insert failed!", ex);
        }
    }

    public int update(String sql, Object[] parameterValues) throws DatabaseException {
        return this.update(sql, parameterValues, "Update");
    }

    public int delete(String sql, Object[] parameterValues) throws DatabaseException {
        return this.update(sql, parameterValues, "Delete");
    }

    private int update(String sql, Object[] parameterValues, String updateType) throws DatabaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            this.setQueryValues(parameterValues, preparedStatement);
            return preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            throw new DatabaseException(updateType + " failed!", ex);
        }
    }

    private void setQueryValues(Object[] parameterValues, PreparedStatement preparedStatement) throws SQLException {
        if(parameterValues != null && parameterValues.length > 0) {
            for(int i = 0; i < parameterValues.length; i++) {
                preparedStatement.setObject(i + 1, parameterValues[i]);
            }
        }
    }
}
