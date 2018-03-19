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

        Cursor cursor = songCursor;
        ArrayList<String> test = new ArrayList<>();
        String table = "Album";

        //deleteTable(table);

        if (cursor.moveToFirst()) {

            createTable(table);

            while(!cursor.isAfterLast()){

                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                test.add(album);

                try {
                    insertData(table, album);
                } catch (Exception e){

                }

                cursor.moveToNext();
            }
        }

        ArrayList<ObPlaylist> list = new ArrayList<>();
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        Log.d("Reading: ", "DE GOEDE!!!!!!!!!!!!!");
        Cursor cursor2 = db.getAllSongs(new ObSong(1,"path", table));

        if (cursor2.moveToFirst()) {
            while ( !cursor2.isAfterLast() ) {
                String t = cursor2.getString(1);
                //Log.d("TABLES: : ", t);
                list.add(new ObPlaylist(t));
                cursor2.moveToNext();

            }
        }




        mAdapter = new MyPlaylistAdapter(applicationContext, list);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void createTable(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Creating...");
        db.addDataSingle(new ObSong(1,"path", table));
    }

    public void insertData(String table, String album){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Inserting...");
        db.addData(new ObSong(1, album, table));
    }

    public void deleteTable(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Deleting..");
        db.deleteTable(new ObSong(1,"path", table));
    }

}


