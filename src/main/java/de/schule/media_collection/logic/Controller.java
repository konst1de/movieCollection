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


	private User currentUser;
	private DataLayer dataConnector;
	public Controller(boolean useSQL) throws SQLException{
		try {
			dataConnector = new DataLayer(useSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setCurrentUser(User user){
		this.currentUser = user;
	}
	public User getCurrentUser(){
		return this.currentUser;
	}
	public void addExistingMovieToCollection(int movieId){
		Movie movie = dataConnector.getMovieById(movieId);
		dataConnector.addMovieToCollection(movie, this.currentUser);
	}
	public List<Movie> getAllMovies(){
		return dataConnector.getMoviesFromDatabase();
	}
	public List<User> getAllUser(){	
		return dataConnector.getUserFromDatabase();
	}

	public void editMovie(Movie movie,Boolean addToCollection){
		
		if(dataConnector.getMovieById(movie.getId()) == null){
			dataConnector.addMovie(movie, this.currentUser.getId(), addToCollection);
		}else{
			dataConnector.editMovie(movie);
		}
	}
	public void removeMovieFromCollection(int movieId){
		Movie movieToRemove = this.getMovieById(movieId);
		dataConnector.removeMovieFromCollection(movieToRemove, this.currentUser);
	}
	public List<Movie> getAllOwnedMovies(){
		return dataConnector.getAllOwnedMovies(this.currentUser);
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


