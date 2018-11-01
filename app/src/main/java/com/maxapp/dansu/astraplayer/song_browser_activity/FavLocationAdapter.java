package com.maxapp.dansu.astraplayer.song_browser_activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import java.util.List;


public class FavLocationAdapter extends RecyclerView.Adapter<FavLocationAdapter.ViewHolder> {

    private List<MyDirectory> folders;
    private Context ctx;

    FavLocationAdapter(List<MyDirectory> directories, Context ctx){
        folders = directories;
        this.ctx = ctx;
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
                    Intent intent = new Intent(ctx, FavSong.class);
                    intent.putExtra("folder", position);
                    ctx.startActivity(intent);
                }
            });
        }
    }



    @NonNull
    @Override
    public FavLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_location_item, parent, false);

        return new FavLocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDirectory folder = folders.get(position);
        holder.name.setText(folder.name);
        holder.img.setImageDrawable(ctx.getResources().getDrawable(R.drawable.folder));
        holder.bind(position);
    }






    @Override
    public int getItemCount() {
        return folders.size();
    }
}
