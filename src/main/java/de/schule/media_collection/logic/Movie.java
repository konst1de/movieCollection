package de.schule.media_collection.logic;

import java.sql.Timestamp;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * General class that provides informations given by the datalayer
 * @author ReneSachse
 *
 */
public class Movie implements Comparable<Movie> {
	
	private final IntegerProperty id;
	private final LongProperty runtime;
	private final StringProperty title;
	private final StringProperty genre;
	private final StringProperty description;
	private final ObjectProperty<LocalDate> releaseDate;
	
	/**
	 * Default Constructor
	 */
	public Movie() {
		this( 0, 0, null, null, null, null);
	}
	
	/**
	 * Constructor filled with parameters without id
	 * @param runtime
	 * @param title
	 * @param genre
	 * @param description
	 * @param releaseDate
	 */
	public Movie(int runtime, String title, String genre, String description, LocalDate releaseDate) {
		this.id = new SimpleIntegerProperty(0);
		this.title = new SimpleStringProperty(title);
		this.runtime = new SimpleLongProperty(runtime);
		this.genre = new SimpleStringProperty(genre);
		this.description = new SimpleStringProperty(description);
		this.releaseDate = new SimpleObjectProperty<LocalDate>(releaseDate);
	}

	/**
	 * Full Constructor filled with parameters
	 * @param id
	 * @param runtime
	 * @param title
	 * @param genre
	 * @param description
	 * @param releaseDate
	 */
	public Movie(int id, int runtime, String title, String genre, String description, LocalDate releaseDate) {
		this.id = new SimpleIntegerProperty(id);
		this.title = new SimpleStringProperty(title);
		this.runtime = new SimpleLongProperty(runtime);
		this.genre = new SimpleStringProperty(genre);
		this.description = new SimpleStringProperty(description);
		this.releaseDate = new SimpleObjectProperty<LocalDate>(releaseDate);
	}
	
	/**
	 * Returns the javaFX id property
	 * @return IntegerProperty
	 */
	public IntegerProperty idProperty() {
		return this.id;
	}

	/**
	 * Getter for the id
	 * @return integer
	 */
	public int getId() {
		return this.idProperty().get();
	}

	/**
	 * Setter for the id
	 * @param id
	 */
	public void setId(int id) {
		this.idProperty().set(id);
	}

	/**
	 * Returns the javaFX runtime property
	 * @return LongProperty
	 */
	public LongProperty runtimeProperty() {
		return this.runtime;
	}
	
	/**
	 * Getter for the runtime
	 * @return long
	 */
	public long getRuntime() {
		return this.runtimeProperty().get();
	}
	
	/**
	 * Setter for the runtime
	 * @param runtime
	 */
	public void setRuntime(final long runtime) {
		this.runtimeProperty().set(runtime);
	}
	
	/**
	 * Returns the javaFX title property
	 * @return StringProperty
	 */
	public StringProperty titleProperty() {
		return this.title;
	}
	
	/**
	 * Getter for the title
	 * @return String
	 */
	public String getTitle() {
		return this.titleProperty().get();
	}
	
	/**
	 * Setter for the title
	 * @param title
	 */
	public void setTitle(final String title) {
		this.titleProperty().set(title);
	}
	
	/**
	 * Returns the javaFX genre property
	 * @return StringProperty
	 */
	public StringProperty genreProperty() {
		return this.genre;
	}
	
	/**
	 * Getter for the genre
	 * @return String
	 */
	public String getGenre() {
		return this.genreProperty().get();
	}
	
	/**
	 * Setter for the genre
	 * @param genre
	 */
	public void setGenre(final String genre) {
		this.genreProperty().set(genre);
	}
	
	/**
	 * Returns the javaFX description property
	 * @return StringProperty
	 */
	public StringProperty descriptionProperty() {
		return this.description;
	}
	
	/**
	 * Getter for the description
	 * @return String
	 */
	public String getDescription() {
		return this.descriptionProperty().get();
	}
	
	/**
	 * Setter for the description
	 * @param description
	 */
	public void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}
	
	/**
	 * Returns the javaFX release date property
	 * @return ObjectProperty<LocalDate>
	 */
	public ObjectProperty<LocalDate> releaseDateProperty() {
		return this.releaseDate;
	}

	/**
	 * Getter for the release date
	 * @return LocalDate
	 */
	public LocalDate getReleaseDate() {
		return this.releaseDate.get();
	}
	
	/**
	 * Setter for the release date
	 * @param date
	 */
	public void setReleaseDate(final LocalDate date) {
		this.releaseDate.set(date);
	}
	
	/**
	 * Override equals to check if list contains object with same id
	 */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.id.intValue() == ((Movie) obj).id.intValue();
    }

    /**
	 * Override equals to check if list contains object with same id
	 */
    @Override
    public int hashCode() {
        return 7 + 5*id.intValue();
    }
    
	@Override
	public int compareTo(Movie comparableMovie) {
		Timestamp thisTimestamp = Timestamp.valueOf(this.getReleaseDate().atStartOfDay());
		Timestamp compareTimestamp = Timestamp.valueOf(comparableMovie.getReleaseDate().atStartOfDay());
        return thisTimestamp.compareTo(compareTimestamp);
	}
   
    
	
}