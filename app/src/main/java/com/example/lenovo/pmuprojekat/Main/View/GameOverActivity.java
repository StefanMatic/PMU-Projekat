package com.example.lenovo.pmuprojekat.Main.View;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.Main.Main.StartActivity;
import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;
import com.example.lenovo.pmuprojekat.Main.Statistics.GameViewModel;
import com.example.lenovo.pmuprojekat.Main.Statistics.SingleGameInfo;
import com.example.lenovo.pmuprojekat.Main.Statistics.StatisticsSingleGameAdapter;
import com.example.lenovo.pmuprojekat.Main.Statistics.StatisticsWinsAdapter;
import com.example.lenovo.pmuprojekat.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GameOverActivity extends AppCompatActivity {
    public static final String PLAYER1_NAME = "player1Name";
    public static final String PLAYER2_NAME = "player2Name";
    public static final String PLAYER1_SCORE = "player1Score";
    public static final String PLAYER2_SCORE = "player2Score";
    public static final String GAME_DURATION = "gameDuration";

    private GameViewModel gameViewModel;
    private String player1, player2;
    private int player1Score, player2Score;
    private double gameDuration;

    private TextView player1TextView, player2TextView;
    private TextView player1ScoreTextView, player2ScoreTextView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_game_over);
        player1TextView = findViewById(R.id.gameOverPlayer1);
        player2TextView = findViewById(R.id.gameOverPlayer2);
        player1ScoreTextView = findViewById(R.id.gameOverScore1);
        player2ScoreTextView = findViewById(R.id.gameOverScore2);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        //Uzimanje podataka koji su prosledeni nakon zavrsetka igre
        Intent intent = this.getIntent();
        player1 = intent.getStringExtra(PLAYER1_NAME);
        player2 = intent.getStringExtra(PLAYER2_NAME);
        player1Score = intent.getIntExtra(PLAYER1_SCORE, -1);
        player2Score = intent.getIntExtra(PLAYER2_SCORE, -1);
        gameDuration = intent.getDoubleExtra(GAME_DURATION, -1);

        //Ukoliko udem iz igrice onda je neophodno da sacuvam novu pobedu
        saveToDataBase();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSingleGameStatistics);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // specify an adapter (see also next example)
        final StatisticsSingleGameAdapter statisticsSingleGameAdapter = new StatisticsSingleGameAdapter();
        recyclerView.setAdapter(statisticsSingleGameAdapter);


        gameViewModel.getMatches(player1, player2).observe(this, new Observer<List<SingleGame>>() {
            @Override
            public void onChanged(List<SingleGame> singleGame) {
                statisticsSingleGameAdapter.setAllSingleGamse(singleGame);
            }
        });

        //postavljanje vrednosti ukupne statistike
        player1TextView.setText(player1);
        player2TextView.setText(player2);

        gameViewModel.getWins(player1, player2).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                player1ScoreTextView.setText(Integer.toString(integer));
            }
        });

        gameViewModel.getWins(player2, player1).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                player2ScoreTextView.setText(Integer.toString(integer));
            }
        });

    }

    private void saveToDataBase() {
        if (player1Score == -1 || player2Score == -1 || gameDuration == -1)
            return;

        SingleGame singleGame = new SingleGame(gameDuration,
                player1,
                player2,
                player1Score,
                player2Score);

        gameViewModel.insert(singleGame);
    }

    public void deletePair(View view) {
        gameViewModel.deletePlayerPair(player1, player2);
        Toast.makeText(this, "Deleted player matches", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
