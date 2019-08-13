package com.example.lenovo.pmuprojekat.Main.Statistics;

import android.app.Application;
import android.os.AsyncTask;

import com.example.lenovo.pmuprojekat.Main.Statistics.Dao.SingleMatchDao;
import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GameRepository {
    private SingleMatchDao singleMatchDao;
    private LiveData<List<SingleGameInfo>> allGames;

    public GameRepository(Application application) {
        GameDatabase database = GameDatabase.getInstance(application);
        singleMatchDao = database.singleMatchDao();
        allGames = singleMatchDao.getAllSingleGameInfo();
    }

    public void insert(SingleGame matchInfo) {
        new InsertMatchAsyncTask(singleMatchDao).execute(matchInfo);
    }

    public LiveData<Integer> getWins(String player, String opponent) {
        return singleMatchDao.getWins(player, opponent);
    }

    public LiveData<List<SingleGameInfo>> getAllGames() {
        return allGames;
    }

    public LiveData<List<SingleGame>> getMatches(String player1, String player2) {
        return singleMatchDao.getMatches(player1, player2);
    }

    public void deleteAll() {
        new DeleteAllMatchAsyncTask(singleMatchDao).execute();
    }

    public void deletePlayerPair(String player1, String player2) {
        new DeletePlayerPairMatchAsyncTask(singleMatchDao).execute(player1, player2);
    }


    private static class InsertMatchAsyncTask extends AsyncTask<SingleGame, Void, Void> {
        private SingleMatchDao singleMatchDao;

        private InsertMatchAsyncTask(SingleMatchDao singleMatchDao) {
            this.singleMatchDao = singleMatchDao;
        }

        @Override
        protected Void doInBackground(SingleGame... matchInfos) {
            singleMatchDao.insert(matchInfos[0]);
            return null;
        }
    }

    private static class DeleteAllMatchAsyncTask extends AsyncTask<Void, Void, Void> {
        private SingleMatchDao singleMatchDao;

        private DeleteAllMatchAsyncTask(SingleMatchDao matchInfoDao) {
            this.singleMatchDao = matchInfoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            singleMatchDao.deleteAll();
            return null;
        }
    }

    private static class DeletePlayerPairMatchAsyncTask extends AsyncTask<String, Void, Void> {
        private SingleMatchDao singleMatchDao;

        private DeletePlayerPairMatchAsyncTask(SingleMatchDao singleMatchDao) {
            this.singleMatchDao = singleMatchDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            singleMatchDao.deletePlayerPair(strings[0], strings[1]);
            return null;
        }
    }
}
