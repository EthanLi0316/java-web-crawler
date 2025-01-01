import java.util.*;

public class Search {
    private DataStorage dataStorage;
    private TfIdf tfIdf;
    private PriorityQueue<PageForRank> rank;

    public Search(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.tfIdf = new TfIdf(dataStorage);
        this.rank = new PriorityQueue<PageForRank>();
    }

    public PriorityQueue<PageForRank> getRank() {
        return rank;
    }

    private TreeMap<String, Double> searchWordsFreq(String phrase){
        String[] searchWords = Arrays.stream(phrase.trim().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        TreeMap<String, Double> wordFreq = new TreeMap<>();
        for (String word : searchWords) {
            double freq = wordFreq.getOrDefault(word, 0.0);
            wordFreq.put(word, freq + 1.0 / searchWords.length);
        }
        return wordFreq;
    }

    private ArrayList<Double> phraseVector(String phrase) {
        TreeMap<String, Double> wordFreq = searchWordsFreq(phrase);
        ArrayList<Double> idf = new ArrayList<>();
        ArrayList<Double> tf = new ArrayList<>();
        for (Map.Entry<String, Double> entry : wordFreq.entrySet()){
            idf.add(tfIdf.calculateIdf(entry.getKey()));
            tf.add(entry.getValue());
        }
        return Matmult.tfIdfVector(tf, idf);
    }

    private ArrayList<Double> docVector(String url, String phrase) {
        TreeMap<String, Double> wordFreq = searchWordsFreq(phrase);
        ArrayList<Double> idf = new ArrayList<>();
        ArrayList<Double> tf = new ArrayList<>();
        for (Map.Entry<String, Double> entry : wordFreq.entrySet()){
            idf.add(tfIdf.calculateIdf(entry.getKey()));
            tf.add(tfIdf.calculateTf(url, entry.getKey()));
        }
        ArrayList<Double> tfidf = Matmult.tfIdfVector(tf, idf);
        return tfidf;
    }

    private double pageScore(String phrase, String url){
        ArrayList<Double> docTfidf = docVector(url, phrase);
        ArrayList<Double> phraseTfidf = phraseVector(phrase);
        double result = Matmult.calculateCosineSimilarity(docVector(url, phrase), phraseVector(phrase));
        if(Double.isNaN(result)){return 0;}
        return result;
    }

    public void storeScores(String phrase){
        for(Map.Entry<String, Page> entry : this.dataStorage.getUrlToPageMap().entrySet()){
            PageForRank pageForRank = new PageForRank(entry.getKey(), entry.getValue().getTitle());
            pageForRank.setScore(pageScore(phrase, entry.getKey()));
            pageForRank.setBoostScore(pageScore(phrase, entry.getKey())*entry.getValue().getPageRank());
            rank.add(pageForRank);
        }
    }
}