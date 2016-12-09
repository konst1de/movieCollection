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

public class DataLayer 
{
	
	// connection
    private Connection connection = null;

    private boolean useSQL;
    private JSONObject storageJSON = null;
    private JSONArray user = null;
    private JSONArray movies = null;
    private JSONArray userMovies = null;
    private SQLConnector sqlConnector;
    private JSONConnector jsonConnector;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DataLayer(boolean useSQL) throws SQLException{
    	this.useSQL = useSQL;
    	if(useSQL){
        	this.sqlConnector = new SQLConnector();
    	}else{
        	this.jsonConnector = new JSONConnector();
    	}
    }

	public List<Movie> getMoviesFromDatabase(){
		List<Movie> ls = new ArrayList<Movie>();
		
		if(useSQL){
	        ResultSet rs = sqlConnector.getMovies();
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					int runtime = rs.getInt("runtime");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String genre = rs.getString("genre");
					LocalDate releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
					Movie movie = new Movie(id, runtime, title, genre, description, releaseDate);
					ls.add(movie);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			movies = jsonConnector.getMovies();
			if (movies != null) { 
				for (int i=0;i<movies.size();i++){ 
					JSONObject movieObj = (JSONObject) movies.get(i);
				   	int id = Integer.parseInt(movieObj.get("id").toString());
					int runtime = Integer.parseInt(movieObj.get("runtime").toString());
					String title = (String) movieObj.get("title");
					String description = (String) movieObj.get("description");
					String genre = (String) movieObj.get("genre");
					LocalDate releaseDate = movieObj.get("releaseDate") != null ?  LocalDate.parse((String) movieObj.get("releaseDate"), DATE_FORMAT) : null; 

					Movie movie = new Movie(id, runtime, title, genre, description, releaseDate);
					ls.add(movie);
				} 
			} 
		}
		return ls;
	}
	public List<User> getUserFromDatabase(){
        List<User> ls = new ArrayList<User>();
		if(useSQL){
	        ResultSet rs = sqlConnector.getUser();
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("firstname");
					String userName = rs.getString("username");
					String lastName = rs.getString("lastname");
					User user = new User(id, userName, firstName, lastName);
					ls.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			user = jsonConnector.getUser();
			if (user != null) { 
				for (int i=0;i<user.size();i++){ 
				   	JSONObject userObject = (JSONObject) user.get(i);
				   	int id = Integer.parseInt(userObject.get("id").toString());
					String username = (String) userObject.get("username");
					String firstname = (String) userObject.get("firstname");
					String lastname = (String) userObject.get("lastname");
					User user = new User(id, username, firstname, lastname);
					ls.add(user);		
				} 
			} 
		}
		return ls;
	}
	public void addMovie(Movie movie){
		String title = movie.getTitle();
		int runtime = (int) movie.getRuntime();
		String genre = movie.getGenre();
		String description = movie.getDescription();
		LocalDate date = movie.getReleaseDate();
		if(useSQL){
			sqlConnector.addMovie(title, runtime, genre, description, date);
		}else{
			jsonConnector.addMovie(title, runtime, genre, description, date);
		}
		

	}
	
	public void addMovieToCollection(Movie movie, User user){
		if(useSQL){
			sqlConnector.addMovieToCollection(user.getId(), movie.getId());
		}else{
			jsonConnector.addMovieToCollection(user.getId(), movie.getId());
		}
	}
	public void editMovie(Movie movie){
		int movieId = movie.getId();
		String title = movie.getTitle();
		long runtime = movie.getRuntime();
		String genre = movie.getGenre();
		String description = movie.getDescription();
		LocalDate relaseDate = movie.getReleaseDate();
		if(useSQL){
			sqlConnector.editMovie(movieId, title, runtime, genre, description, relaseDate);
		}else{
			jsonConnector.editMovie(movieId, title, runtime, genre, description, relaseDate);
		}
	}
    public List<User> getUserWhoOwnMovie(Movie movie){
    	List<User> ownerList = new ArrayList<User>();
    	if(useSQL){
	        ResultSet rs = sqlConnector.getUserOwnMovie(movie.getId());
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("firstname");
					String userName = rs.getString("username");
					String lastName = rs.getString("lastname");
					ownerList.add(new User(id, userName, firstName, lastName));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			user = jsonConnector.getUserOwnMovie(movie.getId());
			if (user != null) { 
				for (int i=0;i<user.size();i++){ 
				   	JSONObject userObject = (JSONObject) user.get(i);
				   	int id = Integer.parseInt(userObject.get("id").toString());
					String username = (String) userObject.get("username");
					String firstname = (String) userObject.get("firstname");
					String lastname = (String) userObject.get("lastname");
					ownerList.add(new User(id, username, firstname, lastname));		

				} 
			} 
		}
    	return ownerList;
    }

