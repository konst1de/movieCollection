package de.schule.media_collection.view;

import de.schule.media_collection.logic.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MovieEditController {
	
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField genreField;
	
	@FXML
	private TextField runtimeField;
	
	@FXML
	private TextField descriptionField;
	
	private Stage dialogStage;
	private Movie movie;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		titleField.setText(movie.getTitle());
		genreField.setText(movie.getGenre());
		runtimeField.setText(Long.toString(movie.getRuntime()));
		descriptionField.setText(movie.getDescription());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			movie.setTitle(titleField.getText());
			movie.setGenre(genreField.getText());
			movie.setRuntime(Integer.parseInt(runtimeField.getText()));
			movie.setDescription(descriptionField.getText());
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (titleField.getText() == null || titleField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		
		if (genreField.getText() == null || genreField.getText().length() == 0) {
			errorMessage += "No valid genre!\n";
		}
		
		if (runtimeField.getText() == null || runtimeField.getText().length() == 0) {
			errorMessage += "No valid runtime!\n";
		}
		
		if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
			errorMessage += "No valid description!\n";
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invakud fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
	}
}
