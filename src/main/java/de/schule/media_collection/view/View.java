package de.schule.media_collection.view;

import java.io.IOException;

import de.schule.media_collection.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View extends Application{
	
	private Scene scene;
	private Stage stage;
	private BorderPane rootLayout;
	@FXML
	StackPane StackPaneMain;
	public static void main(String[] args){
		launch(null);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("RootLayout.fxml"));
		System.out.println(root);
		Scene scene = new Scene(root, 300, 275);
		stage.setTitle("Home");
		stage.setScene(scene);
		stage.show();
	}


}
