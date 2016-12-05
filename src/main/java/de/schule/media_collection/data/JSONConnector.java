package de.schule.media_collection.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONConnector {
	private JSONObject storageJSON;
	private JSONArray user;
	private JSONArray movies;
	private JSONArray userMovies;
	public JSONConnector(){
		// use json file to store data
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader("/Users/konstantinvogel/Documents/workspace_schule/media_collection/storage.json"));
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
		 
	}


	public JSONArray getUser() {
		return user;
	}

	public JSONArray getMovies() {
		return movies;
	}


	public JSONArray getUserMovies() {
		return userMovies;
	}


	public int getLastMovieId(){
		JSONObject lastMovie = (JSONObject) movies.get(movies.size()-1);
		int lastId = 0;
		if(lastMovie != null)
		{
			lastId =  Integer.parseInt(lastMovie.get("id").toString());
		}
		return lastId;
	}
	public int getLastUserId(){
		JSONObject lastUser = (JSONObject) user.get(user.size()-1);
		int lastId = 0;
		if(lastUser != null)
		{
			lastId = (Integer) lastUser.get("id");
		}
		return lastId;
	}
	public void addMovieAndRelationship(String title, int runtime, String genre, String description,
			int userId) {
		JSONObject movie = new JSONObject();
		int lastMovieId = getLastMovieId();
		movie.put("id", lastMovieId);
		movie.put("title", title);
		movie.put("runtime", runtime);
		movie.put("genre", genre);
		movie.put("description", description);
		movies.add(movie);
		addRelationship(userId, lastMovieId);
		lastMovieId++;
	}
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
	public void editMovie(int movieId, String title, int runtime, String genre, String description) {
		JSONObject movie = getMovieById(movieId);
		int lastMovieId = getLastMovieId();
		movie.put("id", lastMovieId);
		movie.put("title", title);
		movie.put("runtime", runtime);
		movie.put("genre", genre);
		movie.put("description", description);
		this.storeToFile();
	}
	

	public void addRelationship(int userId, int movieId) {
		JSONObject userMovie = new JSONObject();
		userMovie.put("userId", userId);
		userMovie.put("userId", movieId);
	}
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

}
