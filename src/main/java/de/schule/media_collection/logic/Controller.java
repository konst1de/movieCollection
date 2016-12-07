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

public class Controller {


	User currentUser;
	DataLayer dataConnector;
	public Controller(boolean useSQL) throws SQLException{
		try {
			dataConnector = new DataLayer(useSQL);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMovieToCollection(String title, int runtime, String genre, String description, int userId, LocalDate date){
		dataConnector.addMovieAndRelationship(title, runtime, genre, description, userId, date);
	}
	public void addExistingMovieToCollection(int movieId, User user){
		Movie movie = dataConnector.getMovieById(movieId);
		dataConnector.addMovieToCollection(movie, user);
	}
	public List<Movie> getAllMovies(){
		return dataConnector.getMoviesFromDatabase();
	}
	public List<User> getAllUser(){	
		return dataConnector.getUserFromDatabase();
	}
	public List<User> getMovieById(){	
		return dataConnector.getUserFromDatabase();
	}
	public void editMovie(Movie movie){
		dataConnector.editMovie(movie);
	}
	public void removeMovieFromCollection(int movieId, User user){
		Movie movieToRemove = this.getMovieById(movieId);
		dataConnector.removeMovieFromCollection(movieToRemove, user);
	}
	public List<Movie> getAllOwnedMovies(User user){
		return dataConnector.getAllOwnedMovies(user);
	}
	public List<User> getOwnerForMovie(Movie movie){
		return dataConnector.getUserWhoOwnMovie(movie);
	}
	public Movie getMovieById(int id){
		return dataConnector.getMovieById(id);
	}
	public User getUserById(int id){
		return dataConnector.getUserById(id);
	}
}


