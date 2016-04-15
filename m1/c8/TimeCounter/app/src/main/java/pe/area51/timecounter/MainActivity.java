package pe.area51.timecounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static long REPETITIVE_TASK_FREQUENCY_IN_MILLIS = 2000;
    private TextView statusTextView;
    private RepetitiveTask repetitiveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTextView = (TextView) findViewById(R.id.textview_status);
        findViewById(R.id.button_switch_time_counter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRepetitiveTask();
            }
        });
        repetitiveTask = createRepetitiveTask();
    }

    private void toggleRepetitiveTask() {
        if (repetitiveTask.isRunning()) {
            repetitiveTask.stop();
        } else {
            repetitiveTask.start();
        }
    }

    private RepetitiveTask createRepetitiveTask() {
        return new RepetitiveTask(REPETITIVE_TASK_FREQUENCY_IN_MILLIS, new Runnable() {
            long startTime = SystemClock.elapsedRealtime();

            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - startTime;
                updateStatusTextView(String.valueOf(elapsedTime));
            }
        });
    }

    private void updateStatusTextView(final String text) {
        statusTextView.setText(text);
    }

}
