package de.schule.media_collection.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteStreams;

import de.schule.media_collection.data.DataLayer;

public class Controller {
	List<Movie> movieList = new ArrayList<Movie>();
	List<User> userList = new ArrayList<User>();
	User currentUser;
	DataLayer dataConnector;
	public Controller(boolean useSQL) throws SQLException{
		try {
			dataConnector = new DataLayer(useSQL);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.printAllMovies();
		this.editMovie(1, "Der Hobbit", 140, "Fantasy", "TESTESTESTEST");
		this.printAllMovies();

	}
	
	public void addMovieToCollection(String title, int runtime, String genre, String description){
		dataConnector.addMovieAndRelationship(title, runtime, genre, description, 1);
	}
	public void addMovieRelationship(int userId , int movieId){
		dataConnector.addRelationship(userId, movieId);
	}
	public List<Movie> getAllMovies(){
		return dataConnector.getMoviesFromDatabase();
	}
	public List<User> getAllUser(){	
		return dataConnector.getUserFromDatabase();
	}
	public void editMovie(int movieId, String title, int runtime, String genre, String description){
		dataConnector.editMovie(movieId, title, runtime, genre, description);
	}
	public void printAllMovies(){
		movieList = getAllMovies();
		for(int i=0; i< movieList.size(); i++){
			System.out.println("-------");
			Movie currentMovie = movieList.get(i);
			System.out.print(currentMovie.getTitle() + " ");
			System.out.print(currentMovie.getDescription()+ " ");
			System.out.print(currentMovie.getRuntime());
			System.out.println("-------");
		}
	}

}


