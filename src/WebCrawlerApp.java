import javafx.application.Application;
import javafx.stage.Stage;

public class WebCrawlerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the model and view
        WebCrawlerModel model = new WebCrawlerModel();
        WebCrawlerGUI view = new WebCrawlerGUI();

        // Create the controller, passing the model and view
        WebCrawlerController controller = new WebCrawlerController(view, model);

        // Show the view
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}