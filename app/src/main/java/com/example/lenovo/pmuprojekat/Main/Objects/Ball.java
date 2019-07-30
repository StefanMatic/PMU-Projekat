package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Handlers.CollisionHandler;

public abstract class Ball implements CollisionHandler {
    protected Vector2D position;
    protected Vector2D velocity;
    protected float mass;
    protected float radius;
    protected Bitmap image;
    protected Paint paint;


    public Ball(Vector2D position, float mass, float radius, Bitmap img) {
        this.position = position;
        this.mass = mass;
        this.radius = radius;
        this.image = img;

        velocity = null;
        paint = new Paint();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, this.position.getX()-radius,this.position.getY()-radius, paint);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
