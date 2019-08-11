package com.example.lenovo.pmuprojekat.Main.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Main.StartActivity;
import com.example.lenovo.pmuprojekat.Main.SavedGame.SaveGame;
import com.example.lenovo.pmuprojekat.R;
import com.google.gson.Gson;

public class GameActivity extends AppCompatActivity {
    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //sets the activity view as GameView class
        view = new GameView(this);

        setContentView(view);
        AppConstants.setMyGameContext(this);
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

    private void OnActionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        AppConstants.getGameEngine().checkIfSelected(x,y);
    }


    @Override
    protected void onPause() {
        if (!AppConstants.isGameOver()){
            saveGame();
            view.stopThread();

            finish();
        }

        super.onPause();
    }

    private void saveGame(){
        SaveGame saveGame = AppConstants.getGameEngine().saveGame();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saveGame);
        editor.putString(getString(R.string.saved_game_key), json);

        //provaera da li je sve proslo kako treba
        editor.putBoolean(getString(R.string.is_game_saved), true);
        editor.apply();

        AppConstants.setGamePaused(true);
    }

    private void OnActionUp(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        AppConstants.getGameEngine().makeMove(x,y);
    }
}
