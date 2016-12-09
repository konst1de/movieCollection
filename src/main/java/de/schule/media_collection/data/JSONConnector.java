package de.schule.media_collection.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * Class to handle all actions considering working with the JSON storage file. 
 * To Handle JSON we use the library org.json.simple (SOURCE: https://code.google.com/archive/p/json-simple/)
 * Possible Improvements:
 * 	- at the start we can insert some initial data like user (when there are none). 
 * 	- user management (methods to add user)
 * 	- file management (choose file name in constructor)
 * @author konstantinvogel
 *
 */
public class JSONConnector {
	private JSONObject storageJSON;
	private JSONArray user;
	private JSONArray movies;
	private JSONArray userMovies;
	public JSONConnector(){
		JSONParser parser = new JSONParser();
		File storageFile = new File("storage.json");
		// if the file does not exist we create a new one with some initial content.
		if(!storageFile.exists()){
			try {
				storageFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// if the file does not exist create one with the json structure and a default user
			storageJSON = new JSONObject();
			user = new JSONArray();
			movies = new JSONArray();
			userMovies = new JSONArray();
			// here the initial data is generated
			JSONObject defaultUser = new JSONObject();
			defaultUser.put("id", 1);
			defaultUser.put("username", "kv");
			defaultUser.put("firstname", "Konstantin");
			defaultUser.put("lastname", "Vogel");
			user.add(defaultUser);
			storageJSON.put("user", user);
			storageJSON.put("movies", movies);
			storageJSON.put("userMovies", userMovies);
			this.storeToFile();
		}else{
			Object obj = null;
			try {
				obj = parser.parse(new FileReader("storage.json"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			storageJSON = (JSONObject) obj;
			user = (JSONArray) storageJSON.get("user");
			movies = (JSONArray) storageJSON.get("movies");
			userMovies = (JSONArray) storageJSON.get("userMovies");	
			System.out.println(storageJSON.toJSONString());
		}
	}
	/**
	 * Method to get an JSON array of all user.
	 * @return JSONArray containing all user objects
	 */
	public JSONArray getUser() {
		return user;
	}
	/**
	 * Method to get an JSON array of all movies.
	 * @return JSONArray containing all the movie objects
	 */
	public JSONArray getMovies() {
		return movies;
	}

	/** 
	 * Method to get an JSON array of all the user and the movies in their collections.
	 * @return JSONArray containing all user movies
	 */
	public JSONArray getUserMovies() {
		return userMovies;
	}
	/**
	 * Method to get the id of the last movie in the JSONArray movies.
	 * @return Integer with the last movie id
	 */
	public int getLastMovieId(){
		JSONObject lastMovie = movies.size() > 0 ? (JSONObject) movies.get(movies.size()-1) : null;
		int lastId = 0;
		if(lastMovie != null)
		{
			lastId =  Integer.parseInt(lastMovie.get("id").toString());
		}
		return lastId;
	}
	/**
	 * Method to get the id of the last user in the JSONArray user.
	 * @return Integer with the last user id
	 */
	public int getLastUserId(){
		JSONObject lastUser = (JSONObject) user.get(user.size()-1);
		int lastId = 0;
		if(lastUser != null)
		{
			lastId = (Integer) lastUser.get("id");
		}
		return lastId;
	}
	/**
	 * Method to add a new movie to the JSONArray movies.
	 * @param title String with the title of the new movie
	 * @param runtime Integer with the runtime of the new movie
	 * @param genre String with the genre of the new movie
	 * @param description String containing the description of the new movie
	 * @param date LocalDate Object that contains the release date of the object.
	 */
	public void addMovie(String title, long runtime, String genre, String description, LocalDate date) {
		JSONObject movie = new JSONObject();
		int lastMovieId = getLastMovieId();
		lastMovieId++;
		movie.put("id", lastMovieId);
		movie.put("title", title);
		movie.put("runtime", runtime);
		movie.put("genre", genre);
		movie.put("description", description);
		// Store LocalDate as String in the JSON
		movie.put("releaseDate", date.toString());
		movies.add(movie);

	}
	/**
	 * Method to get a specific movie by its id. If there is no movie with the given id it returns null.
	 * @param movieId Integer of the movie id we are looking for
	 * @return JSONObject of the movie with the specific id
	 */
	public JSONObject getMovieById(int movieId){
		for(int i=0; i < movies.size(); i++){
			JSONObject currentMovie = (JSONObject) movies.get(i);
			int currentId = Integer.parseInt(currentMovie.get("id").toString());
			if(currentId == movieId){
				return currentMovie;
			}
		}
		return null;
	}
	/**
	 * Method to get a specific user by its id. If there is no user with the given id it returns null.
	 * @param userId
	 * @return
	 */
	public JSONObject getUserById(int userId){
		for(int i=0; i < user.size(); i++){
			JSONObject currentUser = (JSONObject) user.get(i);
			int currentId = Integer.parseInt(currentUser.get("id").toString());
			if(currentId == userId){
				return currentUser;
			}
		}
		return null;
	}
	/**
	 * Method to edit an existing movie. First we get the movie with the method this.getMovieById then we edit it and store it to the file.
	 * @param movieId Integer containing the movieId
	 * @param title String that contains the new title of the movie
	 * @param runtime Integer with the new runtime of the movie
	 * @param genre String with the new genre
	 * @param description String with the new description
	 * @param releaseDate LocalDate with the new release date
	 */
	public void editMovie(int movieId, String title, long runtime, String genre, String description, LocalDate releaseDate) {
		JSONObject movie = this.getMovieById(movieId);
		movie.put("title", title);
		movie.put("runtime", runtime);
		movie.put("genre", genre);
		movie.put("description", description);
		movie.put("releaseDate", releaseDate.toString());
		this.storeToFile();
	}
	
	/**
	 * Method to get add a movie to the user collection
	 * @param userId Integer containing id of the user which the new movie belongs to
	 * @param movieId Integer with the id of the new movie the user owns
	 */
	public void addMovieToCollection(int userId, int movieId) {
		JSONObject userMovie = new JSONObject();
		userMovie.put("userId", userId);
		userMovie.put("movieId", movieId);
		userMovies.add(userMovie);
		this.storeToFile();
	}
	/**
	 * Method to "store" (aka write) the current Objects to the file.
	 */
	private void storeToFile(){
		try {
			System.out.println(storageJSON.toJSONString());
			File file = new File("storage.json");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(storageJSON.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	/**
	 * Method to get the user who own the movie with the given id. Not yet used.
	 * @param movieId Integer containing the movie id
	 * @return JSONArray of the user who own the given movie
	 */
	public JSONArray getUserOwnMovie(int movieId){
		JSONArray userWhoOwnMovie = new JSONArray();
		for(int i=0; i<userMovies.size();i++){
			JSONObject currentUserMovie = (JSONObject) userMovies.get(i);
			int currentMovieId = Integer.parseInt((currentUserMovie.get("movieId").toString()));
			if(movieId == currentMovieId){
				int currentUserId = Integer.parseInt((currentUserMovie.get("userId").toString()));
				userWhoOwnMovie.add(this.getUserById(currentUserId));
			}
		}
		return userWhoOwnMovie;
	}
	/**
	 * Method to get the movies who are owned by the user.
	 * @param userId Integer with the id from the user
	 * @return JSONArray of the movies which are in the collection of the user.
	 */
	public JSONArray getMoviesOwnedByUser(int userId){
		JSONArray moviesOwnedByUser = new JSONArray();
		for(int i=0; i<userMovies.size();i++){
			JSONObject currentUserMovie = (JSONObject) userMovies.get(i);
			int currentUserId = Integer.parseInt((currentUserMovie.get("userId").toString()));
			if(userId == currentUserId){
				int currentMovieId = Integer.parseInt((currentUserMovie.get("movieId").toString()));
				moviesOwnedByUser.add(this.getMovieById(currentMovieId));
			}
		}
		return moviesOwnedByUser;
	}
	/**
	 * Method to remove movies from the collection of the user
	 * @param movieId Integer with the movie id
	 * @param userId Integer with the user id
	 */
	public void removeMovieFromUser(int movieId, int userId){
		for(int i=0; i < userMovies.size();i++){
			JSONObject currentUserMovie = (JSONObject) userMovies.get(i);
			int currentUserId = Integer.parseInt(currentUserMovie.get("userId").toString());
			int currentMovieId = Integer.parseInt(currentUserMovie.get("movieId").toString());
			if(currentUserId == userId && currentMovieId == movieId){
				userMovies.remove(i);
			}
			this.storeToFile();
		}
	}

}
