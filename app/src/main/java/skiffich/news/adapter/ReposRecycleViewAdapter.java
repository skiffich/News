package skiffich.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import skiffich.news.view.DownloadImageTask;
import skiffich.news.api.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skiffich.news.R;

public class ReposRecycleViewAdapter extends RecyclerView.Adapter<ReposRecycleViewAdapter.ViewHolder>{

    private List<Article> articles = new ArrayList<>();
    private Context context;

    public ReposRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void addItem(Article article) {
        articles.add(article);
    }

    public void clearList() {
        articles.clear();
    }

    @Override
    public ReposRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        ReposRecycleViewAdapter.ViewHolder pvh = new ReposRecycleViewAdapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ReposRecycleViewAdapter.ViewHolder holder, final int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.description.setText(articles.get(position).getDescription());
        new DownloadImageTask(holder.poster).execute(articles.get(position).getUrlToImage());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_poster)
        ImageView poster;
        @BindView(R.id.textView_title)
        TextView title;
        @BindView(R.id.textView_description)
        TextView description;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
