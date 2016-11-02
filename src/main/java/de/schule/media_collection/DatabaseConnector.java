package de.schule.media_collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class DatabaseConnector 
{
    private Connection connection = null;
	private String serverName = "localhost";
	private String dbName = "media_collection";
	private String url = "jdbc:mysql://" + this.serverName + "/" + this.dbName; 
    private String username = "root";
    private String password = "";
    
    
    public DatabaseConnector() throws SQLException{
    	this.connection = this.getConnection();
    }
	
	
	private Connection getConnection() throws SQLException{
		return DriverManager.getConnection(this.url, this.username, this.password);
	}
	private ResultSet getAllMovies() throws SQLException{
        String query = "select * from movies";
        Statement stmt = null;
        stmt = this.connection.createStatement();
        return stmt.executeQuery(query);
	}
	private ResultSet getAllUser() throws SQLException{
        String query = "select * from user";
        Statement stmt = null;
        stmt = this.connection.createStatement();
        return stmt.executeQuery(query);
	}
    
}
