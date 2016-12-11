package de.schule.media_collection.view;

import de.schule.media_collection.logic.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 * Class which displays the menu 
 * Possible improvements:
 * 	- being able to quit at a certain operation and returning to the menu.
 * 	- more precise output 
 * @author florianwitt
 *
 */
public class Tui {

	private Controller controller;
	private InputScanner inputScanner;
	/**
	 * Constructor for the TUI. Using InputScanner for parsing input.
	 * @param useSQL boolean to determine which method of storage we initialize the controller with
	 * @throws SQLException
	 */
	public Tui(Boolean useSQL) throws SQLException {
		controller = new Controller(useSQL);
		inputScanner = new InputScanner();
	}
	/**
	 * Method for the menu that after every action the menu is displayed.
	 */
	public void menu() {
		String command;
		boolean running = true;

		while (running) {
			if (controller.getCurrentUser() == null) {
				init();
			}else{
				displayMenu();
			}
			command = getCommand();
			execute(command);
		}
	}
	/**
	 * Method to choose a user in the start
	 */
	private void init() {
		System.out.println("Please choose a user.");
		this.listUser();
		this.changeUserCommand();
		if (controller.getCurrentUser() != null) {
			displayMenu();
		}
	}
	/**
	 * Method to display the menu
	 */
	private void displayMenu() {
		System.out.println();
		System.out.println("Welcome " + controller.getCurrentUser().getFirstName());
		System.out.println();
		System.out.println("            Movie Collection             ");
		System.out.println("=========================================");
		System.out.println("|List all movies.....................[1]|");
		System.out.println("|Add movie...........................[2]|");
		System.out.println("|Edit movie..........................[3]|");
		System.out.println("|List collection.....................[4]|");
		System.out.println("|Add movie to your collection........[5]|");
		System.out.println("|Remove movie from your collection...[6]|");
		System.out.println("|Show all user.......................[7]|");
		System.out.println("|Change user.........................[8]|");
		// System.out.println("|Zeige alle Filme für Nutzer.........[8]|");
		System.out.println("|Quit................................[9]|");
		System.out.println("=========================================");
	}
	/**
	 * Method to handle the input in the menu
	 * @param unparsedCommand Input which is yet unparsed
	 */
	private void execute(String unparsedCommand) {
		int command = 0;

		try {
			command = Integer.parseInt(unparsedCommand);
			if (command == 1) {
				this.listAllMovies();
			} else if (command == 2) {
				this.addMovie();
			} else if (command == 3) {
				this.editMovie();
			} else if (command == 4) {
				this.listCollection();
			} else if (command == 5) {
				this.addExistingMovieToCollectionCommand();
			} else if (command == 6) {
				this.deleteFromCollectionCommand();
			} else if (command == 7) {
				this.listUser();
			} else if (command == 8) {
				this.changeUserCommand();
			} else if (command == 9) {
				this.quitCommand();
			} else {
				unknownCommand(unparsedCommand);
			}
		} catch (NumberFormatException e) {
			// unerlaubte Eingabe
			unknownCommand(unparsedCommand);
		}
	}
	/**
	 * Method to initialize the changeUser method. Expecting Integer input
	 */
	private void changeUserCommand() {
		System.out.println("           Change user                   ");
		System.out.println("=========================================");
		System.out.println("|User ID: ");
		int id = this.inputScanner.expectInteger();
		this.changeUser(id);
	}
	/**
	 * Method to initialize the deleteFromCollection method. Expecting Integer input
	 */
	private void deleteFromCollectionCommand() {
		System.out.println("     Remove movie from your collection   ");
		System.out.println("=========================================");
		System.out.println("|Movie ID: ");
		int id = this.inputScanner.expectInteger();
		this.deleteFromCollection(id);

	}
	/**
	 * Method to initialize the addExistingMovieToCollection method. Expecting Integer input
	 */
	private void addExistingMovieToCollectionCommand() {
		System.out.println("       Add movie to your collection      ");
		System.out.println("=========================================");
		System.out.println("|Movie ID: ");
		int id = this.inputScanner.expectInteger();
		this.addExistingMovieToCollection(id);
	}
	/**
	 * Method to add an existing user to the collection of the user. Calling the method in the controller with the passed id.
	 * @param id Integer id of the movie that wants to be added
	 */
	private void addExistingMovieToCollection(int movieId) {
		controller.addExistingMovieToCollection(movieId);
	}
	/**
	 * Method to change the currentUser on the controller. Calling the method in the controller with the passed id.
	 * @param id Integer id of the user that wants to be changed to
	 */
	private void changeUser(int userId) {
		controller.setCurrentUser(controller.getUserById(userId));
		if(controller.getCurrentUser() == null){
			System.out.println("There is no user with the ID: " + userId + ". Please use an existing ID from the following user.");
			this.listUser();
			this.changeUserCommand();
		}
	}
	private void deleteMovie(int movieId){
		if(controller.getMovieById(movieId) == null){
			System.out.println("There is no movie with the ID: " + movieId + ". Please use an existing ID from the following movies.");
			this.listAllMovies();
			this.deleteMovieCommand();
		}
	}
	private void deleteMovieCommand() {
		System.out.println("           Delete movie                  ");
		System.out.println("=========================================");
		System.out.println("|Movie ID: ");
		int id = this.inputScanner.expectInteger();
		this.deleteMovie(id);		
	}
	/**
	 * Method to list all user. Calling function in controller and iterating through result.
	 */
	private void listUser() {
		System.out.println("            List all user                ");
		System.out.println("=========================================");
		List<User> allUser = controller.getAllUser();
		this.listUserHeader();
		for (int i = 0; i < allUser.size(); i++) {
			User iterateUser = allUser.get(i);
			int id = iterateUser.getId();
			String username = iterateUser.getUserName();
			String firstname = iterateUser.getFirstName();
			String lastname = iterateUser.getLastName();
			System.out.println(id + " --- " + username + " --- " + firstname + " --- " + lastname);
		}
	}
	/**
	 * Method to delete a movie with a certain id from the collection. 
	 * @param movieId Integer id of the movie that should be deleted
	 */
	private void deleteFromCollection(int movieId) {
		if(controller.getMovieById(movieId) != null){
			controller.removeMovieFromCollection(movieId);
		}
		else{
			System.out.println("There is no movie with the given ID.");
		}
	}
	/**
	 * Method to list all movies that are owned by the currentUser (which is set in the controller). Iterating throug the result and printing it to the console.
	 */
	private void listCollection() {
		System.out.println("          List your collection           ");
		System.out.println("=========================================");
		List<Movie> allOwnedMovies = controller.getAllOwnedMovies();
		this.listMovieHeader();
		for (int i = 0; i < allOwnedMovies.size(); i++) {
			Movie currentMovie = allOwnedMovies.get(i);
			int id = currentMovie.getId();
			long runtime = currentMovie.getRuntime();
			String title = currentMovie.getTitle();
			String genre = currentMovie.getGenre();
			String description = currentMovie.getDescription();
			LocalDate releaseDate = currentMovie.getReleaseDate();
			System.out.println(id + " --- " + title + " --- " + genre + " --- " + releaseDate.toString() + " --- " + runtime + " --- " + description);
		}
	}
	/**
	 * Method to display the "table-header" for the movies
	 */
	private void listMovieHeader() {
		System.out.println("ID --- TITLE --- GENRE --- RELEASE DATE --- RUNTIME --- DESCRIPTION");		
	}
	/**
	 * Method to display the "table-header" for the user
	 */
	private void listUserHeader() {
		System.out.println("ID --- USERNAME --- FIRSTNAME --- LASTNAME");		
	}
	/**
	 * Method to edit a movie. Waiting for an ID input and checking if the movie exists. If the movie exists the operation starts. 
	 * If the movie with the given id does not exist nothing happens.
	 */
	private void editMovie() {
		System.out.println("              Edit movie                 ");
		System.out.println("=========================================");
		System.out.println("|Enter movie ID: ");
		int movieId = this.inputScanner.expectInteger();
		Movie movieToEdit = controller.getMovieById(movieId);
		if(movieToEdit != null){
			System.out.println("|Old Title: " + movieToEdit.getTitle());
			System.out.println("|New Title: ");
			movieToEdit.setTitle(this.inputScanner.expectString()); 
			System.out.println("|Old Runtime: " + movieToEdit.getRuntime());
			System.out.println("|New Runtime: ");
			movieToEdit.setRuntime(this.inputScanner.expectInteger());
			System.out.println("|Old Genre: " + movieToEdit.getGenre());
			System.out.println("|New Genre: ");
			movieToEdit.setGenre(this.inputScanner.expectString());
			System.out.println("|Old Description: " + movieToEdit.getDescription());
			System.out.println("|New Description: ");
			movieToEdit.setDescription(this.inputScanner.expectString());
			System.out.println("|Old Releasedate: " + movieToEdit.getReleaseDate().toString());
			System.out.println("|New Releasedate: ");
			movieToEdit.setReleaseDate(scanForDate());
			this.controller.editMovie(movieToEdit);
		}
		
	}
	/**
	 * Method to list all existing movies.
	 */
	private void listAllMovies() {
		System.out.println("           List all movies               ");
		System.out.println("=========================================");
		List<Movie> allMovies = controller.getAllMovies();
		this.listMovieHeader();
		for (int i = 0; i < allMovies.size(); i++) {
			Movie currentMovie = allMovies.get(i);
			int id = currentMovie.getId();
			long runtime = currentMovie.getRuntime();
			String title = currentMovie.getTitle();
			String genre = currentMovie.getGenre();
			String description = currentMovie.getDescription();
			LocalDate releaseDate = currentMovie.getReleaseDate();
			System.out.println(id + " --- " + title + " --- " + genre + " --- " + releaseDate.toString() + " --- " + runtime + " --- " + description);
		}

	}
	/**
	 * Method to ask for a command when currently in the menu.
	 * @return
	 */
	private String getCommand() {
		System.out.println("Please choose a menu point.");
		String command = inputScanner.expectString();
		return command;
	}
	/**
	 * Method to add a movie. Prompting through the different variables for the new movie.
	 */
	private void addMovie() {
		System.out.println("            Add movie                    ");
		System.out.println("=========================================");
		System.out.println("|Title: ");
		String title = this.inputScanner.expectString();
		System.out.println("|Runtime in Minutes: ");
		Integer runtime = this.inputScanner.expectInteger();
		System.out.println("|Genre: ");
		String genre = this.inputScanner.expectString();
		System.out.println("|Description: ");
		String description = this.inputScanner.expectString();
		LocalDate releaseDate = scanForDate();
		controller.editMovie(new Movie(runtime, title, genre, description, releaseDate));

	}
	/**
	 * Method to prompt for single fields of the date.
	 * @return LocalDate returns a LocalDate Object. If there is an exception parsing the date the operation is restarted with recursion
	 */
	private LocalDate scanForDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		LocalDate returnDate = null;
		int day = 0;
		int month = 0;
		int year = 0;
		try {
			System.out.println("|Please enter the day of the release date");
			day = this.inputScanner.expectDay();
			System.out.println("|Please enter the month of the release date");
			month = this.inputScanner.expectMonth();
			System.out.println("|Please enter the year of the release date");
			year = this.inputScanner.expectYear();
			returnDate = LocalDate.of(year, month, day);
		} catch (NumberFormatException e) {
			System.out.println("|Incorrect input. Please Try again");
			scanForDate();
		}
		return returnDate;
	}


	/**
	 * Method to quit
	 */
	private void quitCommand() {
		this.inputScanner.closeStream();
		System.out.println("Thanks for using the movie collection.");
		System.exit(0);
	}
	/** 
	 * Method to handle unknown commands when you are in the menu. 
	 * The unknown command is printed and then you return to the menu.
	 * @param command String the unknown command
	 */
	private void unknownCommand(String command) {
		System.out.println(command+ " is not allowed as input.");
	}
}