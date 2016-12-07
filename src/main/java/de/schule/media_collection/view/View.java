package de.schule.media_collection.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.schule.media_collection.logic.Controller;
import de.schule.media_collection.logic.Movie;
import de.schule.media_collection.logic.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class View extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private List<Movie> movieList;
	private List<Movie> userMovieList;
	private List<User> userList;
	private User selectedUser;
	private Controller logicController;

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
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() {				
				@Override
				public Object call(Class<?> controllerClass) {
					if (controllerClass == MovieViewController.class) {
						MovieViewController controller = new MovieViewController(movieList, View.this);
						return controller;
					} else {
						try {
							return controllerClass.newInstance();
						} catch (Exception  e) {
							e.printStackTrace();
						}
					}
					return controllerClass;
				}
			});
			AnchorPane movieOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(movieOverview);
			
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
	
	public void showUserLoginDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(View.class.getResource("UserLoginDialog.fxml"));
			loader.setControllerFactory(new Callback<Class<?>, Object>() {				
				@Override
				public Object call(Class<?> controllerClass) {
					if (controllerClass == UserLoginController.class) {
						UserLoginController controller = new UserLoginController(userList);
						return controller;
					} else {
						try {
							return controllerClass.newInstance();
						} catch (Exception  e) {
							e.printStackTrace();
						}
					}
					return controllerClass;
				}
			});

			AnchorPane page = (AnchorPane) loader.load();
			UserLoginController controller = loader.getController();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("User Login");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			controller.setDialogStage(dialogStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			dialogStage.showAndWait();
			
			if (controller.isSignInClicked() == true) {
				selectedUser = controller.getSelectedUser();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("User Selection");
				alert.setHeaderText("Error!");
				alert.setContentText("Do you realy want to close this Application?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				   return;
				} else {
					showUserLoginDialog();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			 logicController = new Controller(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MovieOverview");
		
		userList = logicController.getAllUser();
		
		showUserLoginDialog();
		
		if (selectedUser != null) {
			initRootLayout();
			movieList = logicController.getAllMovies();			
			showMovieOverview();
		} else {
			System.exit(0);
		}
	}
}
