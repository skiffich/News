package skiffich.news.Entity;

import java.util.ArrayList;

public class ResponseArt {
    String              status;
    int                 totalResults;
    ArrayList<Article>  articles;

    public ResponseArt() {
        status          = "";
        totalResults    = 0;
        articles        = null;
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
