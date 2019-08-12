package com.example.lenovo.pmuprojekat.Main.Statistics.Entities;


import com.example.lenovo.pmuprojekat.Main.Statistics.Converter;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "match_info")
@TypeConverters({Converter.class})
public class SingleGame {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double time;
    private Date day;
    private String player1;
    private String player2;
    private int score1;
    private int score2;

    public SingleGame(double time, Date day, String player1, String player2, int score1, int score2) {
        this.time = time;
        this.day = day;
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getTime() {
        return time;
    }

    public Date getDay() {
        return day;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }
}
