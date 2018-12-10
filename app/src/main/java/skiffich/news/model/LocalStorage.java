package skiffich.news.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Map;

import skiffich.news.api.model.Article;
import static skiffich.news.MainActivity.FAVOURITES;

public class LocalStorage {
    private final SharedPreferences mPrefs;

    public LocalStorage( Context context) {
        mPrefs = context.getSharedPreferences(FAVOURITES, Context.MODE_PRIVATE);
    }

    public void addToStorage(Article article){
        mPrefs.edit().putString(article.getTitle(), new Gson().toJson(article)).commit();
    }

    public void removeFromStorage(Article article) {
        mPrefs.edit().remove(article.getTitle()).commit();
    }

    public boolean ifStorageContainsItem (String fileName) {
        if (mPrefs.contains(fileName)){
            return true;
        } else {
            return false;
        }
    }

    public Map<String, ?> getAllFromStorage(){
        return mPrefs.getAll();
    }
}
