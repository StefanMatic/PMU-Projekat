package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

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
    public void draw(Canvas canvas) {
        if (selected)
            canvas.drawBitmap(AppConstants.getBitmapBank().getSelected(),
                    (float) (position.getX() - 1.5 * radius),
                    (float) (position.getY() - 1.5 * radius),
                    paint);

        super.draw(canvas);
    }

    public boolean checkIfSelected(float x, float y) {
        if (Math.abs(x - position.getX()) < radius && Math.abs(y - position.getY()) < radius)
            return true;

        return false;
    }
}
