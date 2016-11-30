package de.schule.media_collection.Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteStreams;

import de.schule.media_collection.Data.DataLayer;

public class Controller {
	List<Movie> movieList = new ArrayList<Movie>();
	List<User> userList = new ArrayList<User>();
	User currentUser;
	DataLayer dataConnection;
	public Controller(boolean useSQL) throws SQLException{
		try {
			dataConnection = new DataLayer(useSQL);
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

		dataConnection.addMovieAndRelationship(title, runtime, genre, description, bytes, 1);
	}
	public void addMovieRelationship(int userId , int movieId){
		dataConnection.addRelationship(userId, movieId);
	}
	public List<Movie> getAllMovies(){
		List<Movie> ls = new ArrayList<Movie>();
			int id;
			int runtime;
			String title;
			String description;
			String genre;
			try {
				ResultSet rs = dataConnection.getMoviesFromDatabase();
				while (rs.next()) {
				id = rs.getInt("id");
				runtime = rs.getInt("runtime");
				title = rs.getString("title");
				description = rs.getString("description");
				genre = rs.getString("genre");
				Movie movie = new Movie(id, runtime, title, description, genre);
				ls.add(movie);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return ls;
	}
	public List<User> getAllUser(){
		List<User> ls = new ArrayList<User>();
			int id;
			String userName;
			String firstName;
			String lastName;
			try {
				ResultSet rs = dataConnection.getMoviesFromDatabase();
				while (rs.next()) {
				id = rs.getInt("id");
				firstName = rs.getString("firstname");
				userName = rs.getString("username");
				lastName = rs.getString("lastname");
				User user = new User(id, userName, firstName, lastName);
				ls.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return ls;
	}

}


