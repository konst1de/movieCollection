package de.schule.media_collection.view;

import de.schule.media_collection.logic.Movie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Controller class for the edit movie ui
 * @author Rene
 *
 */
public class MovieEditController {
	
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField genreField;
	
	@FXML
	private TextField runtimeField;
	
	@FXML
	private TextField descriptionField;
	
	@FXML
	private DatePicker releaseDatePicker;
	
	private Stage dialogStage;
	private Movie movie;
	private boolean okClicked = false;
	
	/**
	 * Default constructor
	 * @param movie
	 */
	public MovieEditController(Movie movie) {
		this.movie = movie;
	}
	
	/**
	 * Default controller initilize method provided by javaFX
	 * Initilize the ui fields and inserts the data
	 */
	@FXML
	private void initialize() {
		runtimeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					long value = Long.parseLong(newValue);
				} else {
					runtimeField.setText(oldValue);
				}
			}
		});
	}
	
	/**
	 * Setter for the javaFX dialog stage
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Setter for the movie object
	 */
	public void setMovie() {
		titleField.setText(movie.getTitle());
		genreField.setText(movie.getGenre());
		runtimeField.setText(Long.toString(movie.getRuntime()));
		descriptionField.setText(movie.getDescription());
		releaseDatePicker.setValue(movie.getReleaseDate());			
	}
	
	/**
	 * Getter for the okClicked variable
	 * @return boolean
	 */
	public boolean getOkClicked() {
		return okClicked;
	}
	
	/**
	 * Method to handle the ok button event
	 * Sets the movie propertys with the values of the ui fields
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			movie.setTitle(titleField.getText());
			movie.setGenre(genreField.getText());
			movie.setRuntime(Long.parseLong(runtimeField.getText()));
			movie.setDescription(descriptionField.getText());
			movie.setReleaseDate(releaseDatePicker.getValue());
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Method to handle the cancel button event
	 * Closes the ui
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * Method to check if ui fields contain information
	 * @return boolean
	 */
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
		
		if (releaseDatePicker.getValue() == null ) {
			errorMessage += "No valid release date!\n";
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
	}
}
