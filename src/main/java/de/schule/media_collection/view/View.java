package de.schule.media_collection.view;

import java.io.IOException;

import de.schule.media_collection.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View extends Application{
	private Scene scene;
	private Stage primaryStage;
	private BorderPane rootLayout;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Media_Collection");
		
		initRootLayout();
		
		showMovieOverview();
		
	}
	
	private void showMovieOverview() {
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("movieView.fxml"));
			AnchorPane movieOverview = null;
			try {
				movieOverview = (AnchorPane) loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			rootLayout.setCenter(movieOverview);
		
		
	}

	private void initRootLayout() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
