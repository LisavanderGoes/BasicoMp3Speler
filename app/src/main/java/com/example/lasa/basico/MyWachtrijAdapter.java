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

public class MyWachtrijAdapter extends ArrayAdapter {

    private Context mContext;
    private List<ObWachtrij> songs = new ArrayList<>();

    public MyWachtrijAdapter(Context context, ArrayList<ObWachtrij> list) {
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


        ObWachtrij currentSong = songs.get(position);

        TextView title = (TextView) listItem.findViewById(R.id.title);
        TextView artist = (TextView) listItem.findViewById(R.id.artist);
        title.setText(currentSong.getTitle());
        artist.setText(currentSong.getArtist());

        return listItem;
    }
}
