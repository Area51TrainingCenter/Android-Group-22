package pe.area51.servicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_start_service).setOnClickListener(this);
        findViewById(R.id.button_stop_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_service:
                startService();
                break;
            case R.id.button_stop_service:
                stopService();
                break;
        }
    }

    private void startService() {
        startService(getMyServiceIntent());
    }

    private void stopService() {
        stopService(getMyServiceIntent());
    }

    private Intent getMyServiceIntent() {
        return new Intent(this, MyService.class);
    }
}
