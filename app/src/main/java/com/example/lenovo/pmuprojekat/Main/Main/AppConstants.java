package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.Objects.GameEngine;

public class AppConstants {
    //Mogao bih da dodam i gameStatistics ovde kako bi uvek moglo da se dode do njih

    //Constants
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static final float FRICTION = 0.98f;

    public static float SOCCERBALL_MASS = 6;
    public static float PLAYER_MASS = 10;

    public static float SOCCERBALL_RADIUS = 30;
    public static float PLAYER_RADIUS = 50;

    public static float PLAYER_VELOCITY_SPEED = 10;
    public static float COMPUTER_VELOCITY_SPEED = 3;

    public static float GOAL_POST_WIDTH = 7;
    public static float GOAL_POST_MASS = 200;

    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;

    public static Context myGameContext;

    public static void initialize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine();
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

    public static void StopThread(Thread thread) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public static Context getMyGameContext() {
        return myGameContext;
    }

    public static void setMyGameContext(Context myGameContext) {
        AppConstants.myGameContext = myGameContext;
    }
}
