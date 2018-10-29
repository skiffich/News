package skiffich.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText    editText;
    private Button      button;
    private TextView    textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText    = (EditText)findViewById(R.id.editText);
        button      = (Button)  findViewById(R.id.button);
        textView    = (TextView)findViewById(R.id.textView);
    }
}
