package de.schule.media_collection.logic;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {
	
	private final IntegerProperty id;
	private final LongProperty runtime;
	private final StringProperty title;
	private final StringProperty genre;
	private final StringProperty description;
	private LocalDate releaseDate;
	public Movie() {
		this( 0, 0, null, null, null, null);
	}

	public Movie(int id, int runtime, String title, String genre, String description, LocalDate releaseDate) {
		this.id = new SimpleIntegerProperty(id);
		this.title = new SimpleStringProperty(title);
		this.runtime = new SimpleLongProperty(runtime);
		this.genre = new SimpleStringProperty(genre);
		this.description = new SimpleStringProperty(description);
		this.releaseDate = releaseDate;
	}
	
	public IntegerProperty idProperty() {
		return this.id;
	}

	public int getId() {
		return this.idProperty().get();
	}

	public void setId(int id) {
		this.idProperty().set(id);
	}

	public LongProperty runtimeProperty() {
		return this.runtime;
	}
	

	public long getRuntime() {
		return this.runtimeProperty().get();
	}
	

	public void setRuntime(final long runtime) {
		this.runtimeProperty().set(runtime);
	}
	

	public StringProperty titleProperty() {
		return this.title;
	}
	

	public String getTitle() {
		return this.titleProperty().get();
	}
	

	public void setTitle(final String title) {
		this.titleProperty().set(title);
	}
	

	public StringProperty genreProperty() {
		return this.genre;
	}
	

	public String getGenre() {
		return this.genreProperty().get();
	}
	

	public void setGenre(final String genre) {
		this.genreProperty().set(genre);
	}
	

	public StringProperty descriptionProperty() {
		return this.description;
	}
	

	public String getDescription() {
		return this.descriptionProperty().get();
	}
	

	public void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public LocalDate getReleaseDate() {
		return this.releaseDate;
	}
	
}