package de.schule.media_collection.data;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.schule.media_collection.logic.Movie;

import java.sql.Connection;
public class SQLConnector {
	
	private String serverName = "localhost";
	private String dbName = "media_collection";
	private String mySQLUrl = "jdbc:mysql://" + this.serverName + "/" + this.dbName; 
    private String username = "root";
    private String password = "";
    private Connection connection;
    public SQLConnector() throws SQLException{
    	this.connection = connect();
    }
    
    private Connection connect() throws SQLException{
		return DriverManager.getConnection(this.mySQLUrl, this.username, this.password);
    }
    
    public ResultSet getMovies(){
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
    public ResultSet getMovieById(int id){
    	String query = "select * from movies WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setMaxRows(1); 

	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs;
    }
    
    public ResultSet getUser(){
    	String query = "select * from user";
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
    public ResultSet getUserMovies(){
    	String query = "select * from user_movies";
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
    public ResultSet getUserById(int id) {
    	String query = "select * from user WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setMaxRows(1); 
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs;
	}
    public void addMovieAndRelationship(String title, int runtime, String genre, String description, int userId){
    	PreparedStatement statement;
		String movieSql = "INSERT INTO movies (title, runtime, genre, description, cover) VALUES (?, ?, ?, ?, ?)";
		try {
			statement = this.connection.prepareStatement(movieSql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setInt(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
//			statement.setBytes(5, cover);
			statement.executeUpdate();
			
			// get last inserted id to store the relationship
			ResultSet rs = statement.getGeneratedKeys();
		    	rs.next();
		    int movieId = rs.getInt(1);
		    if(userId != 0){
				addMovieToCollection(userId, movieId);
		    }
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void addMovieToCollection(int userId, int movieId){
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

	public void editMovie(int movieId, String title, int runtime, String genre, String description) {
		// TODO Auto-generated method stub
		
	}

	public ResultSet getUserOwnMovie(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getMoviesOwnedByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeMovieFromUser(int id, int id2) {
		// TODO Auto-generated method stub
		
	}

	

}
