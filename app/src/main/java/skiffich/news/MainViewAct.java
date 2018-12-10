package skiffich.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import skiffich.news.adapter.EndlessRecyclerView;
import skiffich.news.adapter.ReposRecycleViewAdapter;
import skiffich.news.api.RetroClient;
import skiffich.news.api.model.Article;
import skiffich.news.api.model.ResponseArt;

public class MainViewAct extends AppCompatActivity implements EndlessRecyclerView.OnLoadMoreListener,
        SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycleView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private ReposRecycleViewAdapter reposRecycleViewAdapter;
    private int currentPage = 1;
    private String requestStr = "";
    private boolean mIsInFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        reposRecycleViewAdapter = new ReposRecycleViewAdapter(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setAdapter(reposRecycleViewAdapter);

        searchView.setOnQueryTextListener(this);
        swipeContainer.setOnRefreshListener(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_search:
                            mIsInFavorites = false;
                            onRefresh();
                            break;
                        case R.id.nav_favorites:
                            mIsInFavorites = true;
                            reposRecycleViewAdapter.clearList();
                            reposRecycleViewAdapter.notifyDataSetChanged();
                            Gson gson = new Gson();
                            SharedPreferences result = getSharedPreferences("Favorites", Context.MODE_PRIVATE);
                            Map<String,?> favoriteArticles = result.getAll();
                            for (Map.Entry<String, ?> article : favoriteArticles.entrySet()) {
                                reposRecycleViewAdapter.addItem(gson.fromJson(article.getValue().toString(), Article.class));
                            }
                            reposRecycleViewAdapter.notifyDataSetChanged();
                            break;
                    }
                    return true;
                }
            };

    @Override
    public void onRefresh() {
        if (!mIsInFavorites) {
            reposRecycleViewAdapter.clearList();
            reposRecycleViewAdapter.notifyDataSetChanged();
            currentPage = 1;
        } else {
            reposRecycleViewAdapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!mIsInFavorites) {
            reposRecycleViewAdapter.clearList();
            reposRecycleViewAdapter.notifyDataSetChanged();
            requestStr = query;
            currentPage = 1;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!mIsInFavorites) {
            reposRecycleViewAdapter.clearList();
            reposRecycleViewAdapter.notifyDataSetChanged();
            requestStr = newText;
            currentPage = 1;
            if (!newText.contentEquals("")) {
            }
        }
        return true;
    }

    @Override
    public void onLoadMore() {
        if (!mIsInFavorites) {
            currentPage++;
        }
    }
}
