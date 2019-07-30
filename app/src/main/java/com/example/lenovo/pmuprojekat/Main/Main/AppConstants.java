package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.Objects.GameEngine;

public class AppConstants {
    //Mogao bih da dodam i gameStatistics ovde kako bi uvek moglo da se dode do njih

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;

    public static void initialize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        gameEngine = new GameEngine();
        bitmapBank = new BitmapBank(context.getResources());
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public static void setBitmapBank(BitmapBank bitmapBank) {
        AppConstants.bitmapBank = bitmapBank;
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static void setGameEngine(GameEngine gameEngine) {
        AppConstants.gameEngine = gameEngine;
    }

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
