package pe.area51.helloandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nameEditText = (EditText) findViewById(R.id.edittext_name);
        findViewById(R.id.button_register)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name = nameEditText.getText().toString();
                        final Intent intent = new Intent(MainActivity.this, HelloActivity.class);
                        intent.putExtra(HelloActivity.ARG_NAME, name);
                        startActivity(intent);
                    }
                });
    }
}
