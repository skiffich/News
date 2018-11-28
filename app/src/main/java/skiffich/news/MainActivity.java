package skiffich.news;

import android.content.Intent;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import skiffich.news.adapter.ReposRecycleViewAdapter;
import skiffich.news.api.RetroClient;
import skiffich.news.api.model.Article;
import skiffich.news.api.model.ResponseArt;
import skiffich.news.view.EndlessRecyclerView;

public class MainActivity extends AppCompatActivity implements EndlessRecyclerView.OnLoadMoreListener,
        Callback<ResponseArt>, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int MAX_ITEMS_PER_PAGE = 20;

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
                            searchView.setBackgroundResource(R.color.colorAccent);
                            break;
                        case R.id.nav_favorites:
                            searchView.setBackgroundResource(R.color.colorPrimaryDark);
                            break;
                    }
                    return true;
                }
            };

    @Override
    public void onRefresh() {
        reposRecycleViewAdapter.clearList();
        reposRecycleViewAdapter.notifyDataSetChanged();
        currentPage = 1;
        RetroClient.sInstance().everything(requestStr, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        reposRecycleViewAdapter.clearList();
        reposRecycleViewAdapter.notifyDataSetChanged();
        requestStr = query;
        currentPage = 1;
        RetroClient.sInstance().everything(query, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reposRecycleViewAdapter.clearList();
        reposRecycleViewAdapter.notifyDataSetChanged();
        requestStr = newText;
        currentPage = 1;
        if (!newText.contentEquals("")) {
            RetroClient.sInstance().everything(newText, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
        }
        return true;
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        RetroClient.sInstance().everything(requestStr, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseArt> call, Response<ResponseArt> response) {
        ResponseArt responseArt = response.body() != null ?
                response.body() : new ResponseArt("", 0, null);
        if (responseArt.getTotalResults() > 0) {
            swipeContainer.setRefreshing(false);
            for (Article article : responseArt.getArticles()) {
                reposRecycleViewAdapter.addItem(article);
            }
            reposRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<ResponseArt> call, Throwable t) {
        t.printStackTrace();
        Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }
}
