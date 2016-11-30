package de.schule.media_collection.Data;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.sql.PreparedStatement;

public class DatabaseConnector 
{
	
	// connection
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
	public ResultSet getMoviesFromDatabase(){
        String query = "select * from movies";
        Statement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.createStatement();
	        rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs;        
	}
	public ResultSet getUserFromDatabase() throws SQLException{
        String query = "select * from user";
        Statement stmt = null;
        stmt = this.connection.createStatement();
        return stmt.executeQuery(query);
	}
	public void addMovieAndRelationship(String title, int runtime, String genre, String description, byte[] cover, int userId){
		PreparedStatement statement;
		String movieSql = "INSERT INTO movies (title, runtime, genre, description, cover) VALUES (?, ?, ?, ?, ?)";

		try {
			statement = connection.prepareStatement(movieSql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setInt(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
			statement.setBytes(5, cover);
			statement.executeUpdate();
			
			
			// get last inserted id to store the relationship
			ResultSet rs = statement.getGeneratedKeys();
		    	rs.next();
		    int movieId = rs.getInt(1);
			addRelationship(userId, movieId);
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void addRelationship(int userId, int movieId){
		PreparedStatement statement;
		String relationShipSql = "INSERT INTO user_movies (user_id, movie_id) VALUES (?, ?)";
		try {
			statement = connection.prepareStatement(relationShipSql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, userId);
			statement.setInt(2, movieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
