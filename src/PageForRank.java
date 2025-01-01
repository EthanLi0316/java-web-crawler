import javax.naming.NameNotFoundException;

public class PageForRank implements Comparable<PageForRank>, SearchResult{
    private String pageUrl;
    private String title;
    private double boostScore;
    private double score;
    public static boolean BOOST;

    public PageForRank(String pageUrl, String title){
        this.pageUrl = pageUrl;
        this.title = title;
        this.boostScore = 0;
        this.score = 0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getScore() {
        if(BOOST) {
            return boostScore;
        }
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setBoostScore(double boostScore) {
        if(Double.isNaN(boostScore)){this.boostScore = 0.0;}
        else{this.boostScore = boostScore;}
    }

    @Override
    public int compareTo(PageForRank o) {
        double thisScore = Math.round(this.getScore() * 1000) / 1000.0;
        double otherScore = Math.round(o.getScore() * 1000) / 1000.0;

        if (thisScore > otherScore) {
            return -1;
        } else if (thisScore < otherScore) {
            return 1;
        } else {
            return this.getTitle().compareTo(o.getTitle());
        }
    }
}
