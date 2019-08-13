package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.Objects.GameEngine;
import com.example.lenovo.pmuprojekat.Main.View.GameView;

public class AppConstants {
    //Mogao bih da dodam i gameStatistics ovde kako bi uvek moglo da se dode do njih

    //Constants
    public static final int NEW_GAME = 1;

    public static final int MIN_GAME_GOALS = 1;
    public static final int MAX_GAME_GOALS = 10;
    public static final int MIN_GAME_TIME = 1;
    public static final int MAX_GAME_TIME = 60;

    //default vrednosti igre
    public static final int DEFAULT_SPEED = 2;
    public static final int DEFAULT_TIME = 1;
    public static final int DEFAULT_GOALS = 2;
    public static final int DEFAULT_FIELDS_INDEX = 0;

    public static int CURRENT_SPEED;
    public static int CURRENT_TIME;
    public static int CURRENT_GOALS;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static final float FRICTION = 0.98f;

    public static final float SOCCERBALL_MASS = 6;
    public static final float PLAYER_MASS = 10;

    public static final float SOCCERBALL_RADIUS = 30;
    public static final float PLAYER_RADIUS = 50;

    public static final float PLAYER_VELOCITY_SPEED = 7;
    public static final float COMPUTER_VELOCITY_SPEED = 7;

    public static final float GOAL_POST_WIDTH = 7;
    public static final float GOAL_POST_MASS = 200;

    public static final int CROWD_SOUND  = 1;
    public static final int COLLISION_SOUND = 2;
    public static final int BEEP_SOUND = 3;

    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;
    public static boolean playOnGoals;

    public static Context myGameContext;
    public static boolean gameOver;
    public static boolean gamePaused;

    public static String player1Name, player2Name;
    public static int player1Score, player2Score;
    public static double gameDuration;

    public static void initialize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine(context);

        //postavljanje pocetne vrednosti koji mogu kasnije da se menjaju
        //promeniti da se ne radi svaki put kada se ude u startActivity
        CURRENT_SPEED = DEFAULT_SPEED;
        CURRENT_GOALS = DEFAULT_GOALS;
        CURRENT_TIME = DEFAULT_TIME;
        playOnGoals = true;
        gameOver = false;

        myGameContext = context;
        gamePaused = false;
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

    public static void stopThread(Thread thread) {
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

    public static int getCurrentSpeed() {
        return CURRENT_SPEED;
    }

    public static void setCurrentSpeed(int currentSpeed) {
        CURRENT_SPEED = currentSpeed;
    }

    public static int getCurrentTime() {
        return CURRENT_TIME;
    }

    public static void setCurrentTime(int currentTime) {
        CURRENT_TIME = currentTime;
    }

    public static int getCurrentGoals() {
        return CURRENT_GOALS;
    }

    public static void setCurrentGoals(int currentGoals) {
        CURRENT_GOALS = currentGoals;
    }

    public static boolean isPlayOnGoals() {
        return playOnGoals;
    }

    public static void setPlayOnGoals(boolean playOnGoals) {
        AppConstants.playOnGoals = playOnGoals;
    }

    public static boolean isGameOver() {
        return AppConstants.gameOver;
    }

    public static void setGameOver(boolean gameOver) {
        AppConstants.gameOver = gameOver;
    }

    public static boolean isGamePaused() {
        return gamePaused;
    }

    public static void setGamePaused(boolean gamePaused) {
        AppConstants.gamePaused = gamePaused;
    }

    public static String getPlayer1Name() {
        return player1Name;
    }

    public static void setPlayer1Name(String player1Name) {
        AppConstants.player1Name = player1Name;
    }

    public static String getPlayer2Name() {
        return player2Name;
    }

    public static void setPlayer2Name(String player2Name) {
        AppConstants.player2Name = player2Name;
    }

    public static int getPlayer1Score() {
        return player1Score;
    }

    public static void setPlayer1Score(int player1Score) {
        AppConstants.player1Score = player1Score;
    }

    public static int getPlayer2Score() {
        return player2Score;
    }

    public static void setPlayer2Score(int player2Score) {
        AppConstants.player2Score = player2Score;
    }

    public static double getGameDuration() {
        return gameDuration;
    }

    public static void setGameDuration(double gameDuration) {
        AppConstants.gameDuration = gameDuration;
    }

    public static void resetForNewGame(){
        player1Name = null;
        player2Name = null;
        gameDuration = 0;
        player1Score = 0;
        player2Score = 0;
        gameOver = false;
        gamePaused = false;
        myGameContext = null;

        gameEngine.setGameView(null);
        gameEngine.setGameStats(null);
        gameEngine.setGameOver(false);
    }
}