	public List<Movie> getAllOwnedMovies(User user) {
		int userId = user.getId();
		// TODO Auto-generated method stub
		List<Movie> movieList = new ArrayList<Movie>();
    	if(useSQL){
	        ResultSet rs = sqlConnector.getMoviesOwnedByUser(userId);
			try {
				while (rs.next()) {
					int id = rs.getInt("id");
					int runtime = rs.getInt("runtime");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String genre = rs.getString("genre");
					LocalDate releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
					movieList.add(new Movie(id, runtime, title, genre, description, releaseDate));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			movies = jsonConnector.getMoviesOwnedByUser(userId);
			if (movies != null) { 
				for (int i=0;i<movies.size();i++){ 
				   	JSONObject movieJSON= (JSONObject) movies.get(i);
				   	int id = Integer.parseInt(movieJSON.get("id").toString());
				   	int runtime = Integer.parseInt(movieJSON.get("id").toString());
					String title = (String) movieJSON.get("title");
					String description = (String) movieJSON.get("description");
					String genre = (String) movieJSON.get("genre");
					LocalDate releaseDate = movieJSON.get("releaseDate") != null ?  LocalDate.parse((String) movieJSON.get("releaseDate"), DATE_FORMAT) : null; 
					movieList.add(new Movie(id, runtime, title, genre, description, releaseDate));
				} 
			} 
		}
    	return movieList;
	}

	public void removeMovieFromCollection(Movie movie, User user) {
		// TODO Auto-generated method stub
		
		if(useSQL){
			sqlConnector.removeMovieFromUser(movie.getId(), user.getId());
		}else{
			jsonConnector.removeMovieFromUser(movie.getId(), user.getId());
		}
	}

	public User getUserById(int id) {
		int userId = id;
		String userName = null;
		String firstName = null;
		String lastName = null;
		if(useSQL){
			ResultSet rs = sqlConnector.getUserById(id);
			try {
				if(rs.first()){
					userId = rs.getInt("id");
					firstName = rs.getString("firstname");
					userName = rs.getString("username");
					lastName = rs.getString("lastname");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			JSONObject userObject = jsonConnector.getUserById(id);
		   	userId = Integer.parseInt(userObject.get("id").toString());
			userName = (String) userObject.get("username");
			firstName = (String) userObject.get("firstname");
			lastName = (String) userObject.get("lastname");
		}
		if(userName == null){
			return null;
		}
		return new User(userId, userName, firstName, lastName);
	}

	public Movie getMovieById(int id) {
		int movieId = id;
		int runtime = 0;
		String title = null;
		String description = null;
		String genre = null;
		LocalDate releaseDate = null;
		if(useSQL){
			ResultSet rs = sqlConnector.getMovieById(id);
			try {
				if(rs.first()){
					movieId = rs.getInt("id");
					runtime = rs.getInt("runtime");
					title = rs.getString("title");
					description = rs.getString("description");
					genre = rs.getString("genre");
					releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JSONObject movieJSON = jsonConnector.getMovieById(id);
			if(movieJSON != null){
				movieId = Integer.parseInt(movieJSON.get("id").toString());
			   	runtime = Integer.parseInt(movieJSON.get("id").toString());
				title = (String) movieJSON.get("title");
				description = (String) movieJSON.get("description");
				genre = (String) movieJSON.get("genre");
				releaseDate = movieJSON.get("releaseDate") != null ?  LocalDate.parse((String) movieJSON.get("releaseDate"), DATE_FORMAT) : null; 
			}
		}
		if(movieId == 0){
			return null;
		}
		return new Movie(movieId, runtime, title, genre, description, releaseDate);
	}
}
