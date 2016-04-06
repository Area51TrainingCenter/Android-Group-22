package pe.area51.servicetest;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

public class MyService extends Service {

    public static final String TAG = "MyService";
    public static final int NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showMessage("onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID, createForegroundNotification());
        showMessage("onCreate()");
    }

    @Override
    public void onDestroy() {
        showMessage("onDestroy()");
        super.onDestroy();
    }

    private Notification createForegroundNotification() {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(TAG)
                .build();
    }

    private void showMessage(final String message) {
        Toast.makeText(MyService.this, message, Toast.LENGTH_SHORT).show();
    }

}
