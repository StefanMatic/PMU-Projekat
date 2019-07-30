package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;

public class SoccerBall extends Ball {
    public SoccerBall(Vector2D position, float mass, float radius, Bitmap img) {
        super(position, mass, radius, img);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    @Override
    public void resolveCollision() {

    }
}
