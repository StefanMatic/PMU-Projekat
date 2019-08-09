package com.example.lenovo.pmuprojekat.Main.GameStats;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Objects.Ball;
import com.example.lenovo.pmuprojekat.R;

import java.util.ArrayList;

public class GameStatus {
    private String player1, player2;
    private boolean player1Computer, player2Computer;
    private boolean player1Turn, player2Turn;
    private int player1Score, player2Score;
    private ArrayList<Ball> allBalls;
    private Bitmap fieldImage;
    private Bitmap player1Flag, player2Flag;
    private double timeStart;
    private double timeElapsed;
    private Paint myPaint;
    private boolean currentPlayerTurnComputer;

    @SuppressLint("NewApi")
    public GameStatus(String player1, String player2, Bitmap fieldImage, Bitmap player1Flag, Bitmap player2Flag, boolean player1Computer, boolean player2Computer) {
        this.player1 = player1;
        this.player2 = player2;
        this.fieldImage = fieldImage;
        this.player1Flag = player1Flag;
        this.player2Flag = player2Flag;
        this.player1Computer = player1Computer;
        this.player2Computer = player2Computer;
        this.currentPlayerTurnComputer = player1Computer;

        player1Turn = true;
        player2Turn = false;

        player1Score = 0;
        player2Score = 0;

        allBalls = new ArrayList<Ball>();

        //Prebaci u AppConstants kasnije i obrati pasnju kako ces da cuvas stvari u bazi podataka
        timeStart = System.currentTimeMillis();
        timeElapsed = timeStart;

        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        myPaint.setTextSize(50);
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public boolean isPlayer2Turn() {
        return player2Turn;
    }

    public void setPlayer2Turn(boolean player2Turn) {
        this.player2Turn = player2Turn;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public ArrayList<Ball> getAllBalls() {
        return allBalls;
    }

    public void setAllBalls(ArrayList<Ball> allBalls) {
        this.allBalls = allBalls;
    }

    public void nextPlayerTurn() {
        if (player1Turn) {
            player1Turn = false;
            player2Turn = true;

            currentPlayerTurnComputer = player2Computer;
        } else {
            player2Turn = false;
            player1Turn = true;

            currentPlayerTurnComputer = player1Computer;
        }
    }

    public void draw(Canvas canvas) {
        String score = player1Score + " - " + player2Score;
        canvas.drawText(score, AppConstants.SCREEN_WIDTH / 8 * 3, AppConstants.SCREEN_HEIGHT / 10, myPaint);
        canvas.drawText(currentTimeAsString(), AppConstants.SCREEN_WIDTH / 8 * 5, AppConstants.SCREEN_HEIGHT / 10, myPaint);
        canvas.drawText(player1, AppConstants.SCREEN_WIDTH / 10, AppConstants.SCREEN_HEIGHT / 10, myPaint);
        canvas.drawText(player2, AppConstants.SCREEN_WIDTH / 10 * 8, AppConstants.SCREEN_HEIGHT / 10, myPaint);
    }

    public boolean isPlayer1Computer() {
        return player1Computer;
    }

    public boolean isPlayer2Computer() {
        return player2Computer;
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

    //Dat je gol na levoj strani terena - player1 strani
    public void player1Goal() {
        player2Score++;
    }

    //Dat je gol na desnoj strani terena - player2 strani
    public void player2Goal() {
        player1Score++;
    }

    public String currentTimeAsString() {
        double currentTime = System.currentTimeMillis() - timeStart;
        String time;
        int min = (int) (currentTime / 1000 / 60);
        int sec = (int) (currentTime / 1000 % 60);

        //formatiranje
        String minString;
        String secString;

        if (min < 10)
            minString = "0" + min;
        else
            minString = min + "";

        if (sec < 10)
            secString = "0" + sec;
        else
            secString = sec + "";

        time = minString + " : " + secString;

        return time;
    }

    public boolean isCurrentPlayerTurnComputer() {
        return currentPlayerTurnComputer;
    }
}
