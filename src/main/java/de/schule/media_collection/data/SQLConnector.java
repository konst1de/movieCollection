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

/**
 * Class which handles the SQL Connection and does the queries which are executed on the local database.
 * Possible Improvements:
 * 	- database connection credentials could be initialized in the constructor (to be able to switch databases)
 * 	- user management (method to add / edit and remove user)
 * @author konstantinvogel
 *
 */
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
    /** 
     * Method to return all movies that are in the database table.
     * @return a ResultSet with all Movies
     */
    public ResultSet getMovies(){
    	String query = "select * from movies";
        Statement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.createStatement();
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
    }
    /** 
     * Method to get a specific movie by a given id and if a movie with the given id does not exist it returns null.
     * @param id an Integer containing the movie ID which we are looking for
     * @return the movie with the id or null
     */
    public ResultSet getMovieById(int id){
    	String query = "select * from movies WHERE id = " + id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
    }
    /** 
     * Method to return all user that are in the database table.
     * @return A ResultSet with all User
     */
    public ResultSet getUser(){
    	String query = "select * from user";
        Statement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.createStatement();
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
    }
    /**
     * Method to return the user movie relationships. 
     * @return a ResultSet containing the userMovies
     */
    public ResultSet getUserMovies(){
    	String query = "select * from user_movies";
        Statement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.createStatement();
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
    }
    /**
     * Method to receive a specific user by his id. If no user with the id exists it returns null.
     * @param An integer containing an id if a user
     * @return
     */
    public ResultSet getUserById(int id) {
    	String query = "select * from user WHERE id = "+ id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query);
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
	}
    /**
     * Method to add a new movie to the database table movies
     * @param title String of the new movie name
     * @param runtime Integer containing the runtime of the new movie
     * @param genre String containing the genre of the new movie
     * @param description String containing the description of the new movie
     * @param date LocalDate object of the release Date
     */
    public void addMovie(String title, long runtime, String genre, String description, LocalDate date){
    	PreparedStatement statement;
		String movieSql = "INSERT INTO movies (title, runtime, genre, description, release_date) VALUES (?, ?, ?, ?, ?)";
		try {
			statement = this.connection.prepareStatement(movieSql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setLong(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
			statement.setDate(5, date != null ? Date.valueOf(date) : null);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
		    rs.next();
		    int movieId = rs.getInt(1);

		} catch (SQLException e) {
			System.out.println("INSERT failed: "+ e);
		}
    }
    /**
     * Method to add a new movie to a users collection
     * @param userId Integer with the id of the user
     * @param movieId Integer with the id of the movie 
     */
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
			System.out.println("INSERT failed: "+ e);
		}
    }

    /**
     * Method to edit an existing movie.
     * @param movieId
     * @param title
     * @param runtime
     * @param genre
     * @param description
     * @param relaseDate
     */
	public void editMovie(int movieId, String title, long runtime, String genre, String description, LocalDate relaseDate) {
		// TODO Auto-generated method stub
		PreparedStatement statement;
		String editSQL = "UPDATE movies set title = ?, runtime = ?, genre = ?, description = ? , release_date = ? WHERE id = ?";
		
		try {
			statement = connection.prepareStatement(editSQL, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setLong(2, runtime);
			statement.setString(3, genre);
			statement.setString(4, description);
			statement.setDate(5, relaseDate != null ? Date.valueOf(relaseDate) : null);
			statement.setInt(6, movieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("UPDATE failed: "+ e);
		}
	}
	/**
	 * Method to get a ResultSet of the User who own a specific movie
	 * @param movieId Integer of the movieId we want the owner of.
	 * @return ResultSet of the user who own the movie with the given id
	 */
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
			System.out.println("SELECT failed: "+ e);
		}
	    return rs;
	}
	/**
	 * Method to get a ResultSet of the Movies who a specific user owns
	 * @param userId Integer of the user we want the movies of.
	 * @return ResultSet Containing the movies the user with the given id owns.
	 */
	public ResultSet getMoviesOwnedByUser(int userId) {
		String query = "select * from movies WHERE id IN(select movie_id from user_movies where user_id="+userId+")";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
			stmt = this.connection.prepareStatement(query);
	        rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SELECT failed: "+ e);
		}
        return rs;
	}
	/**
	 * Method to remove a movie from the collection of the user.
	 * @param movieId Integer containing the movie id
	 * @param userId Integer containing the user id
	 */
	public void removeMovieFromUser(int movieId, int userId) {
		PreparedStatement statement;
		String deleteOwnershipSQL = "DELETE FROM user_movies WHERE user_id = ? AND movie_id = ?";
		try {
			statement = this.connection.prepareStatement(deleteOwnershipSQL);
			statement.setInt(1, userId);
			statement.setInt(2, movieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETING failed: "+ e);
		}
		
	}
	/**
	 * Method that check if a user owns a specific movie and returns a boolean.
	 * @param movieId Integer id of the movie that has to be checked
	 * @param userId Integer id of the user that has to be checked
	 * @return Boolean true if its owned and false if not
	 */
	public boolean isMovieOwnedByUser(int movieId, int userId) {
		PreparedStatement statement;
		String checkOwnershipSQL = "SELECT * FROM user_movies WHERE user_id = ? AND movie_id = ?";
		ResultSet rs = null;
		boolean isOwned = false;
		try {
			statement = this.connection.prepareStatement(checkOwnershipSQL);
			statement.setInt(1, userId);
			statement.setInt(2, movieId);
			rs = statement.executeQuery();
			if (rs.isBeforeFirst() ) {
				isOwned = true;
			}
		} catch (SQLException e) {
			System.out.println("CHECKING OWNERSHIP failed: "+ e);
		} 
		return isOwned;
	}
	/**
	 * Method to delete the movie from the database. Since the userMovies has set ON DELETE: Cascade it deletes itself
	 * @param movieId Integer of the id from the movie that wants to be deleted.
	 */
	public void deleteMovie(int movieId) {
		PreparedStatement statement;
		String deleteMovieSQL = "DELETE FROM movies WHERE id = ?";
		try {
			statement = this.connection.prepareStatement(deleteMovieSQL);
			statement.setInt(1, movieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DELETING failed: "+ e);
		}		
	}
}
