package com.example.lasa.basico;

import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class infoAllSongsFragment extends Fragment {
    ObObject obObject = new ObObject();
    Context applicationContext = MainActivity.getContextOfApplication();
    ListView listview;
    TextView titleView;
    TextView artistView;
    Button shuffleBtn;
    MySonglistAdapter mAdapter;
    ArrayList<ObSonglist> songList = obObject.songList;



    //region [play music]
    String title;
    String artist;
    String album;
    //endregion


    public infoAllSongsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_all_songs, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        titleView = (TextView) view.findViewById(R.id.titlemain);
        artistView = (TextView) view.findViewById(R.id.artistmain);
        shuffleBtn = (Button) view.findViewById(R.id.shuffleBtn);

    /*Context applicationContext = MainActivity.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri songUri = myUri;
        Cursor songCursor = contentResolver.query(songUri,
                null,
                null,
                null,
                null);


        cursorAdapter = new MyCursorAdapter(
                getActivity(),
                songCursor,
                0);*/
        //listview.setAdapter(cursorAdapter);

        mAdapter = new MySonglistAdapter(applicationContext, songList);
        listview.setAdapter(mAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context applicationContext = MainActivity.getContextOfApplication();
                //Toast.makeText(applicationContext.getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                //wachtrij = songList;
                ((MainActivity)getActivity()).send(position, listview, PlashScreen.wachtrij, true);


            }
        });

        /*shuffleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //wachtrij = songList;
                ((MainActivity)getActivity()).shuffle(songList);
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }

}
