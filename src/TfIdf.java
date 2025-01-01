public class TfIdf {
    private DataStorage dataStorage;

    public TfIdf(DataStorage dataStorage){
        this.dataStorage = dataStorage;
    }

    public double calculateIdf(String word){
        int countOccurrence = 0;
        for(Page page : this.dataStorage.getUrlToPageMap().values()) {
            if (page.getWordsOccurrenceMap().containsKey(word)) {
                countOccurrence++;
            }
        }
        if(countOccurrence == 0){return 0;}
        return Math.log(this.dataStorage.getUrlToPageMap().size() / (double) (1 + countOccurrence)) / Math.log(2);
    }

    public double calculateTf(String url, String word){
        Page page = this.dataStorage.getUrlToPageMap().get(url);
        if(page == null){return 0;}
        return (double)page.getWordsOccurrenceMap().getOrDefault(word,0)/(double)page.getTotalWords();
    }

    public double calculateTfIdf(String url, String word){
        return (Math.log(1+calculateTf(url, word)) / Math.log(2)) * calculateIdf(word);
    }
}
