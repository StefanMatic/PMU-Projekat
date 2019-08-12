package com.example.lenovo.pmuprojekat.Main.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.lenovo.pmuprojekat.Main.GameStats.GameStatus;
import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Main.StartActivity;
import com.example.lenovo.pmuprojekat.Main.SavedGame.SaveGame;
import com.example.lenovo.pmuprojekat.R;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

public class ContinueGameActivity extends AppCompatActivity {
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

        SharedPreferences sharedPrefGame = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefGame.getString(getString(R.string.saved_game_key), "");
        SaveGame saveGame = gson.fromJson(json, SaveGame.class);

        SharedPreferences.Editor editor = sharedPrefGame.edit();
        editor.clear();
        editor.apply();

        //postavljanje svih konstanta
        setAppConstantsCurrentValues(saveGame);
        GameStatus newGameStatus = setUpGameStats(saveGame);

        AppConstants.getGameEngine().setGameStats(newGameStatus);
        AppConstants.getGameEngine().initAudio(this);
        AppConstants.getGameEngine().initGoal();
        AppConstants.getGameEngine().setAllObjectsOnField(newGameStatus.getAllBalls());

        AppConstants.setMyGameContext(this);

        setContentView(view);
    }

    //postavljanje konstanti koje su neophodne za igru
    private void setAppConstantsCurrentValues(SaveGame saveGame) {
        //postavljanje neophodnih slika
        AppConstants.getBitmapBank().setFieldID(saveGame.getFieldID());
        AppConstants.getBitmapBank().setPlayer1FlagID(saveGame.getPlayer1().getFlagID());
        AppConstants.getBitmapBank().setPlayer2FlagID(saveGame.getPlayer2().getFlagID());

        AppConstants.getBitmapBank().setPlayer1FlagByID(saveGame.getPlayer1().getFlagID());
        AppConstants.getBitmapBank().setPlayer2FlagByID(saveGame.getPlayer2().getFlagID());
        AppConstants.getBitmapBank().setNewFieldByID(saveGame.getFieldID());

        AppConstants.setCurrentSpeed(saveGame.getCurrentSpeed());
        AppConstants.setCurrentTime(saveGame.getCurrentTime());
        AppConstants.setCurrentGoals(saveGame.getCurrentGoals());
        AppConstants.setPlayOnGoals(saveGame.getIsPlayOnGoals());
    }

    //Postavljanje svih neophodnih podataka za klasu GameStatus
    private GameStatus setUpGameStats(SaveGame saveGame){

        GameStatus gameStatus = new GameStatus(
                saveGame.getPlayer1().getName(),
                saveGame.getPlayer2().getName(),
                AppConstants.getBitmapBank().getFiled(),
                AppConstants.getBitmapBank().getPlayer1Flag(),
                AppConstants.getBitmapBank().getPlayer2Flag(),
                saveGame.getPlayer1().isComputer(),
                saveGame.getPlayer2().isComputer()
        );

        gameStatus.setPlayer1Score(saveGame.getPlayer1().getScore());
        gameStatus.setPlayer2Score(saveGame.getPlayer2().getScore());
        gameStatus.setPlayer1Turn(saveGame.getPlayer1().isMyTurn());
        gameStatus.setPlayer2Turn(saveGame.getPlayer2().isMyTurn());
        gameStatus.setAllBalls(saveGame.makeAllBalls());
        gameStatus.setTimeStart(saveGame.getTimeStart());
        gameStatus.setTimeElapsed(saveGame.getTimeElapsed());
        if (gameStatus.isPlayer1Turn()){
            gameStatus.setCurrentPlayerTurnComputer(saveGame.getPlayer1().isComputer());
        }
        else{
            gameStatus.setCurrentPlayerTurnComputer(saveGame.getPlayer2().isComputer());
        }

        return gameStatus;
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
        AppConstants.setGamePaused(true);
        SaveGame saveGame = AppConstants.getGameEngine().saveGame();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saveGame);
        editor.putString(getString(R.string.saved_game_key), json);

        //provaera da li je sve proslo kako treba
        editor.putBoolean(getString(R.string.is_game_saved), true);
        editor.apply();
    }

    private void OnActionUp(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        AppConstants.getGameEngine().makeMove(x,y);
    }
}
