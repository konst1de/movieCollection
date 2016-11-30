package de.schule.media_collection.Control;

public class Movie{
	int id;
	int runtime;
	String title;
	String genre;
	String description;
	public Movie(int id, int runtime, String title, String genre, String description) {
		this.id = id;
		this.title = title;
		this.runtime = runtime;
		this.genre = genre;
		this.description = description;
	}
}