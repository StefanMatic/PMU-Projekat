package com.example.lenovo.pmuprojekat.Main.Statistics;

import android.app.Application;

import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GameViewModel extends AndroidViewModel {
    private GameRepository gameRepository;
    private LiveData<List<SingleGameInfo>> allGames;

    public GameViewModel(@NonNull Application application) {
        super(application);

        gameRepository =  new GameRepository(application);
        allGames = gameRepository.getAllGames();
    }

    public void insert(SingleGame matchInfo) {
        gameRepository.insert(matchInfo);
    }

    public LiveData<Integer> getWins(String player, String opponent) {
        return gameRepository.getWins(player, opponent);
    }

    public LiveData<List<SingleGameInfo>> getAllGames() {
        return gameRepository.getAllGames();
    }

    public LiveData<List<SingleGame>> getMatches(String player1, String player2) {
        return gameRepository.getMatches(player1, player2);
    }

    public void deleteAll() {
        gameRepository.deleteAll();
    }

    public void deletePlayerPair(String player1, String player2) {
        gameRepository.deletePlayerPair(player1, player2);
    }
}
