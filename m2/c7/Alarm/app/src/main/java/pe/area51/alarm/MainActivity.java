package pe.area51.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText timeInSecondsEditText = (EditText) findViewById(R.id.edittext_time_in_seconds);
        findViewById(R.id.button_program_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String timeInSecondsString = timeInSecondsEditText.getText().toString();
                try {
                    final int timeInSeconds = Integer.parseInt(timeInSecondsString);
                    programAlarm(timeInSeconds);
                    showMessage(R.string.alarm_programmed);
                } catch (NumberFormatException e) {
                    showMessage(R.string.error_number);
                }
            }
        });
    }

    private void programAlarm(final int timeInSeconds) {
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, AlarmActivity.class), PendingIntent.FLAG_ONE_SHOT);
        final int timeInMillis = timeInSeconds * 1000;
        setExactRealtimeWakeupAlarm(alarmManager, SystemClock.elapsedRealtime() + timeInMillis, pendingIntent);
    }

    private static void setExactRealtimeWakeupAlarm(final AlarmManager alarmManager, final long timeInMillis, final PendingIntent operation) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, operation);
        } else {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, operation);
        }
    }

    private void showMessage(@StringRes final int stringId) {
        showMessage(getString(stringId));
    }

    private void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
