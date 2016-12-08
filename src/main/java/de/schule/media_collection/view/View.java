package de.schule.media_collection.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
	private User currentUser;
	private Controller logicController;

	public View() {
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public Controller getLogicController() {
		return logicController;
	}
	
	private void initRootLayout() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			RootLayoutController controller = loader.getController();
			controller.setView(this);
			
			primaryStage.show();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void showMovieOverview() {
		try {
			movieList = logicController.getAllMovies();	
			userMovieList = logicController.getAllOwnedMovies();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("MovieOverview.fxml"));
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() {				
				@Override
				public Object call(Class<?> controllerClass) {
					if (controllerClass == MovieViewController.class) {
						MovieViewController controller = new MovieViewController(movieList, userMovieList, View.this);
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
			
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> controllerClass) {
					if (controllerClass == MovieEditController.class) {
						MovieEditController controller = new MovieEditController(movie);
						return controller;
					} else {
						try {
							return controllerClass.newInstance();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return controllerClass;
				}
			});
			
			AnchorPane page = (AnchorPane) loader.load();
			MovieEditController controller = loader.getController();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Movie");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			controller.setDialogStage(dialogStage);
			
			controller.setMovie();
			
			dialogStage.showAndWait();
			
			if (controller.getOkClicked()) {
				return true;
			} else {
				return false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void showUserLoginDialog() {
		try {
			userList = logicController.getAllUser();
			
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
				currentUser = controller.getSelectedUser();
				logicController.setCurrentUser(currentUser);
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
	
	public void saveMovie(Movie movie, boolean addCollection) {
		logicController.editMovie(movie, addCollection);
	}
	
	private void cleanup() {
		
	}
	
	public void restart() {
		cleanup();
		startApplication();
	}

	@Override
	public void start(Stage primaryStage) {
		
		boolean useSql = getParameters().getUnnamed().get(0).equals("--use-sql");
        
		try {
			 logicController = new Controller(useSql);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Connection Error!");
			alert.setHeaderText("Error! No Connection!");
			alert.setContentText("Missing Connection! Please check server connectivity!");
		}
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MovieOverview");
		startApplication();
	}
	
	private void startApplication() {
		
		showUserLoginDialog();
		
		if (currentUser != null) {
			initRootLayout();
			showMovieOverview();
		} else {
			System.exit(0);
		}
	}
}
