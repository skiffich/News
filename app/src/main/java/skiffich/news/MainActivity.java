package skiffich.news;

import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private ReposRecycleViewAdapter reposRecycleViewAdapter;
    private int     currentPage = 1;
    private String  requestStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        reposRecycleViewAdapter = new ReposRecycleViewAdapter(this);

        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setAdapter(reposRecycleViewAdapter);

        searchView.setOnQueryTextListener(this);
        swipeContainer.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        reposRecycleViewAdapter.clearList();
        currentPage = 1;
        RetroClient.instance().everything(requestStr, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        reposRecycleViewAdapter.clearList();
        requestStr = query;
        currentPage = 1;
        RetroClient.instance().everything(query, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reposRecycleViewAdapter.clearList();
        requestStr = newText;
        currentPage = 1;
        RetroClient.instance().everything(newText, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
        return false;
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        RetroClient. instance().everything(requestStr, currentPage, MAX_ITEMS_PER_PAGE).enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseArt> call, Response<ResponseArt> response) {
        ResponseArt responseArt = response.body() != null ? response.body() : new ResponseArt();
        if (responseArt.getArticles() != null) {
            swipeContainer.setRefreshing(false);
            for (Article model : responseArt.getArticles())
                reposRecycleViewAdapter.addItem(model);
            reposRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<ResponseArt> call, Throwable t) {
        t.printStackTrace();
        Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
    }
}
