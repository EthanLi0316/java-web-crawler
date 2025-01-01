import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WebCrawlerGUI extends Application {
    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");
    private RadioButton boostOption = new RadioButton("Use PageRank Boost");
    private ListView<String> searchResults = new ListView<>();

    // Getters for the controller to use
    public TextField getSearchField() { return searchField; }
    public Button getSearchButton() { return searchButton; }
    public RadioButton getBoostOption() { return boostOption; }
    public ListView<String> getSearchResults() { return searchResults; }

    public void start(Stage primaryStage) {
        VBox searchBox = new VBox(10);
        searchBox.getChildren().addAll(searchField, searchButton, boostOption);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        VBox resultsBox = new VBox(10);
        resultsBox.getChildren().add(searchResults);
        resultsBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(searchBox);
        layout.setCenter(resultsBox);

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Web Crawler");
        primaryStage.show();
    }
}