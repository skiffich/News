package skiffich.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.squareup.picasso.Picasso;
import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import skiffich.news.adapter.CircleTransform;
import skiffich.news.api.model.Article;


public class ShowMoreActivity extends AppCompatActivity {

    @BindView(R.id.mainImage)
    ImageButton mainImage;
    @BindView(R.id.titleArt)
    TextView titleArt;
    @BindView(R.id.contentView)
    TextView contentView;
    @BindView(R.id.authorView)
    TextView authorView;
    @BindView(R.id.sourceView)
    TextView sourceView;
    @BindView(R.id.publishedAtView)
    TextView publishedAtView;
    @BindView(R.id.addToFavoritesBtn)
    Button addToFavoritesBtn;

    private Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);
        ButterKnife.bind(this);
        getIncomingInent();
    }

    private void getIncomingInent() {
        if(getIntent().hasExtra("articleJson")) {
            Gson gson = new Gson();
            mArticle = gson.fromJson(getIntent().getStringExtra("articleJson"), Article.class);

            setUI();
        }
    }

    private void setUI() {
        Picasso.get()
                .load(mArticle.getUrlToImage())
                .centerCrop()
                .transform(new CircleTransform(3,0))
                .fit()
                .into(mainImage);
        titleArt.setText(mArticle.getTitle());
        contentView.setText(mArticle.getContent());
        authorView.setText(mArticle.getAuthor());
        sourceView.setText(mArticle.getSource().getName());
        publishedAtView.setText(mArticle.getPublishedAt());

        SharedPreferences result = getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        Map<String,?> favoriteArticles = result.getAll();
        if (favoriteArticles.containsKey(mArticle.getTitle())) {
            addToFavoritesBtn.setBackgroundResource(R.color.colorAccent);
        }
    }

    @OnClick(R.id.mainImage)
    public void onMainImageClick(View v) {
        Intent intent = new Intent(this, FullScreenImage.class);
        intent.putExtra("imageUrl", mArticle.getUrlToImage());
        this.startActivity(intent);
    }

    @OnClick(R.id.addToFavoritesBtn)
    public void onAddToFavoritesBtnClick(View v) {
        SharedPreferences result = getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        Editor ed = result.edit();

        Map<String,?> favoriteArticles = result.getAll();
        if (favoriteArticles.containsKey(mArticle.getTitle())) {
            ed.remove(mArticle.getTitle());
            ed.apply();
            addToFavoritesBtn.setBackgroundResource(R.color.colorStandart);
        } else {
            Gson gson = new Gson();
            ed.putString(mArticle.getTitle(), gson.toJson(mArticle));
            ed.apply();
            addToFavoritesBtn.setBackgroundResource(R.color.colorAccent);
        }
    }
}
