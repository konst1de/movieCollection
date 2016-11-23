package de.schule.media_collection;

import java.io.IOException;

import de.schule.media_collection.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main{
	
	public static void main(String[] args) {
		
		View view = new View();
		Stage primaryStage = null;

		try {
			view.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
}
