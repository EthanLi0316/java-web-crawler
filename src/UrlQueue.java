import java.util.ArrayList;
import java.util.HashSet;

public class UrlQueue {
    private ArrayList<String> uniqueUrlQueue;
    private HashSet<String> urlSet;

    public UrlQueue(){
        uniqueUrlQueue = new ArrayList<String>();
        urlSet = new HashSet<String>();
    }

    public void addUrls(ArrayList<String> urls) {
        for(String url : urls) {
            if (!urlSet.contains(url)) {
                uniqueUrlQueue.add(url);
                urlSet.add(url);
            }
        }
    }

    public void addUrl(String url) {
        if (!urlSet.contains(url)) {
            uniqueUrlQueue.add(url);
            urlSet.add(url);
        }
    }

    public void readdUrl(String url){
        if(urlSet.contains(url)){urlSet.remove(url);}
        addUrl(url);
    }

    public String popUrl() {
        if (uniqueUrlQueue.isEmpty()) {
            return null;
        }
        return this.uniqueUrlQueue.remove(0);
    }

    public boolean isEmpty(){return this.uniqueUrlQueue.isEmpty();}
}
