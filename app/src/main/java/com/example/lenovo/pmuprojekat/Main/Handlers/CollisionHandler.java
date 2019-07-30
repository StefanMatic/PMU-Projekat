package com.example.lenovo.pmuprojekat.Main.Handlers;

public interface CollisionHandler {
    //Fale argumenti metodama, treba da se ubaci Ball
    boolean checkCollision();
    void resolveCollision();
}
