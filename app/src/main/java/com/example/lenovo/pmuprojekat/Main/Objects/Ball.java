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

    @Override
    public boolean checkBallCollision(Ball ball) {
        float distance = position.getDistance(ball.getPosition());
        if (distance <= radius + ball.getRadius())
            return true;

        return false;
    }

    @Override
    public void resolveBallCollision(Ball ball) {
        //prvi korak je da nademo jedinicne vektore normale i tangente
        Vector2D normalVector = ball.position.subtract(position);
        Vector2D unitNormalVector = normalVector.normalize();

        Vector2D unitTangentVector = new Vector2D(-unitNormalVector.getY(), unitNormalVector.getX());

        //Drugi korak je da izracunamo skalarne vrednosti velocity
        float normalVelocity1 = unitNormalVector.dot(velocity);
        float tangentialVelocity1 = unitTangentVector.dot(velocity);

        float normalVelocity2 = unitNormalVector.dot(ball.velocity);
        float tangentialVelocity2 = unitTangentVector.dot(ball.velocity);

        //Treci korak je izracunavanje novih tangecialnih vektora
        float newTangentialVelocity1 = tangentialVelocity1;
        float newTangentialVelocity2 = tangentialVelocity2;

        //Cetvrti korak je da se nadu nove vektore noramle
        float newNormalVelocity1  = (normalVelocity1*(mass - ball.getMass()) + 2*ball.getMass()*normalVelocity2)/(mass + ball.getMass());
        float newNormalVelocity2  = (normalVelocity2*(ball.getMass() - mass) + 2*mass*normalVelocity1)/(mass + ball.getMass());

        //peti korak je da vratimo novopronadene vektore u vektorski oblik
        Vector2D newNormalVelocity1Vector = unitNormalVector.multiply(newNormalVelocity1);
        Vector2D newTangetVelocity1Vector = unitTangentVector.multiply(newTangentialVelocity1);

        Vector2D newNormalVelocity2Vector = unitNormalVector.multiply(newNormalVelocity2);
        Vector2D newTangetVelocity2Vector = unitTangentVector.multiply(newTangentialVelocity2);

        //sesti korak je da se opet sastave prethodno izracunate velocity-s i postavljanje tih vrednosti
        velocity = newNormalVelocity1Vector.add(newTangetVelocity1Vector);
        ball.setVelocity(newNormalVelocity2Vector.add(newTangetVelocity2Vector));

        // get the mtd
        Vector2D delta = (position.subtract(ball.position));
        float d = delta.getLength();
        // minimum translation distance to push balls apart after intersecting
        Vector2D mtdVector = delta.multiply(((getRadius() + ball.getRadius())-d)/d);
        // impact speed
        Vector2D v = (this.velocity.subtract(ball.velocity));
        float vn = v.dot(mtdVector.normalize());

        // sphere intersecting but moving away from each other already
        if (vn > 0.0f) return;

        double mtd = calculateSeparationDistance(ball);
        if (mtd > 0){
            System.out.println("*******************");
            System.out.println(mtd);
            position = position.add(new Vector2D((float)mtd*velocity.getX(), (float)mtd*velocity.getY()));
        }
    }

    private double calculateSeparationDistance(Ball ball){
        double distance;

        float a = position.getX()-ball.getPosition().getX();
        float b = velocity.getX();
        float c = position.getY()-ball.getPosition().getY();
        float d = velocity.getY();
        float e = (radius + ball.getRadius()) * (radius + ball.getRadius());

        distance = (Math.sqrt(((d*d)+(b*b))*e-(a*a)*(d*d)+2*a*b*c*d-(b*b)*(c*c))-c*d-a*b)/((d*d)+(b*b));

        return distance;
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
