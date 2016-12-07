package de.schule.media_collection.view;

import java.util.List;

import de.schule.media_collection.logic.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserLoginController {
	
	@FXML
	private ComboBox<User> userComboBox;
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	private Stage dialogStage;
	private User selectedUser;
	private boolean signInClicked = false;
	private List<User> userList;
	private ObservableList<User> masterData;
	
	public UserLoginController(List<User> userList) {
		this.userList = userList;
		this.masterData = FXCollections.observableList(this.userList);
	}
	
	@FXML
	private void initialize() {
		userComboBox.setItems(masterData);
		userComboBox.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			
			@Override
			public ListCell<User> call(ListView<User> param) {
				return new ListCell<User>() {
					protected void updateItem(User user , boolean empty){
						super.updateItem(user, empty);
						if (user == null || empty) {
							setGraphic(null);
						} else {
							setText(user.getUserName());
						}
					}
				};
			}
		});
		userComboBox.setButtonCell((ListCell<User>) userComboBox.getCellFactory().call(null));
		
		userComboBox.valueProperty().addListener(new ChangeListener<User>() {
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				firstNameField.setText(newValue.getFirstName());
				lastNameField.setText(newValue.getLastName());
			}
		});
	}
	
	@FXML
	private void handleSignIn() {
		this.signInClicked = true;
		this.selectedUser = userComboBox.getValue();
		this.dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isSignInClicked() {
		return signInClicked;
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}
}
