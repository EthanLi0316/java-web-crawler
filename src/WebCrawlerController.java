import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class WebCrawlerController {
    private WebCrawlerGUI view;
    private WebCrawlerModel model;

    public WebCrawlerController(WebCrawlerGUI view, WebCrawlerModel model) {
        this.view = view;
        this.model = model;

        // Set up event handler for the search button
        view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent event) {
                performSearch();
            }
        });
    }

    // Method to perform a search when the search button is clicked
    private void performSearch() {
        String query = view.getSearchField().getText();
        boolean usePageRankBoost = false;
        usePageRankBoost = view.getBoostOption().isSelected();
        List<SearchResult> results = model.search(query, usePageRankBoost);

        // Convert search results to a format suitable for the ListView
        List<String> displayResults = new ArrayList<>();
        for (SearchResult result : results) {
            displayResults.add(result.getTitle() + " (Score: " + result.getScore() + ")");
        }

        // Update the ListView with the search results
        view.getSearchResults().setItems(FXCollections.observableArrayList(displayResults));
    }
}