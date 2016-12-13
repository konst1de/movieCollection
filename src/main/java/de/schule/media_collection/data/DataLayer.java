package de.schule.media_collection.data;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.schule.media_collection.logic.Movie;
import de.schule.media_collection.logic.User;

import java.sql.PreparedStatement;
/**
 * Class for the communication with sql / json.
 * It initalizes a json or sql controller depening on the arguments it becomes in the constructor.
 * Possible Improvements:
 * 	- inheritance for only using 1 data controller instead of 2 different
 * 	- exception handling
 * @author konstantinvogel
 *
 */
public class DataLayer 
{
	
	// connection
    private Connection connection = null;

    private DataConnector connector = null;
	/**
	 * Constructor of the DataLayer class. Depending on parameter initializing sql or json connector.
	 * @param useSQL BOOLEAN which decides what type of storage we are using
	 * @throws SQLException
	 */
    public DataLayer(boolean useSQL) throws SQLException{
    	connector = useSQL ? new SQLConnector() : new JSONConnector();
    	
    }
    /**
     * Method to get all movies from sql or json storage.
	 * When using sql its iterating through a ResultSet and writing it to an List<Movie>.
	 * When using JSON its iterating through a JSONArray and writing it into a List<Movie>.
     * @return List<Movie> of all movies which are storaged
     */
	public List<Movie> getMoviesFromDatabase(){
		return this.connector.getMovies();
	}
	/**
	 * Method to get all user from data storage (json or sql)
	 * Iterating through resultset or jsonarray and writing the data from it to a List<User>
	 * @return List<User> containing all user
	 */
	public List<User> getUserFromDatabase(){
		return this.connector.getUser();
	}
	/**
	 * Method to add movie to the sql database or json object / file
	 * Receiving a movie and getting the single values of the movie and passing it to the json or sql connector
	 * @param movie Movie object which should be added to the database / json file
	 */
	public void addMovie(Movie movie){
		if(movie != null){
			String title = movie.getTitle();
			int runtime = (int) movie.getRuntime();
			String genre = movie.getGenre();
			String description = movie.getDescription();
			LocalDate date = movie.getReleaseDate();
			this.connector.addMovie(title, runtime, genre, description, date);
		}
	}
	/**
	 * Method to add a movie to the userMovies storage
	 * @param movie Movie object that should be stored to the collection
	 * @param user User object of the user adding the movie
	 */
	public void addMovieToCollection(Movie movie, User user){
		this.connector.addMovieToCollection(user.getId(), movie.getId());
	}
	/**
	 * Method to edit an existing movie.
	 * @param movie Movie object that will be splitted to the single variables
	 */
	public void editMovie(Movie movie){
		int movieId = movie.getId();
		String title = movie.getTitle();
		long runtime = movie.getRuntime();
		String genre = movie.getGenre();
		String description = movie.getDescription();
		LocalDate relaseDate = movie.getReleaseDate();
		this.connector.editMovie(movieId, title, runtime, genre, description, relaseDate);
		
	}
	/**
	 * Method to get users that own a specific movie.
	 * @param movie Movie object of the movie we want the owners of
	 * @return List<User> ArrayList of User objects that own a specific movie
	 */
    public List<User> getUserWhoOwnMovie(Movie movie){
    	return this.connector.getUserOwnMovie(movie.getId());
    }
    /**
     * Method to get all Movies which a specific user owns
     * @param user User Object we want to movies of
     * @return List<Movie> ArrayList of the movies that are owned by the given user
     */
	public List<Movie> getAllOwnedMovies(User user) {
		return this.connector.getMoviesOwnedByUser(user.getId());
	}
	/**
	 * Method to remove a specific movie from a collection of a user
	 * @param movie Movie object that should be deleted from the collection
	 * @param user User object that specifies the collection from what the movie should be deleted
	 */
	public void removeMovieFromCollection(Movie movie, User user) {		
		this.connector.removeMovieFromUser(movie.getId(), user.getId());
	}
	/**
	 * Method to check if a user owns a specific movie.
	 * @param movie Movie object that should be checked
	 * @param user User object that should be checked
	 * @return Boolean returns true when the user owns the given movie or false if not
	 */
	public Boolean isMovieOwned(Movie movie, User user){
		return this.connector.isMovieOwnedByUser(movie.getId(), user.getId());
	}
	/**
	 * Method to receive a specific user depending on id.
	 * @param id Integer id of the user we want the object from. Returns null if the user does not exist
	 * @return User object of the user with the given id or null if the user does not exist
	 */
	public User getUserById(int id) {
		return this.connector.getUserById(id);
	}
	/**
	 * Method to receive a specific movie depending on id.
	 * @param id Integer id of the movie we want the object from. Returns null if the movie does not exist
	 * @return Movie object of the movie with the given id or null if the movie does not exist
	 */
	public Movie getMovieById(int id) {
		return this.connector.getMovieById(id);
	}
	public void deleteMovie(Movie movie) {
		this.connector.deleteMovie(movie.getId());
	}
}
