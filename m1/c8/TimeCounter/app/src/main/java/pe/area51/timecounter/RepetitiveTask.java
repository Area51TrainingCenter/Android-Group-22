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
	//Creamos el Runnable que publicará nuestra tarea "task" para que se ejecute a una frecuencia determinada ("frequencyInMillis").
        runnable = new Runnable() {
            @Override
            public void run() {
		/*
		Ejecutamos el método "run()" del Runnable "task" que tenemos como parámetro, de
		tal forma que se ejecute la tarea que queremos.
		*/ 
                task.run();
		/*
		Hacemos quu el runnable se publique a sí mismo en la cola de mensajes del hilo
		y que se ejecute dentro del tiempo "frequencyInMillis". De esta forma la tarea se repetirá.
		*/
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
	    //Publicamos la ejecución del runnable que repetirá nuestra tarea "task" pasada como parámetro en el contructor.
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
