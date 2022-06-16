package db.project.model;

import java.sql.Connection;

import db.project.model.mysql.ConnectionProvider;

public class ModelImpl implements Model {

    private Connection dbConnection;
    private String username;
    private String password;
    private String dbName = "hospital";
    
    /**
     * Creates a simple connection to a local database
     */
    public ModelImpl() {
        dbConnection = new ConnectionProvider(username, password, dbName ).getMySQLConnection();
    }
    
}
