package com.example.lasa.basico;


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
import android.app.Fragment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;


public class infoAllSongsFragment extends Fragment {
    ObObject obObject = new ObObject();
    Context applicationContext = MainActivity.getContextOfApplication();
    xObMethodes methode = new xObMethodes(applicationContext);
    ListView listview;
    ArrayList<String> arrayList = obObject.arrayList;
    TextView titleView;
    TextView artistView;
    int i;
    Uri myUri = obObject.songUri;
    private MyService player;
    boolean serviceBound = false;
    private xObItem[] listData;
    MyCursorAdapter cursorAdapter;

    //region [play music]
    String title;
    String artist;
    String album;
    String album_id;
    private int currentSongIndex = 0;
    private int nextSongIndex = 0;
    //endregion


    public infoAllSongsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_all_songs, container, false);
        listview = (ListView) view.findViewById(R.id.ListView);
        titleView = (TextView) view.findViewById(R.id.titlemain);
        artistView = (TextView) view.findViewById(R.id.artistmain);


    Context applicationContext = MainActivity.getContextOfApplication();
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
                0);
        listview.setAdapter(cursorAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context applicationContext = MainActivity.getContextOfApplication();
                //Toast.makeText(applicationContext.getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).send(position, listview);
                //methode.send(position, listview);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //region [play music] //probeer dit miss in service te zetten
    public void send(int pos){
        currentSongIndex = pos;
        init(currentSongIndex);
    }

    public void nextSong(){
        nextSongIndex = currentSongIndex + 1;
        currentSongIndex = nextSongIndex;
        init(currentSongIndex);

    }

    public void previousSong(){
        nextSongIndex = currentSongIndex - 1;
        currentSongIndex = nextSongIndex;
        init(currentSongIndex);

    }

    public void init(int pos){
        //playAudio(pos);
    }

    private void playAudio(int pos) {
        Cursor cursor = (Cursor) listview.getItemAtPosition(pos);
        int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        String media = cursor.getString(data);
        ContentResolver contentResolver = applicationContext.getContentResolver();

        int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

        title = cursor.getString(songTitle);
        artist = cursor.getString(songArtist);
        album = cursor.getString(songAlbum);

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        getArtistImage(album_id);

        Bitmap artwork = null;
        try {
            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri,
                    Long.valueOf(album_id));
            InputStream in = contentResolver.openInputStream(uri);
            artwork = BitmapFactory.decodeStream(in);

            MainActivity.albumPicMain.setImageBitmap(artwork);
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        //endregion

        //region [send to service] //hier wordt service gestart
        Intent playerIntent = new Intent(getActivity(), MyService.class);
        playerIntent.putExtra("media", media);
        getActivity().startService(playerIntent);
        //endregion
    }

    private Bitmap getArtistImage(String albumid) {
        Bitmap artwork = null;
        ContentResolver contentResolver = applicationContext.getContentResolver();
        try {
            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri,
                    Long.valueOf(albumid));
            InputStream in = contentResolver.openInputStream(uri);
            artwork = BitmapFactory.decodeStream(in);

            MainActivity.albumPicMain.setImageBitmap(artwork);
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return artwork;
    }
    //endregion

    //region [stuff]
    private void addData(){
        xObItem data = null;
        listData = new xObItem[30];
        for(int i = 0; i < 30; i ++){
            data = new xObItem();
            data.titleMain = "title:" + i;
            data.artistMain = "artist" + i;
            listData[i] = data;
        }

        /*if (!serviceBound) {
            Intent playerIntent = new Intent(getActivity(), MyService.class);
            playerIntent.putExtra("media", media);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send to service
        }*/

        // region [old listview prut]
        /*arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(adapter);*/

        /*addData();
        xItemAdapter itemAdapter = new xItemAdapter(getContext(), R.layout.item, listData);
        listview.setAdapter(itemAdapter);*/
        //endregion
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

            Toast.makeText(applicationContext.getApplicationContext(), "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            Toast.makeText(applicationContext.getApplicationContext(), "Service NOT Bound", Toast.LENGTH_SHORT).show();

        }
    };
    public void getMusic() {
        Context applicationContext = MainActivity.getContextOfApplication();

        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri songUri = myUri;


        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentData = songCursor.getString(songData);
                arrayList.add(i, currentData);
                i++;
            } while (songCursor.moveToNext());
        }
        songCursor.close();
    }
    //endregion

}
