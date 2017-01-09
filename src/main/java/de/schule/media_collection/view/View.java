package de.schule.media_collection.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.schule.media_collection.logic.ConceptInterface;
import de.schule.media_collection.logic.ConceptSorted;
import de.schule.media_collection.logic.ConceptUnsorted;
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

/**
 * Main JavaFX View Class
 * Extends the javafx.application.Application class
 * Initilize all the views + controller, and the logic controller
 * Possible Improvements:
 * - userLogin provided by Java Logger class
 * @author Rene
 *
 */
public class View extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private List<Movie> movieList;
	private List<Movie> userMovieList;
	private List<User> userList;
	private User currentUser;
	private ConceptInterface logicController;
	private MovieViewController viewController;

	/**
	 * Default Constructor
	 */
	public View() {
	}
	
	/**
	 * Getter for the primary stage
	 * @return Stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Getter for the logic controller
	 * @return Controller
	 */
	public ConceptInterface getLogicController() {
		return logicController;
	}
	
	/**
	 * Initilized the main root layout and the related controller by means of the javaFX loader
	 */
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

	/**
	 * Initilized the main movie view and the related controller by means of the javaFX loader
	 */
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
						viewController = new MovieViewController(movieList, userMovieList, View.this);
						return viewController;
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
	
	/**
	 * Initilize the main edit view and the related controller by means of the javaFX loader
	 * Returns the user button input
	 * @param movie
	 * @return boolean
	 */
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
	
	/**
	 * Initilize the user login view and the related controller by means of the javaFX loader
	 */
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
			dialogStage.setTitle("User Selection");
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
	
	/**
	 * Method to restart the hole application
	 */
	public void restart() {
		primaryStage.close();
		Stage stage = new Stage();
		this.primaryStage = stage;
		this.primaryStage.setTitle("MovieOverview");
		startApplication();
	}

	/**
	 * Method to reload the database listings
	 */
	public void reloadVideos() {
		movieList = logicController.getAllMovies();
		userMovieList = logicController.getAllOwnedMovies();
		viewController.refreshTable(movieList, userMovieList);
	}

	/**
	 * javaFX start method implemented by javafx application class 
	 */
	@Override
	public void start(Stage primaryStage) {
		
		boolean useSql = getParameters().getUnnamed().get(0).equals("--use-sql");
		boolean sort = getParameters().getUnnamed().get(2).equals("--sort");

		try {
			 logicController = sort ? new ConceptSorted(useSql) : new ConceptUnsorted(useSql);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Connection Error!");
			alert.setHeaderText("Error! No Connection!");
			alert.setContentText("Missing Connection! Please check server connectivity!");
		}
		
		this.primaryStage = primaryStage;
		startApplication();
	}
	
	/**
	 * default method to start the ui
	 */
	private void startApplication() {
		
		showUserLoginDialog();
		
		if (currentUser != null) {
			this.primaryStage.setTitle("MovieOverview - " + currentUser.getFirstName() + " " + currentUser.getLastName());
			initRootLayout();
			showMovieOverview();
		} else {
			System.exit(0);
		}
	}
}
