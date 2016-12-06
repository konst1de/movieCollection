package de.schule.media_collection.view;

import java.io.IOException;

import de.schule.media_collection.logic.Movie;
import de.schule.media_collection.logic.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class View extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private User user;

	public View() {
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	private void initRootLayout() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void showMovieOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("MovieOverview.fxml"));
			AnchorPane movieOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(movieOverview);
			
			MovieViewController controller = loader.getController();
			controller.setView(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean showMovieEditDialog(Movie movie) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("MovieEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Movie");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			MovieEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMovie(movie);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User showUserLoginDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("UserLoginDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("User Login");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			UserSelectionController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			dialogStage.showAndWait();
			
			return controller.getUser();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MovieOverview");

		initRootLayout();
		
		//user = showUserLoginDialog();
		
		//if (user != null) {
			showMovieOverview();
		//}
	}
}
