package com.example.lenovo.pmuprojekat.Main.Statistics;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lenovo.pmuprojekat.Main.Statistics.Dao.SingleMatchDao;
import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;
import com.example.lenovo.pmuprojekat.Main.View.ContinueGameActivity;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SingleGame.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {
    private static GameDatabase instance;

    public abstract SingleMatchDao singleMatchDao();

    public static synchronized GameDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GameDatabase.class,
                    "game_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SingleMatchDao singleMatchDao;

        private PopulateDbAsyncTask(GameDatabase db) {
            singleMatchDao = db.singleMatchDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            singleMatchDao.insert(new SingleGame(100, "Masa", "Luka", 1, 6));
            singleMatchDao.insert(new SingleGame(100, "Masa", "Luka", 5, 2));
            singleMatchDao.insert(new SingleGame(102, "Masa", "Stefan", 4, 4));
            singleMatchDao.insert(new SingleGame(201, "Masa", "Stefan", 2, 5));
            singleMatchDao.insert(new SingleGame(278, "Aleksa", "Stefan", 0, 0));
            singleMatchDao.insert(new SingleGame(298, "Stefan", "Aleksa", 2, 2));
            return null;
        }
    }

}
