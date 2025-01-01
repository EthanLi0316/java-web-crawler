public class RawPage {
    private String url;
    private String rawData;

    public RawPage(String url){
        this.url = url;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getUrl() {return url;}
    public String getRawData() {return rawData;}
}
