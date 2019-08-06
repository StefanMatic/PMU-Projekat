package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Handlers.CollisionHandler;
import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

import java.util.ArrayList;

public class Goal implements CollisionHandler {
    private Vector2D start, end;
    private float radius;
    private float width, height;
    private ArrayList<Goal> goalposts = new ArrayList<>();
    private Ball post;
    private ArrayList<Vector2D> collisionPoints = new ArrayList<>();

    public Goal() {
    }

    public Goal(Vector2D start, Vector2D end, float radius) {
        this.start = start;
        this.end = end;
        this.radius = radius / 2;

        width = calculateX(48, 800);
        height = radius;
        if (this.start.getX() == 0)
            post = new SoccerBall(new Vector2D(end.getX(), end.getY()), AppConstants.GOAL_POST_MASS, (float) (radius * 1.7), null);
        else
            post = new SoccerBall(new Vector2D(start.getX(), start.getY()), AppConstants.GOAL_POST_MASS, (float) (radius * 1.7), null);
    }

    public Vector2D getStart() {
        return start;
    }

    public void setStart(Vector2D start) {
        this.start = start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public void setEnd(Vector2D end) {
        this.end = end;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public ArrayList<Goal> getGoalposts() {
        return goalposts;
    }

    public Ball getPost() {
        return post;
    }

    public void setPost(Ball post) {
        this.post = post;
    }

    public void setGoalposts() {
        goalposts.add(new Goal(new Vector2D(0, calculateY(173, 480)), new Vector2D(calculateX(44, 800), calculateY(173, 480)), calculateY(AppConstants.GOAL_POST_WIDTH, 480)));
        goalposts.add(new Goal(new Vector2D(0, calculateY(307, 480)), new Vector2D(calculateX(44, 800), calculateY(307, 480)), calculateY(AppConstants.GOAL_POST_WIDTH, 480)));
        goalposts.add(new Goal(new Vector2D(calculateX(756, 800), calculateY(173, 480)), new Vector2D(AppConstants.SCREEN_WIDTH, calculateY(173, 480)), calculateY(AppConstants.GOAL_POST_WIDTH, 480)));
        goalposts.add(new Goal(new Vector2D(calculateX(756, 800), calculateY(307, 480)), new Vector2D(AppConstants.SCREEN_WIDTH, calculateY(307, 480)), calculateY(AppConstants.GOAL_POST_WIDTH, 480)));
    }

    public void draw(Canvas canvas) {
        Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setColor(Color.RED);
        for (Goal gp : goalposts) {
            canvas.drawLine(gp.start.getX(), gp.start.getY(), gp.end.getX(), gp.end.getY(), myPaint);
            canvas.drawCircle(gp.getPost().getPosition().getX(), gp.getPost().getPosition().getY(), gp.getPost().getRadius(), myPaint);
        }
        myPaint.setStrokeWidth(5);
        for (Vector2D point : collisionPoints) {
            canvas.drawPoint(point.getX(), point.getY(), myPaint);
        }
    }

    //metode koje sluze da urade skaliranje pozicije golova u odnosu na ekran uredaja po X osi
    private float calculateX(double x, double max) {
        return (float) (AppConstants.SCREEN_WIDTH * x / max);
    }

    //metode koje sluze da urade skaliranje pozicije golova u odnosu na ekran uredaja po y osi
    private float calculateY(double y, double max) {
        return (float) (AppConstants.SCREEN_HEIGHT * y / max);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public boolean checkBallCollision(Ball ball) {
        // if (checkBallCollisionLeft(ball) || checkBallCollisionRight(ball))
        //   return true;

        return false;
    }

    private boolean checkBallCollision(Ball ball, Goal gp) {
        if (minimumDistance(gp.getStart(), gp.getEnd(), ball.getPosition()) <= ball.getRadius() + gp.getRadius())
            return true;

        return false;


        /*
        Vector2D goalSideHelper;
        Vector2D goalSideHalf;

        for (int i = 0; i < 2; i++) {
            Goal gp = goalposts.get(i);
            goalSideHalf = new Vector2D(width / 2, height / 2);
            goalSideHelper = new Vector2D(gp.end.getX() + goalSideHalf.getX(), gp.end.getY() + goalSideHalf.getY());

            Vector2D difference = ball.getPosition().subtract(goalSideHelper);
            Vector2D clamped = new Vector2D(clamp(difference.getX(), -goalSideHalf.getX(), goalSideHalf.getX()), clamp(difference.getY(), -goalSideHalf.getY(), goalSideHalf.getY()));

            Vector2D closestPoint = goalSideHelper.add(clamped);
            float distance = closestPoint.getDistance(ball.getPosition());

            if (distance <= ball.getRadius())
                return true;
        }
        return false;
        */
    }

    private boolean checkBallCollisionRight(Ball ball) {
        Vector2D goalSideHelper;
        Vector2D goalSideHalf;

        for (int i = 2; i < 4; i++) {
            Goal gp = goalposts.get(i);
            goalSideHalf = new Vector2D(width / 2, height / 2);
            goalSideHelper = new Vector2D(gp.start.getX() + goalSideHalf.getX(), gp.start.getY() + goalSideHalf.getY());

            Vector2D difference = ball.getPosition().subtract(goalSideHelper);
            Vector2D clamped = new Vector2D(clamp(difference.getX(), -goalSideHalf.getX(), goalSideHalf.getX()), clamp(difference.getY(), -goalSideHalf.getY(), goalSideHalf.getY()));

            Vector2D closestPoint = goalSideHelper.add(clamped);
            float distance = closestPoint.getDistance(ball.getPosition());

            if (distance <= ball.getRadius())
                return true;
        }
        return false;
    }

    @Override
    public void resolveBallCollision(Ball ball) {
        for (int i = 0; i < 2; i++) {
            Goal gp = goalposts.get(i);
            if (checkBallCollision(ball, gp) && checkIfSmallEnoughDistance(ball, gp)) {
                collisionPoints.add(new Vector2D(ball.getPosition().getX(), ball.getPosition().getY()));
                ball.resetPositionOnce();
                if (ball.checkBallCollision(gp.getPost())) {
                    //ball-like collision
                    resolveGoalpostCollision(gp.getPost(), ball);
                    break;
                } else {
                    //wall-like collision
                    ball.getVelocity().setY(-ball.getVelocity().getY());
                    break;
                }
            }
        }
        for (int i = 2; i < 4; i++) {
            Goal gp = goalposts.get(i);
            if (checkBallCollision(ball, gp) && checkIfSmallEnoughDistance(ball, gp)) {
                collisionPoints.add(new Vector2D(ball.getPosition().getX(), ball.getPosition().getY()));
                ball.resetPositionOnce();

                if (ball.checkBallCollision(gp.getPost())) {
                    //ball-like collision
                    resolveGoalpostCollision(gp.getPost(), ball);
                } else {
                    //wall-like collision
                    ball.getVelocity().setY(-ball.getVelocity().getY());
                }
            }
        }
    }

    private void resolveGoalpostCollision(Ball post, Ball ball) {
        //prvi korak je da nademo jedinicne vektore normale i tangente
        Vector2D normalVector = ball.position.subtract(post.getPosition());
        Vector2D unitNormalVector = normalVector.normalize();

        Vector2D unitTangentVector = new Vector2D(-unitNormalVector.getY(), unitNormalVector.getX());

        //Drugi korak je da izracunamo skalarne vrednosti velocity
        float normalVelocity1 = unitNormalVector.dot(post.getVelocity());
        float tangentialVelocity1 = unitTangentVector.dot(post.getVelocity());

        float normalVelocity2 = unitNormalVector.dot(ball.velocity);
        float tangentialVelocity2 = unitTangentVector.dot(ball.velocity);

        //Treci korak je izracunavanje novih tangecialnih vektora
        float newTangentialVelocity1 = tangentialVelocity1;
        float newTangentialVelocity2 = tangentialVelocity2;

        //Cetvrti korak je da se nadu nove vektore noramle
        float newNormalVelocity1 = (normalVelocity1 * (post.getMass() - ball.getMass()) + 2 * ball.getMass() * normalVelocity2) / (post.getMass() + ball.getMass());
        float newNormalVelocity2 = (normalVelocity2 * (ball.getMass() - post.getMass()) + 2 * post.getMass() * normalVelocity1) / (post.getMass() + ball.getMass());

        //peti korak je da vratimo novopronadene vektore u vektorski oblik
        Vector2D newNormalVelocity1Vector = unitNormalVector.multiply(newNormalVelocity1);
        Vector2D newTangetVelocity1Vector = unitTangentVector.multiply(newTangentialVelocity1);

        Vector2D newNormalVelocity2Vector = unitNormalVector.multiply(newNormalVelocity2);
        Vector2D newTangetVelocity2Vector = unitTangentVector.multiply(newTangentialVelocity2);

        //sesti korak je da se opet sastave prethodno izracunate velocity-s i postavljanje tih vrednosti
        //velocity = newNormalVelocity1Vector.add(newTangetVelocity1Vector);
        ball.setVelocity(newNormalVelocity2Vector.add(newTangetVelocity2Vector));
    }

    /*
    public void resolveGoalpostCollision(Vector2D goalpost, Ball ball) {
        Vector2D newVelocity2 = Vector2D.subtract(ball.getPosition(), goalpost);
        newVelocity2 = newVelocity2.normalize();
        newVelocity2.multiply(ball.getVelocity().dot(newVelocity2));
        Vector2D newVelocity1 = Vector2D.subtract(ball.getVelocity(), newVelocity2);

        ball.setVelocity(Vector2D.subtract(newVelocity1, newVelocity2));
    }
    */

    public double minimumDistance(Vector2D s1, Vector2D s2, Vector2D p) {
        double length = Vector2D.subtract(s1, s2).getLength();
        double lengthSquared = length * length;
        double t = Math.max(0, Math.min(1, Vector2D.dot(Vector2D.subtract(p, s1), Vector2D.subtract(s2, s1)) / lengthSquared));
        Vector2D projection = Vector2D.subtract(s2, s1);
        projection = projection.multiply((float) t);
        projection = projection.add(s1);
        return projection.getDistance(p);
    }

    public boolean checkIfSmallEnoughDistance(Ball ball, Goal gp) {
        if (gp.getStart().getX() == 0) {
            if (gp.getEnd().getX() >= ball.getPosition().getX() - ball.getRadius())
                return true;
        } else {
            if (gp.getStart().getX() <= ball.getPosition().getX() + ball.getRadius())
                return true;
        }

        return false;
    }

}
