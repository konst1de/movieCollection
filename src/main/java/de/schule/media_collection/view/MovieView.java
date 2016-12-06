package de.schule.media_collection.view;

import java.io.IOException;

import de.schule.media_collection.logic.Movie;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieView extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Movie> movieData = FXCollections.observableArrayList();

	public MovieView() {
		movieData.add(new Movie(1, 3, "Herr der Ringe 1", "fantasy", "Bla"));
		movieData.add(new Movie(2, 4, "Herr der Ringe 2", "fantasy", "Bla"));
		movieData.add(new Movie(3, 3, "Herr der Ringe 3", "fantasy", "Bla"));

	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<Movie> getMovieData() {
		return movieData;
	}
	
	private void initRootLayout() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MovieView.class.getResource("RootLayout.fxml"));
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
			loader.setLocation(MovieView.class.getResource("MovieOverview.fxml"));
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
			loader.setLocation(MovieView.class.getResource("MovieEditDialog.fxml"));
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
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MovieOverview");

		initRootLayout();

		showMovieOverview();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
