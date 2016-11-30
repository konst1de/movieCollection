package de.schule.media_collection.view;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class ViewController {

	
	@FXML
	BorderPane borderPane;
	
	@FXML
	Pane mainContent;
	@FXML
	StackPane StackPaneMain;
	
	@FXML
	private void initializeContent(){
		StackPaneMain.getChildren().clear();
		try {
			StackPaneMain.getChildren().add((Node) FXMLLoader.load(getClass().getResource("MovieView.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StackPaneMain.setLayoutX(0);
		StackPaneMain.setLayoutY(0);
	}
}
