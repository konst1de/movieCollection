package de.schule.media_collection.data;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;

import de.schule.media_collection.logic.Movie;

import java.sql.Connection;
import java.sql.Date;
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
    	String query = "select * from movies WHERE id = " + id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
    	String query = "select * from user WHERE id = "+ id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query);
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs;
	}
    public void addMovie(String title, long runtime, String genre, String description, LocalDate date, Boolean addToCollection, int userId){
    	PreparedStatement statement;
		String movieSql = "INSERT INTO movies (title, runtime, genre, description, release_date) VALUES (?, ?, ?, ?, ?)";
		try {
			statement = this.connection.prepareStatement(movieSql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setLong(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
			statement.setDate(5, Date.valueOf(date));
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
		    rs.next();
		    int movieId = rs.getInt(1);
		    if(addToCollection){
		    	this.addMovieToCollection(userId, movieId);
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

	public void editMovie(int movieId, String title, long runtime, String genre, String description, LocalDate relaseDate) {
		// TODO Auto-generated method stub
		PreparedStatement statement;
		String editSQL = "UPDATE movies set title = ?, runtime = ?, genre = ?, description = ? , release_date = ?";
		
		try {
			statement = connection.prepareStatement(editSQL, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setLong(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
			statement.setDate(5, Date.valueOf(relaseDate));
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet getUserOwnMovie(int movieId) {
		// TODO Auto-generated method stub
		PreparedStatement statement;
		String ownedMoviesSQL = "SELECT * FROM user WHERE id in (SELECT user_id from user_movies WHERE movie_id = ?)";
		ResultSet rs = null;
		try {
			statement = this.connection.prepareStatement(ownedMoviesSQL);
			statement.setInt(1, movieId);
			rs = statement.executeQuery(ownedMoviesSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return rs;
	}

	public ResultSet getMoviesOwnedByUser(int userId) {
		String query = "select * from movies WHERE id IN(select movie_id from user_movies where user_id="+userId+")";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query);
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs;
	}

	public void removeMovieFromUser(int movieId, int userId) {
		PreparedStatement statement;
		String deleteOwnershipSQL = "DELETE FROM user_movies WHERE user_id = ? AND movie_id = ?";
		try {
			statement = this.connection.prepareStatement(deleteOwnershipSQL);
			statement.setInt(1, userId);
			statement.setInt(2, movieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
