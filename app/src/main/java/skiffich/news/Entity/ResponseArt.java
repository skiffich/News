package skiffich.news.Entity;

import java.util.ArrayList;

public class ResponseArt {
    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    public ResponseArt(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }
}
