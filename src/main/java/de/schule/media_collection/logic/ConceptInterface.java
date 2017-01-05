package de.schule.media_collection.logic;

import java.util.List;

public interface ConceptInterface {
	public abstract void setCurrentUser(User user);
	public abstract User getCurrentUser();
	public abstract void addExistingMovieToCollection(int movieId);
	public abstract List<Movie> getAllMovies();
	public abstract List<User> getAllUser();
	public abstract void editMovie(Movie movie);
	public abstract List<Movie> getAllOwnedMovies();
	public abstract List<User> getOwnerForMovie(Movie movie);
	public abstract void deleteMovie(Movie movie);
	public abstract Movie getMovieById(int id);
	public abstract User getUserById(int id);
	public abstract Boolean isMovieOwnedByUser(Movie movie);
}