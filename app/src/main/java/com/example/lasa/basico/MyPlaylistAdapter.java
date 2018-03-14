package com.example.lasa.basico;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lasa on 7-3-2018.
 */

public class MyPlaylistAdapter extends ArrayAdapter {

    private Context mContext;
    private List<ObPlaylist> playlists = new ArrayList<>();

    public MyPlaylistAdapter(Context context, ArrayList<ObPlaylist> list) {
        super(context, 0, list);
        mContext = context;
        playlists = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.playlist_item,parent,false);


        ObPlaylist currentMovie = playlists.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.titlePlaylist);
        name.setText(currentMovie.getTable());

        return listItem;
    }
}
