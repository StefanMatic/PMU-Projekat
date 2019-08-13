package com.example.lenovo.pmuprojekat.Main.View;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Objects.GameThread;


//GameView klasa predstavlja klasu koja pokrece nit koja je zaduzena za iscrtavanje cele igrice
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Context context;
    private GameThread gameThread;

    public GameView(Context context) {
        super(context);
        this.context = context;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);


        //Napravi thread
        gameThread = new GameThread(surfaceHolder);
        gameThread.setRunning(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (gameThread.isRunning()) {
            gameThread.start();
        } else {
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Nista ne diramo
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        AppConstants.stopThread(gameThread);
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    public void stopThreadFromRunning(){
        gameThread.setRunning(false);
        //AppConstants.stopThread(gameThread);
    }

    public void stopThreadForever(){
        AppConstants.stopThread(gameThread);
    }

    public void killThread() {
        gameThread.interrupt();
    }
}
