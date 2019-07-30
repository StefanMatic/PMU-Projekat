package com.example.lenovo.pmuprojekat.Main.View;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //sets the activity view as GameView class
        SurfaceView view = new GameView(this);

        setContentView(view);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            {
                OnActionDown(event);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                OnActionUp(event);
                break;
            }
            default:break;
        }
        return false;
    }

    private void OnActionUp(MotionEvent event) {

    }

    private void OnActionDown(MotionEvent event) {

    }
}
