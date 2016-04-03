package pe.area51.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String message;
        switch (intent.getAction()) {
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                if (intent.getBooleanExtra("state", false)) {
                    message = context.getString(R.string.airplane_mode_enabled);
                } else {
                    message = context.getString(R.string.airplane_mode_disabled);
                }
                break;
            case MainActivity.BUTTON_CLICKED_ACTION:
                message = context.getString(R.string.button_clicked);
                break;
            default:
                message = "";
                break;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
