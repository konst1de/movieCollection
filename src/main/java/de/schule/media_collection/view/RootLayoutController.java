package de.schule.media_collection.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController {
	
	private View view;
	
	public void setView(View view) {
		this.view = view;
	}
	
	@FXML
	private void handleReload() {
		view.reloadVideos();
	}
	
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
	
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("MovieCollection");
		alert.setHeaderText("About");
		alert.setContentText("Author: Rene Sachse, Florian Witt, Konstantin Vogel\nhttps://github.com/konst1de/movieCollection");
		
		alert.showAndWait();
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
