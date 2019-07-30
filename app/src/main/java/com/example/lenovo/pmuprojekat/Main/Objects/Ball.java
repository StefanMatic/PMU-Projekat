package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Handlers.CollisionHandler;
import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

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

        velocity = new Vector2D(0,0);
        paint = new Paint();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, this.position.getX()-radius,this.position.getY()-radius, paint);
    }

    public void updatePosition(){
        position.setX(position.getX() + velocity.getX());
        position.setY(position.getY() + velocity.getY());

        updateWallCollisioin();
        applyFriction();
    }

    private void updateWallCollisioin(){
        //Provera da li udara u levi ili desni zid
        if(position.getX() - radius < 0 )  {
            position.setX(radius);
            velocity.setX(-velocity.getX());
        } else if(position.getX() + radius > AppConstants.SCREEN_WIDTH)  {
            position.setX(AppConstants.SCREEN_WIDTH - radius);
            velocity.setX(-velocity.getX());
        }

        //Provera da li udara gornji ili donji zid
        if(position.getY() - radius < 0 )  {
            position.setY(radius);
            velocity.setY(-velocity.getY());
        } else if(position.getY() + radius > AppConstants.SCREEN_HEIGHT)  {
            position.setY(AppConstants.SCREEN_HEIGHT - radius);
            velocity.setY(-velocity.getY());
        }
    }

    private void applyFriction(){
        velocity = velocity.multiply(AppConstants.FRICTION);
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
