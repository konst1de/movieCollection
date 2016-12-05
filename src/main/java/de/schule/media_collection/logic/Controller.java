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
		this.movieList = this.getAllMovies();

	}
	
	public void addMovieToCollection(String title, int runtime, String genre, String description){
		byte[] bytes = null;
		InputStream is;
		try {
			is = new FileInputStream("/Users/konstantinvogel/Documents/workspace_schule/media_collection/cover.jpg");
			bytes = ByteStreams.toByteArray(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataConnector.addMovieAndRelationship(title, runtime, genre, description, bytes, 1);
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

}


