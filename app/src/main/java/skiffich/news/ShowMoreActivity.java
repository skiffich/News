package skiffich.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import skiffich.news.adapter.CircleTransform;

public class ShowMoreActivity extends AppCompatActivity implements View.OnClickListener {

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

    private String mainImageUrlD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);
        ButterKnife.bind(this);
        getIncomingInent();
        mainImage.setOnClickListener(this);
    }

    private void getIncomingInent() {
        if(getIntent().hasExtra("mainImageUrlD")
            && getIntent().hasExtra("contentViewD")
            && getIntent().hasExtra("titleArtD")
            && getIntent().hasExtra("authorViewD")
            && getIntent().hasExtra("sourceViewD")
            && getIntent().hasExtra("publishedAtViewD")) {
            mainImageUrlD = getIntent().getStringExtra("mainImageUrlD");
            String contentViewD = getIntent().getStringExtra("contentViewD");
            String titleArtD = getIntent().getStringExtra("titleArtD");
            String authorViewD = getIntent().getStringExtra("authorViewD");
            String sourceViewD = getIntent().getStringExtra("sourceViewD");
            String publishedAtViewD = getIntent().getStringExtra("publishedAtViewD");

            setUI(contentViewD, titleArtD, authorViewD, sourceViewD, publishedAtViewD);
        }
    }

    private void setUI(String contentViewD
            , String titleArtD
            , String authorViewD
            , String sourceViewD
            , String publishedAtViewD) {
        Picasso.get()
                .load(mainImageUrlD)
                .centerCrop()
                .transform(new CircleTransform(3,0))
                .fit()
                .into(mainImage);
        titleArt.setText(titleArtD);
        contentView.setText(contentViewD);
        authorView.setText(authorViewD);
        sourceView.setText(sourceViewD);
        publishedAtView.setText(publishedAtViewD);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FullScreenImage.class);
        intent.putExtra("imageUrl", mainImageUrlD);
        this.startActivity(intent);
    }
}
