package com.example.lasa.basico;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class infoAlbumFragment extends Fragment {
    Context applicationContext = MainActivity.getContextOfApplication();
    DatabaseHandler db = new DatabaseHandler(applicationContext);
    ObObject obObject = new ObObject();
    Uri myUri = obObject.songUri;
    ListView listview;
    TextView titleView;
    TextView artistView;

    MyPlaylistAdapter mAdapter;


    public infoAlbumFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_playlist, container, false);
        listview = (ListView) view.findViewById(R.id.PlayListView);

        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri songUri = myUri;
        Cursor songCursor = contentResolver.query(songUri,
                null,
                null,
                null,
                null);

        ArrayList<ObPlaylist> list = new ArrayList<ObPlaylist>();
        ArrayList<ObPlaylist> newGenre = new ArrayList<ObPlaylist>();
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        Log.d("Reading: ", "DE GOEDE!!!!!!!!!!!!!");
        Cursor cursor = songCursor;

        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast()){

                String table = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                list.add(new ObPlaylist(table));

                for (int g = 0; g < newGenre.size(); g++) {
                    String gen = Integer.toString(g);
                    if (gen == table)
                        newGenre.add(new ObPlaylist(gen));
                }


            /*//Set variable

            //get all song.genres
            for (int i = 0; !cursor.isAfterLast(); i++) {
                String table = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                oldGenre.add(table);

                //foreach
                for (int g = 0; g < newGenre.size(); g++) {
                    if (g != i)
                        //String t = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        Log.d("Reading: ", Integer.toString(g));*/

                cursor.moveToNext();
            }
        }




        mAdapter = new MyPlaylistAdapter(applicationContext, newGenre);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}


