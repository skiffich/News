package skiffich.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;

import skiffich.news.api.model.Article;

public class ManageViews {

    private final MainActivity activity;
    private Activity current;

    ManageViews(MainActivity activity){
        this.activity = activity;
    }

    public void setMain(final Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        current = activity;
    }

    public void setShowMore(final Activity activity, Article article){
        Intent intent = new Intent(activity, ShowMoreActivity.class);
        Gson gson = new Gson();
        String articleJson = gson.toJson(article);
        intent.putExtra("articleJson", articleJson);
        activity.startActivity(intent);
        current = activity;
    }

    public void setFullScreen(final Activity activity, Article article){
        Intent intent = new Intent(activity, FullScreenImage.class);
        Gson gson = new Gson();
        String articleJson = gson.toJson(article);
        intent.putExtra("articleJson", articleJson);
        activity.startActivity(intent);
        current = activity;
    }

    public Article getArguments(){
        if (current.getIntent().hasExtra("articleJson")) {
            Article article = new Gson().fromJson(current.getIntent().getStringExtra("articleJson"),
                    Article.class);
            return article;
        }
        return null;
    }
}
