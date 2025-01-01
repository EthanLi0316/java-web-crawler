import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Getter {
    private DataStorage dataStorage;
    private TfIdf tfIdf;

    public Getter(){
        this.dataStorage = DataStorage.readFromFile();
        this.tfIdf = new TfIdf(dataStorage);
    }

    public List<String> getOutgoingLinks(String url){
        ArrayList<String> outgoingUrls = new ArrayList<>();
        Page currentpage = this.dataStorage.getUrlToPageMap().get(url);
        if(currentpage == null){return null;}
        HashSet<String> outgoingUrlsSet = currentpage.getOutgoingUrls();
        for(String urlInset : outgoingUrlsSet){
            outgoingUrls.add(urlInset);
        }
        return outgoingUrls;
    }

    public List<String> getIncomingLinks(String url) {
        ArrayList<String> incomingUrls = new ArrayList<>();
        Page currentpage = this.dataStorage.getUrlToPageMap().get(url);
        if(currentpage == null){return null;}
        HashSet<String> incomingUrlsSet = currentpage.getIncomingUrls();
        for(String urlInset : incomingUrlsSet){
            incomingUrls.add(urlInset);
        }
        return incomingUrls;
        // Implement this method using your data structures
    }

    public double getPageRank(String url) {
        Page page = this.dataStorage.getUrlToPageMap().get(url);
        if(page ==null){return -1;}
        return page.getPageRank();
        // Implement this method using your PageRankCalculator
    }

    public double getIDF(String word) {
        return tfIdf.calculateIdf(word);
    }

    public double getTF(String url, String word) {
        return tfIdf.calculateTf(url, word);
    }

    public double getTFIDF(String url, String word) {
        return tfIdf.calculateTfIdf(url, word);
    }

    public List<SearchResult> search(String query, boolean boost, int X) {
        Search search = new Search(this.dataStorage);
        PageForRank.BOOST = boost;
        search.storeScores(query);
        List<SearchResult> searchResults = new ArrayList<>();
        for (int i = 0; i < X; i++) {
            searchResults.add(search.getRank().poll());
        }
        return searchResults;
    }

}
