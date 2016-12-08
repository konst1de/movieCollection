package de.schule.media_collection.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.schule.media_collection.logic.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

	private View view;
	
	private List<Movie> movieList;
	private List<Movie> userMovieList;
	private ObservableList<Movie> masterData;
	private ObservableList<Movie> userMasterData;

	public MovieViewController(List<Movie> movieList, List<Movie> userMovieList, View view) {
		this.movieList = new ArrayList<Movie>(movieList);
		this.userMovieList = new ArrayList<Movie>(userMovieList);
		this.masterData = FXCollections.observableArrayList(this.movieList);
		this.userMasterData = FXCollections.observableArrayList(this.userMovieList);
		this.view = view;
	}
	
	public ObservableList<Movie> getMovieData() {
		return masterData;
	}
	
	public ObservableList<Movie> getUserMovieData() {
		return userMasterData;
	}


	@FXML
	private void initialize() {
		initMovieTable();
		initUserMovieTable();
	}
	
	private void initUserMovieTable() {
		userMovieTable.setItems(userMasterData);
		
		userTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		userRuntimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		userGenreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		userDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		userReleaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
				
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

	private void initMovieTable() {
		movieTable.setItems(masterData);
		
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		runtimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		releaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
				
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

	@FXML
	private void handleNewMovie() {
		Movie tempMovie = new Movie();
		boolean okClicked = view.showMovieEditDialog(tempMovie);
		if (okClicked) {
			masterData.add(tempMovie);
		}
	}
	
	@FXML
	private void handleEditMovie () {
		Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
		if (selectedMovie != null) {
			boolean okClicked = view.showMovieEditDialog(selectedMovie);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Movie Selected");
			alert.setContentText("Please select a movie in the table.");
			
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleDeleteMovie() {
		int selectedIndex = movieTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			movieTable.getItems().remove(selectedIndex);
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
