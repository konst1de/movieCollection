package de.schule.media_collection.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for the root layout
 * @author Rene
 *
 */
public class RootLayoutController {
	
	private View view;
	
	/**
	 * Setter for the javaFX view
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Method to handle the reload menu event
	 * Calls the view to reload the database listings
	 */
	@FXML
	private void handleReload() {
		view.reloadVideos();
	}
	
	/**
	 * Method to handle the logout menu event
	 * Restarts the application
	 */
	@FXML
	private void handleLogout() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logout!");
		alert.setContentText("Do you want to logout?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			view.restart();
		} else {
			return;
		}
	}
	
	/**
	 * Method to handle the about menu event
	 * Returns information about the application
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("MovieCollection");
		alert.setHeaderText("About");
		alert.setContentText("Author: Rene Sachse, Florian Witt, Konstantin Vogel\nhttps://github.com/konst1de/movieCollection");
		
		alert.showAndWait();
	}
	
	/**
	 * Method to handle the exit menu event
	 * Closes the application
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
