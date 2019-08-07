package com.example.lenovo.pmuprojekat.Main.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.View.GameActivity;
import com.example.lenovo.pmuprojekat.R;

public class StartActivity extends Activity {
    public static final int NEW_GAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_start);
    }

    public void startNewGame(View view) {
        AppConstants.initialize(this);

        Intent intent = new Intent(this, GameActivity.class);
        startActivityForResult(intent,NEW_GAME);
    }
}
