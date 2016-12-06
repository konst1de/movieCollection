package de.schule.media_collection.view;

import java.awt.TextField;

import de.schule.media_collection.logic.User;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class UserSelectionController {
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	private Stage dialogStage;
	private User user;
	private boolean signInClicked;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isSignInClicked() {
		return signInClicked;
	}
	
	public User getUser() {
		return this.user;
	}
}
