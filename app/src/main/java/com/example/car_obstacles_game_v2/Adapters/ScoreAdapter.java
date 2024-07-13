package com.example.car_obstacles_game_v2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_obstacles_game_v2.Interfaces.ZoomCallback;
import com.example.car_obstacles_game_v2.Models.ScoreItem;
import com.example.car_obstacles_game_v2.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private final ArrayList<ScoreItem> scores;
    private ZoomCallback zoomCallback;



    public ScoreAdapter(ArrayList<ScoreItem> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_score_item,parent,false);

        return new ScoreViewHolder(view);
    }

    public ScoreAdapter setZoomCallback(ZoomCallback zoomCallback) {
        this.zoomCallback = zoomCallback;
        return this;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreItem score = getItem(position);
        holder.card_score_text.setText(String.valueOf(score.getScore()));
        holder.score_CARD_data.setOnClickListener(v -> zoomCallback.listItemClicked(score.getLat(),score.getLon()));
    }

    @Override
    public int getItemCount() {
        return scores == null ? 0 : Math.min(scores.size(),10);
    }

    private ScoreItem getItem(int position){
        return scores.get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder{
        private final CardView score_CARD_data;
        private final MaterialTextView card_score_text;
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            score_CARD_data = itemView.findViewById(R.id.score_CARD_data);
            card_score_text = itemView.findViewById(R.id.card_score_text);
        }
    }

}
