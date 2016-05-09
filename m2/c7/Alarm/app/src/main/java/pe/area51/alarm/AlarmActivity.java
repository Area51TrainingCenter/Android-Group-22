package pe.area51.alarm;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initRingtone();
    }

    private void initRingtone() {
        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
    }

    private void playRingtone() {
        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void stopRingtone() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playRingtone();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRingtone();
    }
}
