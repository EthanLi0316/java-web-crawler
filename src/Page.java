import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Page implements Serializable {
    private String pageUrl;
    private String title;
    private HashMap<String, Integer> wordsOccurrenceMap;
    private int totalWords;
    private HashSet<String> outgoingUrls;
    private HashSet<String> incomingUrls;
    private double pageRank;

    public Page(String url){
        this.pageUrl = url;
        this.title = null;
        this.wordsOccurrenceMap = new HashMap<String, Integer>();
        this.outgoingUrls = new HashSet<String>();
        this.incomingUrls = new HashSet<String>();
        this.pageRank = 0;
    }

    public String getPageUrl(){return this.pageUrl;}
    public String getTitle(){return this.title;}
    public HashMap<String, Integer> getWordsOccurrenceMap(){return this.wordsOccurrenceMap;}
    public int getTotalWords() {return totalWords;}
    public HashSet<String> getOutgoingUrls(){return this.outgoingUrls;}
    public HashSet<String> getIncomingUrls(){return this.incomingUrls;}
    public double getPageRank() {return pageRank;}

    public void setTitle(String title){this.title = title;}
    public void setWordsOccurrenceMap(ArrayList<String> words){
        for(String word : words){
            int count = this.getWordsOccurrenceMap().getOrDefault(word, 0);
            count = count + 1;
            this.getWordsOccurrenceMap().put(word, count);
        }
    }
    public void setTotalWords(int totalWords) {this.totalWords = totalWords;}
    public void setOutgoingUrl(Page page) {
        if(!page.getPageUrl().equals(this.getPageUrl())) {
            this.getOutgoingUrls().add(page.getPageUrl());
        }
    }
    public void setIncomingUrl(Page incomingPage) {this.incomingUrls.add(incomingPage.getPageUrl());}
    public void setPageRank(double pageRank) {this.pageRank = pageRank;}
}
