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

public class MySonglistAdapter extends ArrayAdapter {

    private Context mContext;
    private List<ObSonglist> songs = new ArrayList<>();

    public MySonglistAdapter(Context context, ArrayList<ObSonglist> list) {
        super(context, 0, list);
        mContext = context;
        songs = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);


        ObSonglist currentSong = songs.get(position);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        TextView artist = (TextView) listItem.findViewById(R.id.artist);
        title.setText(currentSong.getTitle());
        artist.setText(currentSong.getArtist());

        return listItem;
    }
}
