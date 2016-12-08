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
	private User currentUser;

	public Tui() throws SQLException {
		controller = new Controller(false);
		inputScanner = new InputScanner();
	}

	public void menu() {
		String command;
		boolean running = true;

		while (running) {
			if (currentUser == null) {
				init();
			}else{
				displayMenu();
			}
			command = getCommand();
			execute(command);
		}
	}

	private void init() {
		System.out.println("Bitte Nutzer wählen");
		this.listUser();
		this.changeUserCommand();
		if (currentUser != null) {
			displayMenu();
		}
	}

	private void displayMenu() {
		System.out.println();
		System.out.println("Willkommen " + currentUser.getFirstName());
		System.out.println();
		System.out.println("            Filmesammlung                ");
		System.out.println("=========================================");
		System.out.println("|Alle Filme auflisten................[1]|");
		System.out.println("|Film hinzufügen.....................[2]|");
		System.out.println("|Film editieren......................[3]|");
		System.out.println("|Sammlung auflisten..................[4]|");
		System.out.println("|Film zur Sammlung hinzufügen........[5]|");
		System.out.println("|Film aus Sammlung löschen...........[6]|");
		System.out.println("|Nutzer anzeigen.....................[7]|");
		System.out.println("|Nutzer wechseln.....................[8]|");
		// System.out.println("|Zeige alle Filme für Nutzer.........[8]|");
		System.out.println("|Beenden.............................[9]|");
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
		System.out.println("           Nutzer wechseln               ");
		System.out.println("=========================================");
		System.out.println("|Nutzer ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();

		} catch (NumberFormatException e) {
			System.out.println("|Fehlerhafte Eingabe, bitte nur Zahlen eingeben");
			changeUserCommand();
		}
		this.changeUser(id);

	}

	private void deleteFromCollectionCommand() {
		System.out.println("       Film von Sammlung löschen         ");
		System.out.println("=========================================");
		System.out.println("|Film ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();
		} catch (NumberFormatException e) {
			System.out.println("|Fehlerhafte Eingabe, bitte nur Zahlen eingeben");
			this.deleteFromCollectionCommand();
		}
		this.deleteFromCollection(id);

	}

	private void addExistingMovieToCollectionCommand() {
		System.out.println("       Film zur Sammlung hinzufügen      ");
		System.out.println("=========================================");
		System.out.println("|Film ID: ");
		int id = 0;
		try {
			id = this.inputScanner.expectInteger();

		} catch (NumberFormatException e) {
			System.out.println("|Fehlerhafte Eingabe, bitte nur Zahlen eingeben");
			this.addExistingMovieToCollectionCommand();
		}
		this.addExistingMovieToCollection(id);

	}

	private void addExistingMovieToCollection(int id) {
		controller.addExistingMovieToCollection(id, currentUser);
	}

	private void changeUser(int userId) {
		currentUser = controller.getUserById(userId);
		if(currentUser == null){
			System.out.println("Nutzer mit dieser ID ist nicht im System. Bitte wählen Sie ein existierende ID.");
			this.listUser();
			this.changeUserCommand();
		}
	}

	private void listUser() {
		System.out.println("            Nutzer auflisten             ");
		System.out.println("=========================================");
		List<User> allUser = controller.getAllUser();
		System.out.println("ID --- NUTZERNAME --- VORNAME --- NACHNAME");
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
		controller.removeMovieFromCollection(movieId, currentUser);
	}

	private void listCollection() {
		System.out.println("           Sammlung auflisten           ");
		System.out.println("=========================================");
		List<Movie> allOwnedMovies = controller.getAllOwnedMovies(currentUser);
		System.out.println("ID --- TITEL --- GENRE --- LAUFZEIT --- BESCHREIBUNG");
		for (int i = 0; i < allOwnedMovies.size(); i++) {
			Movie currentMovie = allOwnedMovies.get(i);
			int id = currentMovie.getId();
			long runtime = currentMovie.getRuntime();
			String title = currentMovie.getTitle();
			String genre = currentMovie.getGenre();
			String description = currentMovie.getDescription();
			System.out.println(id + " --- " + title + " --- " + genre + " --- " + runtime + " --- " + description);
		}
	}

	private void editMovie() {
		// TODO Auto-generated method stub

	}

	private void listAllMovies() {
		System.out.println("           Alle Filme auflisten          ");
		System.out.println("=========================================");
		List<Movie> allMovies = controller.getAllMovies();
		System.out.println("ID --- TITEL --- GENRE --- ERSCHEINUNGSJAHR--- LAUFZEIT --- BESCHREIBUNG");
		for (int i = 0; i < allMovies.size(); i++) {
			Movie currentMovie = allMovies.get(i);
			int id = currentMovie.getId();
			long runtime = currentMovie.getRuntime();
			String title = currentMovie.getTitle();
			String genre = currentMovie.getGenre();
			String description = currentMovie.getDescription();
			LocalDate releaseDate = currentMovie.getReleaseDate();
			System.out.println(id + " --- " + title + " --- " + genre + " --- " + releaseDate + " --- " + runtime + " --- " + description);
		}

	}

	private String getCommand() {
		System.out.println("Bitte einen Menüpunkt wählen: ");
		String command = inputScanner.expectString();
		return command;
	}

	private void addMovie() {
		System.out.println("            Film hinzufügen              ");
		System.out.println("=========================================");
		System.out.println("|Titel: ");
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
		int tmpUserId = 0;
		if(addToCollection == 1){
			tmpUserId = currentUser.getId();
		}
		System.out.println(title + " , " + runtime + " , " +  genre + " , " +  description + " , " +  tmpUserId + " , " +  releaseDate);
		controller.addMovieToCollection(title, runtime, genre, description, tmpUserId, releaseDate);

	}

	private LocalDate scanForDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		LocalDate returnDate = null;
		int day = 0;
		int month = 0;
		int year = 0;
		try {
			// unerlaubte Eingabe
			System.out.println("|Erscheinungsdatum: Tag");
			day = this.inputScanner.expectDay();
			System.out.println("|Erscheinungsdatum: Monat");
			month = this.inputScanner.expectMonth();
			System.out.println("|Erscheinungsdatum: Jahr");
			year = this.inputScanner.expectYear();
			returnDate = LocalDate.of(year, month, day);
		} catch (NumberFormatException e) {
			System.out.println("|Fehlerhafte Eingabe, bitte nur Zahlen eingeben");
			scanForDate();

		}
		return returnDate;
	}



	private void quitCommand() {
		this.inputScanner.closeStream();
		System.out.println("Danke für die Nutzung. Bis zum nächsten mal...");
		System.exit(0);
	}

	private void unknownCommand(String command) {
		System.out.println("Unerlaubte Eingabe. " + command
				+ " ist nicht für die Menüsteuerung vorgesehen. Bitte nochmal versuchen.");
	}
}