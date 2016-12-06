package de.schule.media_collection.view;

import de.schule.media_collection.logic.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MovieViewController {

	@FXML
	private TableView<Movie> movieTable;

	@FXML
	private TableColumn<Movie, Integer> idColumn;

	@FXML
	private TableColumn<Movie, String> titleColumn;

	@FXML
	private TableColumn<Movie, Long> runtimeColumn;

	@FXML
	private TableColumn<Movie, String> genreColumn;

	@FXML
	private TableColumn<Movie, String> descriptionColumn;

	private MovieView view;

	public MovieViewController() {
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		runtimeColumn.setCellValueFactory(cellData -> cellData.getValue().runtimeProperty().asObject());
		genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
	}
	
	public void setView(MovieView view) {
		this.view = view;
		
		movieTable.setItems(view.getMovieData());
	}
	
	@FXML
	private void handleNewMovie() {
		Movie tempMovie = new Movie();
		boolean okClicked = view.showMovieEditDialog(tempMovie);
		if (okClicked) {
			view.getMovieData().add(tempMovie);
		}
	}
	
	@FXML
	private void handleEditPerson () {
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
	private void handleDeletePerson() {
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
