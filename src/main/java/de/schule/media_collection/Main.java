package de.schule.media_collection;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Media_Collection");
		
		initRootLayout();
		
		showMovieOverview();
	}

	private void showMovieOverview() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/movieView.fxml"));
			AnchorPane movieOverview = (AnchorPane) loader.load();
			
			rootLayout.setCenter(movieOverview);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void initRootLayout() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
