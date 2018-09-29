package com.maxapp.dansu.astraplayer.folder_browser_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;

import java.util.List;

public class DirectoryBrowserAdapter extends RecyclerView.Adapter<DirectoryBrowserAdapter.ViewHolder> {

    private List<MyDirectory> folders;
    private Context context;
    private ListAdapterListener mListener;
    private List<MyDirectory> checkedDirectories;

    public DirectoryBrowserAdapter(List<MyDirectory> folderList, List<MyDirectory> checkedLocations, Context ctx, ListAdapterListener mListener){
        this.folders = folderList;
        this.context = ctx;
        this.mListener = mListener;
        this.checkedDirectories = checkedLocations;
    }

    public interface ListAdapterListener { // create an interface
        void Add(MyDirectory dir);
        void Del(MyDirectory dir);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public CheckBox checkbox;
        public ImageView img;
        public String directory;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            checkbox = v.findViewById(R.id.checkBox);
            img = v.findViewById(R.id.imageView);

            v.setOnClickListener(this);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        checkedDirectories.add(new MyDirectory (name.getText().toString(), directory));
                        mListener.Add(new MyDirectory(name.getText().toString(), directory));
                    } else {
                        checkedDirectories.remove(new MyDirectory(name.getText().toString(), directory));
                        mListener.Del(new MyDirectory(name.getText().toString(), directory));
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directory_browser_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyDirectory folder = folders.get(position);
        holder.name.setText(folder.name);
        holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.folder));
        holder.directory = folders.get(position).directory;
        if(checkedDirectories.contains(folder))
            holder.checkbox.setChecked(true);






    }

    @Override
    public int getItemCount() {
        if(folders == null)
            return 0;
        return folders.size();
    }
}
