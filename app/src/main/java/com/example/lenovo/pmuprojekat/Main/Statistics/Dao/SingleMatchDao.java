package com.example.lenovo.pmuprojekat.Main.Statistics.Dao;

import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;
import com.example.lenovo.pmuprojekat.Main.Statistics.SingleGameInfo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SingleMatchDao {

    @Insert
    void insert(SingleGame matchInfo);

    @Query("SELECT COUNT(*) FROM match_info WHERE ((player1 = :player AND player2 = :opponent AND score1 > score2) OR (player1 = :opponent AND player2 = :player AND score2 > score1))")
    LiveData<Integer> getWins(String player, String opponent);

    @Query("SELECT  *, (SELECT COUNT(*) FROM match_info WHERE (player1 = A.player1 AND player2 = A.player2 AND score1 > score2) OR (player1 = A.player2 AND player2 = A.player1 AND score2 > score1)) as score1, (SELECT COUNT(*) FROM match_info WHERE (player1 = A.player1 AND player2 = A.player2 AND score2 > score1) OR (player1 = A.player2 AND player2 = A.player1 AND score1 > score2)) AS score2 FROM (SELECT DISTINCT CASE WHEN player1 > player2 THEN player1 ELSE player2 END as player1, CASE WHEN player1 > player2 THEN player2 ELSE player1 END AS player2 FROM match_info) AS A")
    LiveData<List<SingleGameInfo>> getAllSingleGameInfo();

    @Query("SELECT * FROM match_info WHERE (player1 = :player1 AND player2 = :player2) OR (player1 = :player2 AND player2 = :player1)")
    LiveData<List<SingleGame>> getMatches(String player1, String player2);

    @Query("DELETE FROM match_info")
    void deleteAll();

    @Query("DELETE FROM match_info WHERE (player1 = :player1 AND player2 = :player2) OR (player1 = :player2 AND player2 = :player1)")
    void deletePlayerPair(String player1, String player2);

}
