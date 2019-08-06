package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.util.ArrayList;

public class GameEngine {
    private final int SOCCERBALL_INDEX = 6;

    private Vector2D touchDown;
    private Player selectedPlayer;

    static final Object _sync = new Object();

    private ArrayList<Ball> allObjectsOnField = null;
    private Goal goal;

    public GameEngine() {
        initFiledAndPlayers();

        touchDown = null;
        selectedPlayer = null;
    }

    public void initFiledAndPlayers() {
        goal = new Goal();
        goal.setGoalposts();

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
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 3 * 2, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag()));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag()));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3 * 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag()));
        allObjectsOnField.add(new SoccerBall(new Vector2D(AppConstants.SCREEN_WIDTH / 2, AppConstants.SCREEN_HEIGHT / 2),
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
                b.updatePosition();
                updateBallCollision(b);
                goal.resolveBallCollision(b);
                if (goal.checkBallCollision(b)){
                    //goal.checkBallCollision(b);
                    resetPlayersOnField();
                }
                b.applyFriction();
            }

            if (goal.checkIfPlayer1Goal(allObjectsOnField.get(SOCCERBALL_INDEX)))
                resetPlayersOnField();

            if (goal.checkIfPlayer2Goal(allObjectsOnField.get(SOCCERBALL_INDEX)))
                resetPlayersOnField();
        }
    }

    private void updateBallCollision(Ball myBall) {
        synchronized (_sync) {
            for (Ball b : allObjectsOnField) {
                if (myBall.isBall(b.getPosition()))
                    continue;

                if (myBall.checkBallCollision(b)){
                    myBall.resolveBallCollision(b);
                }
            }
        }
    }

    /*
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
    */

    //iscrtavanje svih elemenata
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawPlayers(canvas);
        drawGoals(canvas);
       // goal.draw(canvas);
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

    //iscrtavaju se golovi
    private void drawGoals(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getGoal(), 0, 0, new Paint());
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

    private void resetPlayersOnField() {
        allObjectsOnField.get(0).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 3, AppConstants.SCREEN_HEIGHT / 2));
        allObjectsOnField.get(0).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(1).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3));
        allObjectsOnField.get(1).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(2).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3 * 2));
        allObjectsOnField.get(2).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(3).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 3 * 2, AppConstants.SCREEN_HEIGHT / 2));
        allObjectsOnField.get(3).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(4).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3));
        allObjectsOnField.get(4).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(5).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3 * 2));
        allObjectsOnField.get(5).setVelocity(new Vector2D(0, 0));

        allObjectsOnField.get(6).setPosition(new Vector2D(AppConstants.SCREEN_WIDTH / 2, AppConstants.SCREEN_HEIGHT / 2));
        allObjectsOnField.get(6).setVelocity(new Vector2D(0, 0));
    }
}
