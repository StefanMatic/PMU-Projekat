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
    public static final int NEW_GAME = 1;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static final float FRICTION = 0.98f;

    public static final float SOCCERBALL_MASS = 6;
    public static final float PLAYER_MASS = 10;

    public static final float SOCCERBALL_RADIUS = 30;
    public static final float PLAYER_RADIUS = 50;

    public static final float PLAYER_VELOCITY_SPEED = 10;
    public static final float COMPUTER_VELOCITY_SPEED = 3;

    public static final float GOAL_POST_WIDTH = 7;
    public static final float GOAL_POST_MASS = 200;

    public static final int CROWD_SOUND  = 1;
    public static final int COLLISION_SOUND = 2;
    public static final int BEEP_SOUND = 3;

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
        gameEngine = new GameEngine(context);

        myGameContext = context;
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

    public static int getCROWD() {
        return CROWD_SOUND;
    }

    public static int getCOLLISION() {
        return COLLISION_SOUND;
    }

    public static int getBEEP(){
        return BEEP_SOUND;
    }
}
