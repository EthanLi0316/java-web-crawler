import java.util.ArrayList;

public class Parser {
    private RawPage rawPage;

    public Parser(RawPage rawPage){
        this.rawPage = rawPage;
    }


    public String getTitle(){
        String rawData = this.rawPage.getRawData();
        return extractBetweenMarkers(rawData,"<title>","</title>").get(0);
    }
    public ArrayList<String> getWords(){
        String rawData = this.rawPage.getRawData();
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> contents = extractBetweenMarkers(rawData, "<p>", "</p>");
        for(String content : contents){
            for(String word : content.split("[\\n ]")){
                if(!word.isEmpty()){words.add(word);}
            }
        }
        return words;
    }
    public ArrayList<String> getUrls(){
        String rawData = this.rawPage.getRawData();
        ArrayList<String> urls = new ArrayList<String>();
         for(String url : extractBetweenMarkers(rawData,"href=\"","\"")){
             urls.add(getAbsoluteUrl(url));
         }
         return urls;
    }



    private String getAbsoluteUrl(String relativeUrl) {
        String pageUrl = this.rawPage.getUrl();
        if (relativeUrl.startsWith("http://")) {
            return relativeUrl;
        }
        String relativeAddress = relativeUrl.substring(1);
        for (int i = pageUrl.length() - 1; i >= 0; i--) {
            if (pageUrl.charAt(i) == '/') {
                String root = pageUrl.substring(0, i);
                return root + relativeAddress;
            }
        }
        return pageUrl;
    }

    private ArrayList<String> extractBetweenMarkers(String str, String startMarker, String endMarker) {
        ArrayList<String> contents = new ArrayList<String>();
        int i = 0;
        while (i < str.length()) {
            int startIndex = str.indexOf(startMarker, i);
            if (startIndex == -1) {
                break;
            }
            int endIndex = str.indexOf(endMarker, startIndex + startMarker.length());
            if (endIndex == -1) {
                break;
            }
            String content = str.substring(startIndex + startMarker.length(), endIndex);
            contents.add(content);
            i = endIndex + endMarker.length();
        }
        return contents;
    }
}
