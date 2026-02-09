package de.haizon.flux.sql;

import java.sql.*;

public class FluxSqlWrapper {

    private String host;
    private String database;
    private String user;
    private String password;
    private int port;

    private Connection connection;

    public FluxSqlWrapper(){
        this.host = "localhost";
        this.database = "test";
        this.user = "root";
        this.password = "";
        this.port = 3306;
    }

    public FluxSqlWrapper configuration(String host, String database, String user, String password){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = 3306;
        return this;
    }

    public FluxSqlWrapper configuration(String host, String database, String user, String password, int port){
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
        return this;
    }


    public FluxSqlWrapper connect(){
        // Stelle die Connection erst HERSTELLEN vor der Benutzung
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                    this.user,
                    this.password
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String executeQuery(String query) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            StringBuilder result = new StringBuilder();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    result.append(metaData.getColumnName(i)).append(": ").append(resultSet.getString(i)).append(" | ");
                }
                result.append("\n");
            }
            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String query) {
        try {
            Statement statement = this.connection.createStatement();
            boolean result = statement.execute(query);
            statement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        return connection;
    }
}
