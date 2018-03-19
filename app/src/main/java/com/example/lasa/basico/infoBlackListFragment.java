package com.example.lasa.basico;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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


public class infoBlackListFragment extends Fragment {
    Context applicationContext = MainActivity.getContextOfApplication();
    DatabaseHandler db = new DatabaseHandler(applicationContext);

    ListView listview;
    TextView titleView;
    TextView artistView;

    MyPlaylistAdapter mAdapter;


    public infoBlackListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_playlist, container, false);
        listview = (ListView) view.findViewById(R.id.PlayListView);

        String table = "Blacklist";

        createTable(table);


        ArrayList<ObPlaylist> list = new ArrayList<>();
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        Log.d("Reading: ", "DE GOEDE!!!!!!!!!!!!!");
        Cursor cursor = db.getAllSongs(new ObSong(1,"path", table));

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                String t = cursor.getString(1);
                Log.d("TABLES: : ", t);
                list.add(new ObPlaylist(t));
                cursor.moveToNext();

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
        db.create(new ObSong(1,"path", table));
    }
}


