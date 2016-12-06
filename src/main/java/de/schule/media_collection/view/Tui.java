package de.schule.media_collection.view;

import de.schule.media_collection.logic.*;
import java.sql.SQLException;
import java.util.Scanner;


public class Tui {

	private Controller controller;
	private Scanner myScanner;

	public Tui() throws SQLException
	{
		controller = new Controller(true);
	    myScanner = new Scanner(System.in);
	}

	public void menu()
	{
	    int command;
	    boolean running = true;

	    while(running)
	    {
	        displayMenu();
	        command = getCommand();
	        execute(command);
	    }
	}

	private void displayMenu()
	{
	    System.out.println("            Filmesammlung                ");
	    System.out.println("=========================================");
	    System.out.println("|Film hinzufügen.....................[1]|");
	    System.out.println("|Film löschen........................[2]|");
	    System.out.println("|Sammlung auflisten..................[3]|");
	    System.out.println("|Freunde anzeigen....................[4]|");
	    System.out.println("|Beenden.............................[5]|");
	    System.out.println("=========================================");
	}

	private void execute(int command)
	{
	    if(command == 1)
	    {
	        addMovie();
	    }

	    else if(command == 2)
	    {
	    	removeMovie();
	    }

	    else if(command == 3)
	    {
	    	showAllMovies();
	    }

	    else if(command == 4)
	    {
	    	showAllFriends();
	    }

	    else if(command == 5)
	    {
	        quitCommand();
	    }

	    else
	    {
	        unknownCommand(command);
	    }
	}

	private int getCommand()
	{
	    System.out.println("Bitte einen Menüpunkt wählen: ");
	    int command = myScanner.nextInt();
	    return command;
	}

	private void addMovie()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("            Film hinzufügen              ");
	    System.out.println("=========================================");
	    System.out.println("|Titel: ");
	    String title = scanner.nextLine();
	    System.out.println("|Runtime: ");
	    Integer runtime = scanner.nextInt();
	    System.out.println("|Genre: ");
	    String genre = scanner.nextLine();
	    System.out.println("|Description: ");
	    String description = scanner.nextLine();
	    scanner.close();
		controller.addMovieToCollection(title, runtime, genre, description);
	}
	
	private void removeMovie()
	{
		Movie movie;
		User user;
	    Scanner scanner = new Scanner(System.in);
		System.out.println("            Film löschen                 ");
	    System.out.println("=========================================");
	    System.out.println("|Film ID: ");
	    Integer movieId = scanner.nextInt();
	    scanner.close();
	    //TODO wie übergebe ich denn hier die movieID, bzw. was brauche ich an info's um den Film zu löschen?
	    //controller.removeMovieFromCollection(movie, user);
	}

	private void quitCommand()
	{
	    System.out.println("Danke für die Nutzung. Bis zum nächsten mal...");
	    System.exit(0);
	}

	
	private void showAllFriends()
	{
		System.out.println("            Freunde anzeigen             ");
	    System.out.println("=========================================");
	    System.out.println(controller.getAllUser());
	}

	private void showAllMovies()
	{	
		System.out.println("            Sammlung auflisten           ");
	    System.out.println("=========================================");
	    System.out.println(controller.getAllMovies());
	}

	private void unknownCommand(int command)
	{
	    System.out.println("Unerlaubte Eingabe. " + command + " ist nicht für die Menüsteuerung vorgesehen. Bitte nochmal versuchen.");
	}
}