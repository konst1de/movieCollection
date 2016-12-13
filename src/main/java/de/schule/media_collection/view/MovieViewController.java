package de.schule.media_collection.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;

import de.schule.media_collection.logic.Movie;
import de.schule.media_collection.logic.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class for the movie overview 
 * @author Rene
 *
 */
public class MovieViewController {

	@FXML
	private TextField filterField;
	
	@FXML
	private TextField userFilterField;
	
	@FXML
	private TableView<Movie> movieTable;
	
	@FXML
	private TableView<Movie> userMovieTable;

	@FXML
	private TableColumn<Movie, String> titleColumn;
	
	@FXML
	private TableColumn<Movie, String> userTitleColumn;

	@FXML
	private TableColumn<Movie, Long> runtimeColumn;
	
	@FXML
	private TableColumn<Movie, Long> userRuntimeColumn;

	@FXML
	private TableColumn<Movie, String> genreColumn;
	
	@FXML
	private TableColumn<Movie, String> userGenreColumn;

	@FXML
	private TableColumn<Movie, String> descriptionColumn;
	
	@FXML
	private TableColumn<Movie, String> userDescriptionColumn;
	
	@FXML
	private TableColumn<Movie, LocalDate> releaseColumn;
	
	@FXML
	private TableColumn<Movie, LocalDate> userReleaseColumn;
	
	@FXML
	private TableColumn<Movie, Movie> buttonEditColumn;
	
	@FXML
	private TableColumn<Movie, Movie> buttonAddColumn;
	
	@FXML
	private TableColumn<Movie, Movie> buttonDeleteColumn;
	
	@FXML
	private TableColumn<Movie, Movie> userButtonRemoveColumn;

	private View view;
	
	private List<Movie> movieList;
	private List<Movie> userMovieList;
	private ObservableList<Movie> masterData;
	private ObservableList<Movie> userMasterData;

	/**
	 * Default Constructor
	 * @param movieList
	 * @param userMovieList
	 * @param view
	 */
	public MovieViewController(List<Movie> movieList, List<Movie> userMovieList, View view) {
		this.movieList = new ArrayList<Movie>(movieList);
		this.userMovieList = new ArrayList<Movie>(userMovieList);
		this.masterData = FXCollections.observableArrayList(this.movieList);
		this.userMasterData = FXCollections.observableArrayList(this.userMovieList);
		this.view = view;
	}
	
	/**
	 * Getter for the javaFX movieDataList
	 * @return ObservableList<Movie>
	 */
	public ObservableList<Movie> getMovieData() {
		return masterData;
	}
	
	/**
	 * Getter for the javaFX userMovieDataList
	 * @return ObservableList<Movie>
	 */
	public ObservableList<Movie> getUserMovieData() {
		return userMasterData;
	}

	/**
	 * Default controller initilize method provided by javaFX
	 * Initilize the ui fields and tables and inserts the data
	 */
	@FXML
	private void initialize() {
		initMovieTable();
		initUserMovieTable();
	}
	
	/**
	 * Initilize Method for the user movie table
	 * Inserts the data into the table view and
	 * creates the filter field
	 */
	private void initUserMovieTable() {
		userMovieTable.setItems(userMasterData);
		
		userTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		userRuntimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		userGenreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		userDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		userReleaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
		
		userButtonRemoveColumn.setCellFactory(col -> {
			Button removeButton = new Button("Remove Movie");
			TableCell<Movie, Movie> cell = new TableCell<Movie, Movie>() {
				public void updateItem(Movie movie, boolean empty) {
					super.updateItem(movie, empty);
					this.setAlignment(Pos.CENTER);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(removeButton);
					}
				}
			};
			removeButton.setOnAction(e -> handleRemoveUserMovie(cell.getIndex()));
			return cell;
		});
				
		FilteredList<Movie> userFilteredData = new FilteredList<>(userMovieTable.getItems(), e -> true);
		
		userFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
			userFilteredData.setPredicate(Movie -> {			
				if (newValue == null || newValue.isEmpty()) {
	             return true;
				}
			 
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
	                return true;
				}
	            return false;
	        });
		});
		
		SortedList<Movie> sortedData = new SortedList<>(userFilteredData);	
		sortedData.comparatorProperty().bind(userMovieTable.comparatorProperty());
		userMovieTable.setItems(sortedData);
	}

	/**
	 * Method to handle the remove movie from collection event
	 * calls the logic controller to remove the movie from collection
	 * @param index
	 */
	private void handleRemoveUserMovie(int index) {
		Movie selectedMovie = userMasterData.get(index);
		if (selectedMovie != null) {
			userMasterData.remove(selectedMovie);
			view.getLogicController().removeMovieFromCollection(selectedMovie.getId());
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Movie Selected");
			alert.setContentText("Please select a movie in the table.");
			
			alert.showAndWait();
		}
	}

	/**
	 * Initilize Method for the movie table
	 * Inserts the data into the table view and
	 * creates the filter Field
	 */
	private void initMovieTable() {
		movieTable.setItems(masterData);
		
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		runtimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		releaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
		buttonEditColumn.setCellFactory(col -> {
			Button editButton = new Button("Edit Movie");
			TableCell<Movie, Movie> cell = new TableCell<Movie, Movie>() {
				public void updateItem(Movie movie, boolean empty) {
					super.updateItem(movie, empty);
					this.setAlignment(Pos.CENTER);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(editButton);
					}
				}
			};
			editButton.setOnAction(e -> handleEditMovie(cell.getIndex()));
			return cell;
		});
		
		buttonAddColumn.setCellFactory(col -> {
			Button addButton = new Button("Add to Collection");
			TableCell<Movie, Movie> cell = new TableCell<Movie, Movie>() {
				public void updateItem(Movie movie, boolean empty) {
					super.updateItem(movie, empty);
					this.setAlignment(Pos.CENTER);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(addButton);
					}
				}
			};
			addButton.setOnAction(e -> handleAddToCollection(cell.getIndex()));
			return cell;
			
		});
		
		buttonDeleteColumn.setCellFactory(col -> {
			Button deleteButton = new Button("Delete Movie");
			TableCell<Movie, Movie> cell = new TableCell<Movie, Movie>() {
				public void updateItem(Movie movie, boolean empty) {
					super.updateItem(movie, empty);
					this.setAlignment(Pos.CENTER);
					if (empty || userMovieList.contains(movie)) {
						setGraphic(null);
					} else {
						setGraphic(deleteButton);
					}
				}
			};
			deleteButton.setOnAction(e -> handleDeleteMovie(cell.getIndex()));
			return cell;
		});
				
		FilteredList<Movie> filteredData = new FilteredList<>(movieTable.getItems(), e -> true);
		
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Movie -> {			
				if (newValue == null || newValue.isEmpty()) {
	             return true;
				}
			 
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
	                return true;
				}
	            return false;
	        });
		});
		
		SortedList<Movie> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(movieTable.comparatorProperty());	
		movieTable.setItems(sortedData);
	}
	
	/**
	 * Method to refresh the database listings in the table
	 * @param movieList
	 * @param userMovieList
	 */
	public void refreshTable(List<Movie> movieList, List<Movie> userMovieList) {
		this.movieList = movieList;
		this.userMovieList = userMovieList;
		masterData.removeAll(masterData);
		userMasterData.removeAll(userMasterData);
		masterData.addAll(this.movieList);
		userMasterData.addAll(this.userMovieList);
	}

	/**
	 * Method to handle the add to collection event
	 * calls the logic controller to add the movie to collection
	 * @param index
	 */
	private void handleAddToCollection(int index) {
		Movie selectedMovie = masterData.get(index);
		if (selectedMovie != null && !userMasterData.contains(selectedMovie)) {
				userMasterData.add(selectedMovie);
				view.getLogicController().addExistingMovieToCollection(selectedMovie.getId());		
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("Movie allready in collection");
			alert.setContentText("Please select another movie in the table.");
			
			alert.showAndWait();
		}
		
	}
	
	/**
	 * Method to handle the add movie event
	 * calls the view to start the edit movie ui and 
	 * then calls the datalayer to create a new movie
	 */
	@FXML
	private void handleNewMovie() {
		Movie tempMovie = new Movie();
		boolean okClicked = view.showMovieEditDialog(tempMovie);
		if (okClicked) {
			masterData.add(tempMovie);
			view.getLogicController().editMovie(tempMovie);
			view.reloadVideos();
		}
	}
	
	/**
	 * Method to handle the edit movie event
	 * calls the view to start the edit movie ui and 
	 * then calls the datalayer to edit a movie
	 */
	private void handleEditMovie(int index) {
		Movie selectedMovie = masterData.get(index);
		if (selectedMovie != null) {
			boolean okClicked = view.showMovieEditDialog(selectedMovie);
			if (okClicked) {
				masterData.set(index, selectedMovie);
				view.getLogicController().editMovie(selectedMovie);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Movie Selected");
			alert.setContentText("Please select a movie in the table.");
			
			alert.showAndWait();
		}
	}
	
	/**
	 * Method to handle the delete movie event
	 * calls the datalayer to delete the movie 
	 */
	private void handleDeleteMovie(int index) {
		Movie selectedMovie = masterData.get(index);
		if (selectedMovie != null) {
			masterData.remove(index);
			view.getLogicController().deleteMovie(selectedMovie);
			view.reloadVideos();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Movie Selected");
			alert.setContentText("Please select a movie in the table.");
			
			alert.showAndWait();
		}
	}

}
