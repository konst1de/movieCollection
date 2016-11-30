package de.schule.media_collection;

import java.io.IOException;
import java.sql.SQLException;

import de.schule.media_collection.logic.Controller;
import de.schule.media_collection.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main{
	
	public static void main(String[] args) throws SQLException {
//		Application.launch(View.class, null);
		Controller controller = new Controller(true);
	}
}
