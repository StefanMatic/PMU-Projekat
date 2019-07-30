package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;

public class Player extends Ball {
    private boolean selected;

    public Player(Vector2D position, float mass, float radius, Bitmap img) {
        super(position, mass, radius, img);

        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    @Override
    public void resolveCollision() {
        //za sada samo da radi
    }

    public boolean checkIfSelected(float x, float y) {
        if (Math.abs(x - position.getX()) < radius && Math.abs(y - position.getY()) < radius)
            return true;

        return false;
    }
}
