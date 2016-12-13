package de.schule.media_collection.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import de.schule.media_collection.logic.Movie;
import de.schule.media_collection.logic.User;

public abstract class DataConnector {
    protected  DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public abstract List<Movie> getMovies();

	public abstract List<User> getUser();

	public abstract void addMovie(String title, int runtime, String genre, String description, LocalDate date);

	public abstract void addMovieToCollection(int id, int id2);

	public abstract void editMovie(int movieId, String title, long runtime, String genre, String description,
			LocalDate relaseDate);

	public abstract Movie getMovieById(int id);

	public abstract void deleteMovie(int movieId);

	public abstract List<User> getUserOwnMovie(int id);

	public abstract List<Movie> getMoviesOwnedByUser(int id);

	public abstract void removeMovieFromUser(int id, int id2);

	public abstract boolean isMovieOwnedByUser(int id, int id2);

	public abstract User getUserById(int id);
}
