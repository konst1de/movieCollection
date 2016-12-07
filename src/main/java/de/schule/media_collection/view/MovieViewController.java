package de.schule.media_collection.view;

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
	private TableView<Movie> movieTable;

	@FXML
	private TableColumn<Movie, String> titleColumn;

	@FXML
	private TableColumn<Movie, Long> runtimeColumn;

	@FXML
	private TableColumn<Movie, String> genreColumn;

	@FXML
	private TableColumn<Movie, String> descriptionColumn;

	private View view;
	
	private List<Movie> movieList;
	private ObservableList<Movie> masterData;

	public MovieViewController(List<Movie> movieList, View view) {
		this.movieList = new ArrayList<Movie>(movieList);
		this.masterData = FXCollections.observableArrayList(this.movieList);
		this.view = view;
	}
	
	public ObservableList<Movie> getMovieData() {
		return masterData;
	}

	@FXML
	private void initialize() {
		movieTable.setItems(masterData);
		
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		runtimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
				
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
