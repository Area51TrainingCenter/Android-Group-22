package pe.area51.timecounter;

import android.os.Handler;

public class RepetitiveTask {

    private boolean isRunning;

    private final long frequencyInMillis;
    private final Handler callerThreadHandler;
    private final Runnable runnable;

    public RepetitiveTask(final long frequencyInMillis, final Runnable task) {
        this.frequencyInMillis = frequencyInMillis;
        callerThreadHandler = new Handler();
        isRunning = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                task.run();
                callerThreadHandler.postDelayed(this, frequencyInMillis);
            }
        };
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean start() {
        if (isRunning()) {
            return false;
        } else {
            callerThreadHandler.postDelayed(runnable, frequencyInMillis);
            isRunning = true;
            return true;
        }
    }

    public boolean stop() {
        if (isRunning()) {
            callerThreadHandler.removeCallbacks(runnable);
            isRunning = false;
            return true;
        } else {
            return false;
        }
    }
}
