package skiffich.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skiffich.news.Entity.ResponseArt;
import skiffich.news.Retrofit.RetroClient;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        button = (Button)  findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);

        setBtnListener();
    }

    private void setBtnListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    private void search() {
        String request = editText.getText().toString().replace(" ", "+");
        editText.setText("");

        Call<ResponseArt> responseArtCall = RetroClient.getNewsAPI().everything(request);
        responseArtCall.enqueue(new Callback<ResponseArt>() {
            @Override
            public void onResponse(Call<ResponseArt> call, Response<ResponseArt> response) {
                Log.d("MyLog", response.body().getArticles().get(0).getUrlToImage());
                textView.setText("Found " + response.body().getTotalResults() + " results");
            }

            @Override
            public void onFailure(Call<ResponseArt> call, Throwable t) {
                Log.d("MyLog", "Fail " + t);
                textView.setText("Fail");
            }
        });
    }
}
