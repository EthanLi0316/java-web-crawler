import java.util.List;

public class WebCrawlerModel {
    private ProjectTesterImp tester;
    private Getter getter; 

    public WebCrawlerModel() {
        this.tester = new ProjectTesterImp();
        getter = new Getter();
        tester.setGetter(getter);
    }

    // Method to perform a search
    public List<SearchResult> search(String query, boolean usePageRankBoost) {
        int X = 10; // Number of top results to return
        return tester.search(query, usePageRankBoost, X);
    }
}