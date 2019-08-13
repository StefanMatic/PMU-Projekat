package com.example.lenovo.pmuprojekat.Main.Statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.pmuprojekat.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticsWinsAdapter extends RecyclerView.Adapter<StatisticsWinsAdapter.MyViewHolder> {
    List<SingleGameInfo> allGamse = new ArrayList<>();
    OnItemClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_statistic_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SingleGameInfo singleGameInfo = allGamse.get(position);

        holder.player1.setText(singleGameInfo.getPlayer1());
        holder.player2.setText(singleGameInfo.getPlayer2());
        holder.wins1.setText(Integer.toString(singleGameInfo.getScore1()));
        holder.wins2.setText(Integer.toString(singleGameInfo.getScore2()));
    }

    @Override
    public int getItemCount() {
        return allGamse.size();
    }

    public void setAllGamse(List<SingleGameInfo> gamse) {
        allGamse = gamse;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView player1, player2;
        private TextView wins1, wins2;

        public MyViewHolder(View v) {
            super(v);
            player1 = v.findViewById(R.id.player1);
            player2 = v.findViewById(R.id.player2);
            wins1 = v.findViewById(R.id.player1Wins);
            wins2 = v.findViewById(R.id.player2Wins);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(allGamse.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(SingleGameInfo singleGameInfo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
