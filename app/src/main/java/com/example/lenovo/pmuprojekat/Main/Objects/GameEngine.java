package com.example.lenovo.pmuprojekat.Main.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.GameAudio.GameAudioPlayer;
import com.example.lenovo.pmuprojekat.Main.GameStats.GameStatus;
import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.util.ArrayList;

public class GameEngine {
    private final int SOCCERBALL_INDEX = 6;
    private final int TRANSPARENT_PAINT = 170;
    private final int OPAQUE_PAINT = 255;

    private final int MAX_TURN_TIME = 5000;
    private final int MIN_TURN_TIME = 1000;

    private Vector2D touchDown;
    private Player selectedPlayer;

    static final Object _sync = new Object();

    private ArrayList<Ball> allObjectsOnField = null;
    private Goal goal;
    private GameAudioPlayer gameAudioPlayer;
    private GameStatus gameStats;
    private Paint transparentPaint, opaquePaint;


    public GameEngine(Context context) {
        touchDown = null;
        selectedPlayer = null;

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setAlpha(TRANSPARENT_PAINT);

        opaquePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        opaquePaint.setAlpha(OPAQUE_PAINT);

        initFiledAndPlayers(context);
    }

    //inicijacija svih lopta na terenu i pocetak vodenje statistike igre
    public void initFiledAndPlayers(Context context) {
        goal = new Goal();
        goal.setGoalposts();

        allObjectsOnField = new ArrayList<>();
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 3, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag(),
                opaquePaint));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag(),
                opaquePaint));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4, AppConstants.SCREEN_HEIGHT / 3 * 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer1Flag(),
                opaquePaint));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 3 * 2, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag(),
                transparentPaint));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag(),
                transparentPaint));
        allObjectsOnField.add(new Player(new Vector2D(AppConstants.SCREEN_WIDTH / 4 * 3, AppConstants.SCREEN_HEIGHT / 3 * 2),
                AppConstants.PLAYER_MASS,
                AppConstants.PLAYER_RADIUS,
                AppConstants.getBitmapBank().getPlayer2Flag(),
                transparentPaint));
        allObjectsOnField.add(new SoccerBall(new Vector2D(AppConstants.SCREEN_WIDTH / 2, AppConstants.SCREEN_HEIGHT / 2),
                AppConstants.SOCCERBALL_MASS,
                AppConstants.SOCCERBALL_RADIUS,
                AppConstants.getBitmapBank().getBall(),
                opaquePaint));

        gameStats = new GameStatus("Stefan",
                "Sofija",
                AppConstants.getBitmapBank().getFiled(),
                AppConstants.getBitmapBank().getPlayer1Flag(),
                AppConstants.getBitmapBank().getPlayer2Flag());

        gameAudioPlayer = new GameAudioPlayer(context);
    }

    public void update() {
        updateAllObjects();
        if (checkPlayerTimeLimit()) {
            gameAudioPlayer.playSound(AppConstants.getBEEP());
            changePlayerTurns();
        }
    }

    private boolean checkPlayerTimeLimit(){
        if (System.currentTimeMillis() - gameStats.getTimeElapsed() >= MAX_TURN_TIME)
            return true;

        return false;
    }
    private void updateAllObjects() {
        synchronized (_sync) {
            for (Ball b : allObjectsOnField) {
                b.updatePosition();
                updateBallCollision(b);
                goal.resolveBallCollision(b);
                b.applyFriction();
            }

            if (goal.checkIfPlayer1Goal(allObjectsOnField.get(SOCCERBALL_INDEX))) {
                gameStats.player1Goal();
                gameAudioPlayer.playSound(AppConstants.getCROWD());
                resetPlayersOnField();
            }

            if (goal.checkIfPlayer2Goal(allObjectsOnField.get(SOCCERBALL_INDEX))) {
                gameStats.player2Goal();
                gameAudioPlayer.playSound(AppConstants.getCROWD());
                resetPlayersOnField();
            }
        }
    }

    private void updateBallCollision(Ball myBall) {
        synchronized (_sync) {
            for (Ball b : allObjectsOnField) {
                if (myBall.isBall(b.getPosition()))
                    continue;

                if (myBall.checkBallCollision(b)) {
                    gameAudioPlayer.playSound(AppConstants.getCOLLISION());
                    myBall.resolveBallCollision(b);
                }
            }
        }
    }

    //iscrtavanje svih elemenata
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawPlayers(canvas);
        drawGoals(canvas);
        gameStats.draw(canvas);
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
        int i = 0;

        for (Ball ball : allObjectsOnField) {
            if (ball instanceof Player) {
                if (((Player) ball).checkIfSelected(x, y)) {
                    //provera da li je igrac koji je na redu za igru izabran
                    if ((gameStats.isPlayer1Turn() && i < 3) || (gameStats.isPlayer2Turn() && i > 2)) {
                        ((Player) ball).setSelected(true);
                        selectedPlayer = (Player) ball;

                        touchDown = new Vector2D(x, y);
                        break;
                    }
                }
            }
            i++;
        }
    }

    //Nakon dizanja prsta pokrece se potez
    public void makeMove(float x, float y) {
        //izracunati i postaviti velocity
        if (selectedPlayer != null) {
            Vector2D movement = new Vector2D(x, y).subtract(selectedPlayer.position);
            Vector2D newVelocity = movement.divide(AppConstants.PLAYER_VELOCITY_SPEED);
            selectedPlayer.setVelocity(newVelocity);
            changePlayerTurns();
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

    private void changePlayerTurns() {
        Paint transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setAlpha(TRANSPARENT_PAINT);

        Paint opaquePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        opaquePaint.setAlpha(OPAQUE_PAINT);

        gameStats.nextPlayerTurn();

        for (int i = 0; i < 3; i++) {
            if (gameStats.isPlayer1Turn()) {
                allObjectsOnField.get(i).setPaint(opaquePaint);
            }else {
                allObjectsOnField.get(i).setPaint(transparentPaint);
            }
        }
        for (int i = 3; i < allObjectsOnField.size() - 1; i++) {
            if (gameStats.isPlayer2Turn()) {
                allObjectsOnField.get(i).setPaint(opaquePaint);
            }else {
                allObjectsOnField.get(i).setPaint(transparentPaint);
            }
        }

        gameStats.setTimeElapsed(System.currentTimeMillis());
    }

    //Postavljanje igraca u prvobitan raspored
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
