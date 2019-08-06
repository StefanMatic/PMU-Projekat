package com.example.lenovo.pmuprojekat.Main.Objects;

public class Vector2D {
    private float x;
    private float y;

    public Vector2D()
    {
        this.setX(0);
        this.setY(0);
    }

    public Vector2D(float x, float y)
    {
        this.setX(x);
        this.setY(y);
    }

    public Vector2D(Vector2D original)
    {
        this.setX(original.getX());
        this.setY(original.getY());
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void set(float x, float y)
    {
        this.setX(x);
        this.setY(y);
    }


    public float dot(Vector2D v2)
    {
        float result = 0.0f;
        result = this.getX() * v2.getX() + this.getY() * v2.getY();
        return result;
    }

    public float getLength()
    {
        return (float)Math.sqrt(getX()*getX() + getY()*getY());
    }

    public float getDistance(Vector2D v2)
    {
        return (float) Math.sqrt((v2.getX() - getX()) * (v2.getX() - getX()) + (v2.getY() - getY()) * (v2.getY() - getY()));
    }

    public double getDistance(double vx, double vy) {
        vx -= x;
        vy -= y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public Vector2D add(Vector2D v2)
    {
        Vector2D result = new Vector2D();
        result.setX(getX() + v2.getX());
        result.setY(getY() + v2.getY());
        return result;
    }

    public Vector2D subtract(Vector2D v2)
    {
        Vector2D result = new Vector2D();
        result.setX(this.getX() - v2.getX());
        result.setY(this.getY() - v2.getY());
        return result;
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public static double dot(Vector2D v1, Vector2D v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public Vector2D multiply(float scaleFactor)
    {
        Vector2D result = new Vector2D();
        result.setX(this.getX() * scaleFactor);
        result.setY(this.getY() * scaleFactor);
        return result;
    }

    public Vector2D divide(float scaleFactor)
    {
        Vector2D result = new Vector2D();
        result.setX(this.getX() / scaleFactor);
        result.setY(this.getY() / scaleFactor);
        return result;
    }

    public Vector2D normalize()
    {
        Vector2D result = new Vector2D();
        float len = getLength();
        if (len != 0.0f)
        {
            result.setX(this.getX() / len);
            result.setY(this.getY() / len);
        }
        else
        {
            result.setX(0.0f);
            result.setY(0.0f);
        }

        return result;
    }

    public String toString()
    {
        return "X: " + getX() + " Y: " + getY();
    }
}
