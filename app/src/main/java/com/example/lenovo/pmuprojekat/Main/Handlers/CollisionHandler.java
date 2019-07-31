package com.example.lenovo.pmuprojekat.Main.Handlers;

import com.example.lenovo.pmuprojekat.Main.Objects.Ball;

public interface CollisionHandler {
    //Fale argumenti metodama, treba da se ubaci Ball
    boolean checkBallCollision(Ball ball);
    void resolveBallCollision(Ball ball);
}
