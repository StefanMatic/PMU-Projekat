package com.example.lenovo.pmuprojekat.Main.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;

public class GameEngine {

    public GameEngine() {
    }

    public void initFiledAndPlayers(){

    }

    public void draw(Canvas canvas){
        drawBackground(canvas);
        drawPlayers(canvas);

        //drawGoals(canvas);
        //drawResult(canvas);
    }

    private void drawPlayers(Canvas canvas) {

    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getFiled(),0,0,new Paint());
    }
}
