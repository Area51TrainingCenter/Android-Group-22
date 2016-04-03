package pe.area51.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    public static final String ARG_NAME = "name";
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        welcomeTextView = (TextView) findViewById(R.id.textview_welcome);
        final String name = getIntent().getStringExtra(ARG_NAME);
        showWelcome(name);
    }

    private void showWelcome(final String name) {
        welcomeTextView.setText(getString(R.string.welcome_message, name));
    }

}
