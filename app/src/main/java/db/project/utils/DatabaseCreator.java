package db.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DatabaseCreator {

    private final String username;
    private final String password;

    public DatabaseCreator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void createDatabase() {
        final Connection conn = getMySQLConnection();
        
        // runner = new ScriptRunner(conn, false, true);
        final ScriptRunner runner = new ScriptRunner(conn);
        //This is to force decoding of %-escaped characters.
        try (Reader reader = new BufferedReader(new FileReader(new URI(getClass().getResource("/DATABASE.ddl").toString()).getPath()))){
            runner.runScript(reader);
        } catch (IOException | URISyntaxException e){
            throw new IllegalStateException("Could not create db", e);
        }
    }

    private Connection getMySQLConnection() {
        final String dbUri = "jdbc:mysql://localhost:3306/";
        try {
            return DriverManager.getConnection(dbUri, this.username, this.password);
        } catch (final SQLException e) {
            throw new IllegalStateException("Could not establish a connection with db", e);
        }
    }

}
