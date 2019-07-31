package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.util.ArrayList;

public class GameEngine {
    private Vector2D touchDown;
    private Player selectedPlayer;

    static final Object _sync = new Object();

    private ArrayList<Ball> allObjectsOnField = null;

    public GameEngine() {
        initFiledAndPlayers();

        touchDown = null;
        selectedPlayer = null;
    }

    public void initFiledAndPlayers() {
        allObjectsOnField = new ArrayList<>();
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 3, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag()));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag()));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3 * 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag()));
        allObjectsOnField.add(new SoccerBall(new Vector2D(AppConstants.SCREEN_WIDTH * 2 / 3, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.SOCCERBALL_MASS,
                AppConstants.SOCCERBALL_RADIUS,
                AppConstants.getBitmapBank().getBall()));
    }

    public void update() {
        updateAllObjects();
    }

    private void updateAllObjects() {
        synchronized (_sync) {
            for (Ball b : allObjectsOnField) {
                //Prilikom postavljanja novih koordinata lopta, proverava se i da li se udara u zid
                b.updatePosition();
            }

            updateBallCollision();
        }
    }

    private void updateBallCollision() {
        synchronized (_sync) {
            for (int i = 0; i < allObjectsOnField.size(); i++) {
                for (int j = i + 1; j < allObjectsOnField.size(); j++) {
                    if (allObjectsOnField.get(i).checkBallCollision(allObjectsOnField.get(j))){
                        allObjectsOnField.get(i).resolveBallCollision(allObjectsOnField.get(j));
                    }
                }
            }
        }
    }


    //iscrtavanje svih elemenata
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawPlayers(canvas);

        //drawGoals(canvas);
        //drawResult(canvas);
    }

    //ide se redom i iscrtava se svaki objekat koji se nalazi na terenu
    private void drawPlayers(Canvas canvas) {
        synchronized (_sync) {
            for (Ball ball : allObjectsOnField) {
                ball.draw(canvas);
            }
        }
    }

    //iscrtava se pozadina terena
    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getFiled(), 0, 0, new Paint());
    }

    //Proverava da li si na zadatim kordinatama nalazi neki disk
    public void checkIfSelected(float x, float y) {
        for (Ball ball : allObjectsOnField) {
            if (ball instanceof Player) {
                if (((Player) ball).checkIfSelected(x, y)) {
                    ((Player) ball).setSelected(true);
                    selectedPlayer = (Player) ball;

                    touchDown = new Vector2D(x, y);
                    break;
                }
            }
        }
    }

    //Nakon dizanja prsta pokrece se potez
    public void makeMove(float x, float y) {
        //izracunati i postaviti velocity
        if (selectedPlayer != null) {
            Vector2D movement = new Vector2D(x, y).subtract(selectedPlayer.position);
            Vector2D newVelocity = movement.divide(AppConstants.PLAYER_VELOCITY_SPEED);
            selectedPlayer.setVelocity(newVelocity);
        }

        //resetovanje
        resetForNextTurn();
    }

    private void resetForNextTurn() {
        if (selectedPlayer != null)
            selectedPlayer.setSelected(false);

        touchDown = null;
        selectedPlayer = null;
    }
}
