import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Crawler {
    private UrlQueue urlQueue;
    private DataStorage dataStorage;
    private int retry;
    public static final int retryTime = 3;

    public Crawler(){
        this.urlQueue = new UrlQueue();
        this.dataStorage = new DataStorage();
        this.retry = 0;
    }

    public void crawl(String seedUrl){
        urlQueue.addUrl(seedUrl);
        while(!urlQueue.isEmpty()){
            // we need getOrDefault at the very first or the program will create duplicate page object
            String currentUrl = urlQueue.popUrl();
            Page currentPage = dataStorage.getUrlToPageMap().getOrDefault(currentUrl, new Page(currentUrl));
            dataStorage.getUrlToPageMap().put(currentUrl, currentPage);
            RawPage rawPage = new RawPage(currentUrl);
            Parser parser = new Parser(rawPage);
            // handle the exception gracefully
            try {
                rawPage.setRawData(WebRequester.readURL(currentUrl));
            }catch(MalformedURLException e){
                urlQueue.readdUrl(currentUrl);
                retry++;
                if(retry > retryTime){return;}else{continue;}
            }catch(IOException e){
                urlQueue.readdUrl(currentUrl);
                retry++;
                if(retry > retryTime){return;}else{continue;}
            }
            currentPage.setTitle(parser.getTitle());
            ArrayList<String> words = parser.getWords();
            currentPage.setWordsOccurrenceMap(words);
            currentPage.setTotalWords(words.size());
            for(String word : words) {
                dataStorage.getUniqueWords().add(word);
            }
            ArrayList<String> urlsInPage = parser.getUrls();
            urlQueue.addUrls(urlsInPage);
            for(String urlInPage : urlsInPage){
                Page page = dataStorage.getUrlToPageMap().getOrDefault(urlInPage, new Page(urlInPage));
                dataStorage.getUrlToPageMap().put(urlInPage, page);
                currentPage.setOutgoingUrl(page);
                page.setIncomingUrl(currentPage);
            }
        }
        PageRank pageRank = new PageRank(dataStorage);
        pageRank.storePageRankValues();
        dataStorage.saveToFile();
    }
}
