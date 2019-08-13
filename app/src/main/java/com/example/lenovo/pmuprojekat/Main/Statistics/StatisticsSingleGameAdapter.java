package com.example.lenovo.pmuprojekat.Main.Statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.pmuprojekat.Main.Statistics.Entities.SingleGame;
import com.example.lenovo.pmuprojekat.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticsSingleGameAdapter extends RecyclerView.Adapter<StatisticsSingleGameAdapter.MyViewHolder> {
    List<SingleGame> allSingleGamse = new ArrayList<>();

    public void setAllSingleGamse(List<SingleGame> allSingleGamse) {
        this.allSingleGamse = allSingleGamse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_statistic_players_item, parent, false);
        return new StatisticsSingleGameAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SingleGame singleGameInfo = allSingleGamse.get(position);

        holder.player1.setText(singleGameInfo.getPlayer1());
        holder.player2.setText(singleGameInfo.getPlayer2());
        holder.wins1.setText(Integer.toString(singleGameInfo.getScore1()));
        holder.wins2.setText(Integer.toString(singleGameInfo.getScore2()));
        holder.time.setText(currentTimeAsString(singleGameInfo.getTime()));
    }

    public String currentTimeAsString(double time) {
        int min = (int) (time / 1000 / 60);
        int sec = (int) (time / 1000 % 60);

        //formatiranje
        String minString;
        String secString;

        if (min < 10)
            minString = "0" + min;
        else
            minString = min + "";

        if (sec < 10)
            secString = "0" + sec;
        else
            secString = sec + "";

        return minString + " : " + secString;
    }

    @Override
    public int getItemCount() {
        return allSingleGamse.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView player1, player2;
        private TextView wins1, wins2;
        private TextView time;
        private MyViewHolder(View v) {
            super(v);

            player1 = v.findViewById(R.id.player1Item);
            player2 = v.findViewById(R.id.player2Item);
            wins1 = v.findViewById(R.id.player1WinsItem);
            wins2 = v.findViewById(R.id.player2WinsItem);
            time = v.findViewById(R.id.gameTime);

        }
    }
}
