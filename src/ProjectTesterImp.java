import java.io.File;
import java.util.List;

public class ProjectTesterImp implements ProjectTester {
    private Crawler crawler;
    private Getter getter;

    @Override
    public void initialize() {
        File dataStorageFile = new File("dataStorage.ser");
        if(dataStorageFile.exists()) {
            dataStorageFile.delete();
        }
    }

    @Override
    public void crawl(String seedURL) {
        this.crawler = new Crawler();
        this.crawler.crawl(seedURL);
        getter = new Getter();

    }

    public void setGetter(Getter getter) {
        this.getter = getter;
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        return getter.getOutgoingLinks(url);
        // Implement this method using your data structures
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        return getter.getIncomingLinks(url);
        // Implement this method using your data structures
    }

    @Override
    public double getPageRank(String url) {
        return getter.getPageRank(url);
        // Implement this method using your PageRankCalculator
    }

    @Override
    public double getIDF(String word) {
        return getter.getIDF(word);
    }

    @Override
    public double getTF(String url, String word) {
        return getter.getTF(url, word);
    }

    @Override
    public double getTFIDF(String url, String word) {
        return getter.getTFIDF(url, word);
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        return getter.search(query, boost, X);
    }
}