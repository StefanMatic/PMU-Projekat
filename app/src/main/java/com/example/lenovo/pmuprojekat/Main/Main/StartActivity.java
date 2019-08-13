package com.example.lenovo.pmuprojekat.Main.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lenovo.pmuprojekat.Main.GameStats.GameStatus;
import com.example.lenovo.pmuprojekat.Main.SavedGame.SaveGame;
import com.example.lenovo.pmuprojekat.Main.View.ContinueGameActivity;
import com.example.lenovo.pmuprojekat.Main.View.GameActivity;
import com.example.lenovo.pmuprojekat.Main.View.GameOverActivity;
import com.example.lenovo.pmuprojekat.Main.View.NewGameActivity;
import com.example.lenovo.pmuprojekat.Main.View.SettingsActivity;
import com.example.lenovo.pmuprojekat.Main.View.SettingsFieldSelection;
import com.example.lenovo.pmuprojekat.Main.View.StatisticsActivity;
import com.example.lenovo.pmuprojekat.R;
import com.google.gson.Gson;

public class StartActivity extends Activity {
    private Button newGameButton, continueButton, statisticsButton, settingsButton;
    private Drawable backgroundSelectable, backgroundNotSelectable;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_start);

        AppConstants.initialize(this);

        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        statisticsButton = findViewById(R.id.statisticsButton);
        settingsButton = findViewById(R.id.settingsButton);

        Bitmap buttonBackgroundImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_background);
        buttonBackgroundImage = AppConstants.getBitmapBank().resizePicture(buttonBackgroundImage,
                (int) (AppConstants.SCREEN_WIDTH * 0.22),
                (int) (AppConstants.SCREEN_HEIGHT * 0.1));
        backgroundSelectable = new BitmapDrawable(this.getResources(), buttonBackgroundImage);

        newGameButton.setBackground(backgroundSelectable);
        statisticsButton.setBackground(backgroundSelectable);
        settingsButton.setBackground(backgroundSelectable);

        checkIfGameSaved();
    }

    //pokretanje nove igre
    public void startNewGame(View view) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        AppConstants.resetForNewGame();

        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }


    //Otvaranje prozora za podesavanje
    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfGameSaved();

        if (AppConstants.isGameOver()){

            AppConstants.getGameEngine().getGameView().stopThreadForever();

            AppConstants.setGameOver(false);
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra(GameOverActivity.PLAYER1_NAME, AppConstants.getPlayer1Name());
            intent.putExtra(GameOverActivity.PLAYER2_NAME, AppConstants.getPlayer2Name());
            intent.putExtra(GameOverActivity.PLAYER1_SCORE, AppConstants.getPlayer1Score());
            intent.putExtra(GameOverActivity.PLAYER2_SCORE, AppConstants.getPlayer2Score());
            intent.putExtra(GameOverActivity.GAME_DURATION, AppConstants.getGameDuration());
            startActivity(intent);
        }
    }

    @SuppressLint("NewApi")
    public void checkIfGameSaved() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        Boolean saved = sharedPref.getBoolean(getString(R.string.is_game_saved), false);

        if (continueButton == null) return;

        if (saved) {
            continueButton.setClickable(true);
            continueButton.setEnabled(true);
            continueButton.setBackground(backgroundSelectable);
            AppConstants.setGamePaused(true);
        } else {
            //ukoliko nije sacuvana igrica treba da damo korisniku do znanja da ne moze da pritisne dugme
            Bitmap buttonBackgroundImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_background_not_select);
            buttonBackgroundImage = AppConstants.getBitmapBank().resizePicture(buttonBackgroundImage,
                    (int) (AppConstants.SCREEN_WIDTH * 0.22),
                    (int) (AppConstants.SCREEN_HEIGHT * 0.1));
            backgroundNotSelectable = new BitmapDrawable(this.getResources(), buttonBackgroundImage);

            continueButton.setClickable(false);
            continueButton.setEnabled(false);
            continueButton.setBackground(backgroundNotSelectable);

            AppConstants.setGamePaused(false);
        }
    }

    public void continueGame(View view) {
        SharedPreferences sharedPrefCheck = getSharedPreferences(getString(R.string.saved_game_filename), MODE_PRIVATE);
        Boolean saved = sharedPrefCheck.getBoolean(getString(R.string.is_game_saved), false);
        if (!saved) return;

        Intent intent = new Intent(this, ContinueGameActivity.class);
        startActivity(intent);
    }

    public void seeStatistics(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void proba(View view) {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }
}
