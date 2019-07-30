package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.util.ArrayList;

public class GameEngine {
    private Vector2D touchDown;
    private Player selectedPlayer;

    private ArrayList<Ball> allObjectsOnField = null;

    public GameEngine() {
        initFiledAndPlayers();

        touchDown = null;
        selectedPlayer = null;
    }

    public void initFiledAndPlayers(){
        allObjectsOnField = new ArrayList<>();
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH/3, AppConstants.SCREEN_HEIGHT/2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag()));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH*2/3, AppConstants.SCREEN_HEIGHT/2),
                AppConstants.SOCCERBALL_MASS, AppConstants.SOCCERBALL_RADIUS, AppConstants.getBitmapBank().getPlayer2Flag()));
    }

    //iscrtavanje svih elemenata
    public void draw(Canvas canvas){
        drawBackground(canvas);
        drawPlayers(canvas);

        //drawGoals(canvas);
        //drawResult(canvas);
    }

    //ide se redom i iscrtava se svaki objekat koji se nalazi na terenu
    private void drawPlayers(Canvas canvas) {
        for (Ball ball:allObjectsOnField){
            ball.draw(canvas);
        }
    }

    //iscrtava se pozadina terena
    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getFiled(),0,0,new Paint());
    }

    //Proverava da li si na zadatim kordinatama nalazi neki disk
    public void checkIfSelected(float x, float y){
        for (Ball ball: allObjectsOnField){
            if (ball instanceof Player){
                if (((Player) ball).checkIfSelected(x,y)){
                    ((Player) ball).setSelected(true);
                    selectedPlayer = (Player) ball;
                }
            }
        }
    }
}
