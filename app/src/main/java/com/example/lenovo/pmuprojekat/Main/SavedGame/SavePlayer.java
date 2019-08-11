package com.example.lenovo.pmuprojekat.Main.SavedGame;

import android.graphics.Bitmap;

public class SavePlayer {
    private String name;
    private int flagID;
    private int score;
    private boolean isComputer, isMyTurn;

    public SavePlayer(String name, int flagID, int score, boolean isComputer, boolean isMyTurn) {
        this.name = name;
        this.flagID = flagID;
        this.score = score;
        this.isComputer = isComputer;
        this.isMyTurn = isMyTurn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlagID() {
        return flagID;
    }

    public void setFlagID(int flagID) {
        this.flagID = flagID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }
}
