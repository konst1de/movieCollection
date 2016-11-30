package de.schule.media_collection.Control;

public class Movie{
	private int id;
	private int runtime;
	private String title;
	private String genre;
	private String description;
	public Movie(int id, int runtime, String title, String genre, String description) {
		this.id = id;
		this.title = title;
		this.runtime = runtime;
		this.genre = genre;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}