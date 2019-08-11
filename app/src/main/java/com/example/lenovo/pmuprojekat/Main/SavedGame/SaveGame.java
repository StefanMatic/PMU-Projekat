package com.example.lenovo.pmuprojekat.Main.SavedGame;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Objects.Ball;
import com.example.lenovo.pmuprojekat.Main.Objects.Player;
import com.example.lenovo.pmuprojekat.Main.Objects.SoccerBall;
import com.example.lenovo.pmuprojekat.Main.Objects.Vector2D;

import java.util.ArrayList;

public class SaveGame {
    private final int SOCCERBALL_INDEX = 6;
    private final int TRANSPARENT_PAINT = 170;
    private final int OPAQUE_PAINT = 255;

    private ArrayList<Vector2D> positions, velocities;
    private double timeStart, timeElapsed;
    private int currentSpeed, currentGoals, currentTime;
    private boolean isPlayOnGoals;
    private int fieldID;
    private SavePlayer player1, player2;

    public SaveGame(ArrayList<Vector2D> positions, ArrayList<Vector2D> velocities, SavePlayer player1, SavePlayer player2, double timeStart, double timeElapsed, int currentSpeed, int currentGoals, int currentTime, boolean isPlayOnGoals, int fieldID) {
        this.positions = positions;
        this.velocities = velocities;
        this.timeStart = timeStart;
        this.timeElapsed = timeElapsed;
        this.currentSpeed = currentSpeed;
        this.currentGoals = currentGoals;
        this.currentTime = currentTime;
        this.isPlayOnGoals = isPlayOnGoals;
        this.fieldID = fieldID;
        this.player1 = player1;
        this.player2 = player2;
    }

    public SaveGame(ArrayList<Ball> allBalls, SavePlayer player1, SavePlayer player2, double timeStart, double timeElapsed, int currentSpeed, int currentGoals, int currentTime, boolean isPlayOnGoals, int fieldID) {
        this.timeStart = timeStart;
        this.timeElapsed = timeElapsed;
        this.currentSpeed = currentSpeed;
        this.currentGoals = currentGoals;
        this.currentTime = currentTime;
        this.isPlayOnGoals = isPlayOnGoals;
        this.fieldID = fieldID;
        this.player1 = player1;
        this.player2 = player2;

        this.positions = new ArrayList<>();
        this.velocities = new ArrayList<>();

        for (Ball b : allBalls) {
            this.positions.add(new Vector2D(b.getPosition().getX(), b.getPosition().getY()));
            this.velocities.add(new Vector2D(b.getVelocity().getX(), b.getVelocity().getY()));
        }
    }

    public ArrayList<Vector2D> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector2D> positions) {
        this.positions = positions;
    }

    public ArrayList<Vector2D> getVelocities() {
        return velocities;
    }

    public void setVelocities(ArrayList<Vector2D> velocities) {
        this.velocities = velocities;
    }

    public double getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(double timeStart) {
        this.timeStart = timeStart;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentGoals() {
        return currentGoals;
    }

    public void setCurrentGoals(int currentGoals) {
        this.currentGoals = currentGoals;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public boolean getIsPlayOnGoals() {
        return isPlayOnGoals;
    }

    public void setIsPlayOnGoals(boolean isPlayOnGoals) {
        this.isPlayOnGoals = isPlayOnGoals;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public SavePlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(SavePlayer player1) {
        this.player1 = player1;
    }

    public SavePlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(SavePlayer player2) {
        this.player2 = player2;
    }


    public ArrayList<Ball> makeAllBalls() {
        ArrayList<Ball> allBalls = new ArrayList<>();

        Paint transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setAlpha(TRANSPARENT_PAINT);

        Paint opaquePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        opaquePaint.setAlpha(OPAQUE_PAINT);

        Paint player1Paint, player2Paint;
        if (player1.isMyTurn()) {
            player1Paint = opaquePaint;
            player2Paint = transparentPaint;
        } else {
            player2Paint = opaquePaint;
            player1Paint = transparentPaint;
        }

        //prolazimo kroz sve lopte osim fudbalske lopte koju cemo naknadno ubaciti
        for (int i = 0; i < positions.size() - 1; i++) {
            if (i < 3) {
                allBalls.add(new Player(positions.get(i),
                        AppConstants.PLAYER_MASS,
                        AppConstants.PLAYER_RADIUS,
                        AppConstants.getBitmapBank().getPlayer1Flag(),
                        player1Paint));
            } else {
                allBalls.add(new Player(positions.get(i),
                        AppConstants.PLAYER_MASS,
                        AppConstants.PLAYER_RADIUS,
                        AppConstants.getBitmapBank().getPlayer2Flag(),
                        player2Paint));
            }
            allBalls.get(i).setVelocity(velocities.get(i));
        }

        //postavljanje fudbalske lopte
        allBalls.add(new SoccerBall(positions.get(SOCCERBALL_INDEX),
                AppConstants.SOCCERBALL_MASS,
                AppConstants.SOCCERBALL_RADIUS,
                AppConstants.getBitmapBank().getBall(),
                opaquePaint));
        allBalls.get(SOCCERBALL_INDEX).setVelocity(velocities.get(SOCCERBALL_INDEX));

        return allBalls;
    }
}
