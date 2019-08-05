package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.lang.Thread;

//Klasa GameThread predstavlja nit koja iscrtava sve komponente na ekran
//uz odgovarajuci period spavanja kako bi to izgledalo prirodno
public class GameThread extends Thread {
    private static final int THREAD_SLEEP_TIME = 4;

    private SurfaceHolder mySurfaceHolder;
    private boolean isRunning;

    public GameThread(SurfaceHolder surfaceHolder) {
        mySurfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        super.run();

        while (isRunning){
            Canvas canvas = mySurfaceHolder.lockCanvas();
            AppConstants.getGameEngine().update();

            synchronized (mySurfaceHolder){
                //izracunavanje novih pozicija svih komponenata i njihovo iscrtavanje
                AppConstants.getGameEngine().draw(canvas);
            }

            if (canvas != null)
                mySurfaceHolder.unlockCanvasAndPost(canvas);


            try{
                sleep(THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
