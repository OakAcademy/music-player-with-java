package com.ongel.mp3playertest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private final ArrayList<String> songsList;
    private final Context mContext;

    public MusicAdapter(ArrayList<String> songsList, Context mContext) {
        this.songsList = songsList;
        this.mContext = mContext;
    }

    public static class MusicViewHolder extends RecyclerView.ViewHolder {


        private final TextView textViewMusicName;
        private final CardView cardView;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMusicName = itemView.findViewById(R.id.textViewMusicName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_music,parent,false);

        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, final int position) {

        String path = songsList.get(position);
        String title = path.substring(path.lastIndexOf("/")+1);

        holder.textViewMusicName.setText(title);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MusicActivity.class);
                intent.putExtra("path",path);
                intent.putExtra("title",title);
                intent.putExtra("position",holder.getAbsoluteAdapterPosition());
                intent.putExtra("musics", songsList);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }
}
