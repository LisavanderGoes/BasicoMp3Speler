package com.example.lasa.basico;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class infoPlaylistFragment extends Fragment {
    Context applicationContext = MainActivity.getContextOfApplication();
    DatabaseHandler db = new DatabaseHandler(applicationContext);
    ObPlaylist obPlaylist = new ObPlaylist();

    ListView listview;
    TextView titleView;
    TextView artistView;

    MyPlaylistAdapter mAdapter;


    public infoPlaylistFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_playlist, container, false);
        listview = (ListView) view.findViewById(R.id.PlayListView);

        /*ArrayList<ObPlaylist> list = new ArrayList<>();
        ArrayList<ObPlaylist> list2 = new ArrayList<>();
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        Log.d("Reading: ", "DE GOEDE!!!!!!!!!!!!!");
        Cursor cursor = db.getTables();

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                String table = cursor.getString(0);
                Log.d("TABLES: : ", table);
                    list.add(new ObPlaylist(table));
                    cursor.moveToNext();
            }
        }

        mAdapter = new MyPlaylistAdapter(applicationContext, list);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }
}


