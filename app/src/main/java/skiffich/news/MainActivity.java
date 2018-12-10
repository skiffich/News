package skiffich.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

import skiffich.news.api.RetroClient;
import skiffich.news.api.model.Article;
import skiffich.news.api.model.ResponseArt;
import skiffich.news.adapter.ReposRecycleViewAdapter;
import skiffich.news.adapter.EndlessRecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_ITEMS_PER_PAGE = 20;
    public static final String FAVOURITES = "FAVOURITES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ManageViews manageViews = new ManageViews(this);
        ApplicationEx.getInstance().settManager(manageViews);
        ApplicationEx.getInstance().getFragmentManager().setMain(new MainViewAct());
    }
}
