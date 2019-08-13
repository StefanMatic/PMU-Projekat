package com.example.lenovo.pmuprojekat.Main.View;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lenovo.pmuprojekat.Main.Main.StartActivity;
import com.example.lenovo.pmuprojekat.Main.Statistics.GameViewModel;
import com.example.lenovo.pmuprojekat.Main.Statistics.SingleGameInfo;
import com.example.lenovo.pmuprojekat.Main.Statistics.StatisticsWinsAdapter;
import com.example.lenovo.pmuprojekat.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticsActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_statistics);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStatistics);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        final StatisticsWinsAdapter statisticsWinsAdapter = new StatisticsWinsAdapter();
        recyclerView.setAdapter(statisticsWinsAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(this, new Observer<List<SingleGameInfo>>() {
            @Override
            public void onChanged(List<SingleGameInfo> singleGameInfos) {
                statisticsWinsAdapter.setAllGamse(singleGameInfos);
            }
        });

        statisticsWinsAdapter.setOnItemClickListener(new StatisticsWinsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SingleGameInfo singleGameInfo) {
                Intent intent = new Intent(StatisticsActivity.this, GameOverActivity.class);
                intent.putExtra(GameOverActivity.PLAYER1_NAME, singleGameInfo.getPlayer1());
                intent.putExtra(GameOverActivity.PLAYER2_NAME, singleGameInfo.getPlayer2());

                startActivity(intent);
            }
        });
    }

    public void deleteAll(View view) {
        gameViewModel.deleteAll();
        Toast.makeText(this, "All scores deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
