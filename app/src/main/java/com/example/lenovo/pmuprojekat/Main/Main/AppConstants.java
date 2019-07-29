package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class AppConstants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private static Bitmap backgroundPicture;
    //private static GameEngine gameEngine;

    public static void initialize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        //gameEngine
        // gameEngine = new GameEngine();
    }

    public static void setBackgroundPicture(Bitmap picture) {
        AppConstants.backgroundPicture = picture;
    }

    public static Bitmap getBackgroundicture() {
        return backgroundPicture;
    }

    //public static GameEngine getGameEngine() {
    //    return null;
    //}

    public static void StopThread(Thread thread)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }
}
