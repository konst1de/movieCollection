package de.schule.media_collection.Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.google.common.io.ByteStreams;

import de.schule.media_collection.Data.DatabaseConnector;

public class Controller {
	DatabaseConnector dbConn;
	public Controller(){
		try {
			dbConn = new DatabaseConnector();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void addMovieToCollection(int userId, String title, String release_date, int runtime, String genre, String description){

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

		dbConn.addMovie(title, runtime, genre, description, bytes);
	}
}
