package de.schule.media_collection.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteStreams;

import de.schule.media_collection.data.DataLayer;
/**
 * Class for the logic layer. It communicates with the data layer and takes commands of the view layer. 
 * @author konstantinvogel
 *
 */
public class Controller {
	private User currentUser;
	private DataLayer dataConnector;
	/**
	 * Constructor which has a boolean parameter and initalizes the datalayer with that boolean.
	 * @param useSQL boolean which decides wether we use sql or json
	 * @throws SQLException
	 */
	public Controller(boolean useSQL) throws SQLException{
		try {
			dataConnector = new DataLayer(useSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Setter for the currentUser. For the most commands the currentUser has to be set.
	 * @param user 
	 */
	public void setCurrentUser(User user){
		this.currentUser = user;
	}
	/**
	 * Getter for the currentUser.
	 * @return User returns the currentUser
	 */
	public User getCurrentUser(){
		return this.currentUser;
	}
	/**
	 * Method to add an existing movie to the user collection. 
	 * Calling data layer method to add a movie to the collection.
	 * @param movieId Integer containing the id of the movie
	 */
	public void addExistingMovieToCollection(int movieId){
		Movie movie = dataConnector.getMovieById(movieId);
		dataConnector.addMovieToCollection(movie, this.currentUser);
	}
	/**
	 * Method to get an ArrayList containing all movie objects.
	 * @return List<Movie> with all movies
	 */
	public List<Movie> getAllMovies(){
		return dataConnector.getMoviesFromDatabase();
	}
	/**
	 * Method to get an ArrayList containing all user objects.
	 * @return List<User> with all user
	 */
	public List<User> getAllUser(){	
		return dataConnector.getUserFromDatabase();
	}
	/**
	 * Method to edit and add a movie. 
	 * Check whether the id of the movie already exists and if not it adds the movie and otherwise it will be just edited.
	 * @param movie Movie object of the movie that should be edited/added
	 */
	public void editMovie(Movie movie){
		if(dataConnector.getMovieById(movie.getId()) == null){
			dataConnector.addMovie(movie);
		}else{
			dataConnector.editMovie(movie);
		}
	}
	/**
	 * Method to remove a movie from the users collection.
	 * @param movieId Integer of the movie id which has to be deleted
	 */
	public void removeMovieFromCollection(int movieId){
		Movie movieToRemove = this.getMovieById(movieId);
		dataConnector.removeMovieFromCollection(movieToRemove, this.currentUser);
	}
	/**
	 * Method to get an ArrayList containing all movie objects that are in the user collection.
	 * @return List<Movie> with all movies that are owned by the user
	 */
	public List<Movie> getAllOwnedMovies(){
		return dataConnector.getAllOwnedMovies(this.currentUser);
	}
	/**
	 * Method to get all owner for a movie. 
	 * @param movie Movie object of the movie we are getting the owners for
	 * @return List<User> of the owner of the given movie
	 */
	public List<User> getOwnerForMovie(Movie movie){
		return dataConnector.getUserWhoOwnMovie(movie);
	}
	/**
	 * Method to get a specific movie by its id and if there is none it returns null.
	 * @param id Integer id of the movie we are looking for
	 * @return Movie object if the movie is found or null
	 */
	public Movie getMovieById(int id){
		return dataConnector.getMovieById(id);
	}
	/**
	 * Method to get a specific user by its id and if there is none it returns null.
	 * @param id Integer id of the user we are looking for
	 * @return User object if the user is found or null
	 */
	public User getUserById(int id){
		return dataConnector.getUserById(id);
	}
	/**
	 * Method to check if the current user owns a specific movie.
	 * @param movie Movie object that should be checked
	 * @return Boolean returns true when the user owns the given movie or false if not
	 */
	public Boolean isMovieOwnedByUser(Movie movie){
		return dataConnector.isMovieOwned(movie, currentUser);
	}
}


