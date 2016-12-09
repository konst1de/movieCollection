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

public class Tui {

	private Controller controller;
	private InputScanner inputScanner;

	public Tui(Boolean useSQL) throws SQLException {
		controller = new Controller(useSQL);
		inputScanner = new InputScanner();
	}

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

	private void init() {
		System.out.println("Please choose a user.");
		this.listUser();
		this.changeUserCommand();
		if (controller.getCurrentUser() != null) {
			displayMenu();
		}
	}

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

	private void changeUserCommand() {
		System.out.println("           Change user                   ");
		System.out.println("=========================================");
		System.out.println("|User ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();

		} catch (NumberFormatException e) {
			System.out.println("|Incorret input. Please only input numbers.");
			changeUserCommand();
		}
		this.changeUser(id);

	}

	private void deleteFromCollectionCommand() {
		System.out.println("     Remove movie from your collection   ");
		System.out.println("=========================================");
		System.out.println("|Movie ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();
		} catch (NumberFormatException e) {
			System.out.println("|Incorret input. Please only input numbers.");
			this.deleteFromCollectionCommand();
		}
		
		this.deleteFromCollection(id);

	}

	private void addExistingMovieToCollectionCommand() {
		System.out.println("       Add movie to your collection      ");
		System.out.println("=========================================");
		System.out.println("|Movie ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();

		} catch (NumberFormatException e) {
			System.out.println("|Incorret input. Please only input numbers.");
			this.addExistingMovieToCollectionCommand();
		}
		this.addExistingMovieToCollection(id);

	}

	private void addExistingMovieToCollection(int id) {
		controller.addExistingMovieToCollection(id);
	}

	private void changeUser(int userId) {
		
		controller.setCurrentUser(controller.getUserById(userId));

		if(controller.getCurrentUser() == null){
			System.out.println("There is no user with the ID: " + userId + ". Please use an existing ID from the following user.");
			this.listUser();
			this.changeUserCommand();
		}
	}

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

	private void deleteFromCollection(int movieId) {
		if(controller.getMovieById(movieId) != null){
			controller.removeMovieFromCollection(movieId);
		}
		else{
			System.out.println("There is no movie with the given ID.");
		}
	}

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

	private void listMovieHeader() {
		System.out.println("ID --- TITLE --- GENRE --- RELEASE DATE --- RUNTIME --- DESCRIPTION");		
	}
	private void listUserHeader() {
		System.out.println("ID --- USERNAME --- FIRSTNAME --- LASTNAME");		
	}

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
			this.controller.editMovie(movieToEdit, false);
		}
		
	}

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

	private String getCommand() {
		System.out.println("Please choose a menu point.");
		String command = inputScanner.expectString();
		return command;
	}

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
		System.out.println("|Add to your Collection?: [1] for yes [2] for no");
		int addToCollection = this.inputScanner.expectInteger();
		boolean addToCollectionBoolean = false;
		if(addToCollection == 1){
			addToCollectionBoolean = true;
		}
		controller.editMovie(new Movie(runtime, title, genre, description, releaseDate), addToCollectionBoolean);

	}

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



	private void quitCommand() {
		this.inputScanner.closeStream();
		System.out.println("Thanks for using the movie collection.");
		System.exit(0);
	}

	private void unknownCommand(String command) {
		System.out.println(command+ " is not allowed as input.");
	}
}