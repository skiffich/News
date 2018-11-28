package skiffich.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import skiffich.news.view.CircleTransform;

public class FullScreenImage extends AppCompatActivity {

    @BindView(R.id.fullScreenImage)
    ImageView fullScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen_image);

        ButterKnife.bind(this);

        getIncomingInent();
    }

    private void getIncomingInent() {
        if(getIntent().hasExtra("imageUrl")) {
            String mainImageUrlD = getIntent().getStringExtra("imageUrl");;
            Picasso.get()
                    .load(mainImageUrlD)
                    .centerCrop()
                    .transform(new CircleTransform(0,0))
                    .fit()
                    .into(fullScreenImage);
        }
    }

}
