package de.schule.media_collection;

import java.io.IOException;

import de.schule.media_collection.Control.Controller;
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
//		Application.launch(View.class, null);
		Controller controller = new Controller();
		controller.addMovieToCollection("Napoleon Dynamite", 120 , "Komödie" ,"Super Film!");
	}
}
