package com.maxapp.dansu.astraplayer.song_browser_activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.folder_browser.MyFile;
import java.util.List;

public class FavSongAdapter extends RecyclerView.Adapter<FavSongAdapter.ViewHolder> {
    private List<MyFile> folders;
    private Context ctx;
    private FavSongListener mListener;

    FavSongAdapter(List<MyFile> directories, Context ctx, FavSongListener mListener){
        folders = directories;
        this.ctx = ctx;
        this.mListener = mListener;
    }

    public interface FavSongListener{
        void setSong(MyFile song);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        ImageView img;


        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            img = v.findViewById(R.id.imageView);
        }


        void bind(final int position){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mListener.setSong(folders.get(position));
                }
            });
        }
    }



    @NonNull
    @Override
    public FavSongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_location_item, parent, false);

        return new FavSongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavSongAdapter.ViewHolder holder, int position) {
        MyFile folder = folders.get(position);
        holder.name.setText(folder.name);
        holder.img.setImageDrawable(ctx.getResources().getDrawable(R.drawable.folder));
        holder.bind(position);
    }






    @Override
    public int getItemCount() {
        return folders.size();
    }
}
