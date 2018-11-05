package skiffich.news;
/*
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skiffich.news.Entity.Article;
import skiffich.news.Entity.ResponseArt;
import skiffich.news.Retrofit.RetroClient;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchView)  SearchView searchView;
    @BindView(R.id.listView)    ListView listView;

    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    String str;
    ResponseArt responseArt;

    public final List<ResponseArt> responseArts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = "";
        responseArt = null;
        list = new ArrayList<>();

        /*
        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Pineapple");
        list.add("Orange");
        list.add("Lychee");
        list.add("Gavava");
        list.add("Peech");
        list.add("Melon");
        list.add("Watermelon");
        list.add("Papaya");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        */


/*
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
*/

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import butterknife.OnTextChanged;
import skiffich.news.adapter.ReposRecycleViewAdapter;
import skiffich.news.api.RetroClient;
import skiffich.news.api.model.Article;
import skiffich.news.api.model.ResponseArt;
import skiffich.news.view.EndlessRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private int currentPage = 1;
    private String requestStr = "";

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
