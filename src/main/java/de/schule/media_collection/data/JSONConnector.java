package de.schule.media_collection.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

}
