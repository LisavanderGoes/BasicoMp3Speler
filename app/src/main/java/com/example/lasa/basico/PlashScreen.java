package com.example.lasa.basico;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlashScreen extends AppCompatActivity {
    ObObject obObject = new ObObject();
    Uri myUri = obObject.songUri;
    Uri mygenreUri = obObject.genreUri;
    String tableAlbum = "Album";
    String tableGenre = "Genres";
    String tableArtist = "Artist";
    DatabaseHandler db = new DatabaseHandler(this);

    private static Cursor mediaCursor;
    private static Cursor genresCursor;
    Uri guri = mygenreUri;
    Uri songUri = myUri;
    MyService mService;
    boolean mBound = false;
    final static ArrayList<ObSonglist> songList = new ArrayList<>();
    final static ArrayList<ObWachtrij> wachtrij = new ArrayList<>();

    private static String[] mediaProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA
    };
    private static String[] genresProjection = {
            android.provider.MediaStore.Audio.Genres.NAME,
            MediaStore.Audio.Genres._ID
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentResolver contentResolver = getApplicationContext().getContentResolver();

        mediaCursor = contentResolver.query(songUri,
                mediaProjection,
                null,
                null,
                null);


        genresCursor = contentResolver.query(guri,
                genresProjection,
                null,
                null,
                null);

        if (mediaCursor.moveToFirst()) {

            createTable(tableAlbum);
            createTable(tableArtist);

            while(!mediaCursor.isAfterLast()){

                String album = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String title = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String path = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String album_ID = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                try {
                    insertData(tableAlbum, album);
                    insertData(tableArtist, artist);
                    songList.add(new ObSonglist(title, artist, path, album, album_ID));
                    wachtrij.add(new ObWachtrij(title, artist, path, album, album_ID));
                } catch (Exception e){

                }

                mediaCursor.moveToNext();
            }
        }


        if (genresCursor.moveToFirst()) {
            createTable(tableGenre);

            while(!genresCursor.isAfterLast()){

                String genre = genresCursor.getString(genresCursor.getColumnIndex(MediaStore.Audio.Genres.NAME));



                try {
                    insertData(tableGenre, genre);
                } catch (Exception e){

                }

                genresCursor.moveToNext();
            }
        }

        Intent myIntent = new Intent(getBaseContext(), MyService.class);
        startService(myIntent);

        startActivity(new Intent(this, SongLayoutActivity.class));


        startActivity(new Intent(this, MainActivity.class));
        getTables();



        finish();
    }

    //region [service connection]

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    //endregion

    public void insertData(String table, String album){
        /*Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Inserting...");*/
        db.addData(new ObSong(1, album, table));
    }

    public void createTable(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Creating...");
        db.addDataSingle(new ObSong(1,"path", table));
    }

    public void deleteSong(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Deleting song..");
        db.deleteSong(new ObSong(1, "//path/test2", table));
    }

    public void insertSong(String table, String path){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Inserting...");
        db.addSong(new ObSong(1, path, table));
    }

    public void songCount(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Getting song count..");
        int count = db.getSongCount(new ObSong(1,"path", table));

        Log.d("ObSong: : ", "" + count);
    } //werkt nie

    public void updateSong(String table, int id, String path){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Updating..");
        int count = db.updateSong(new ObSong(id,path, table));

        Log.d("ObSong: : ", "" + count);
    }

    public void deleteAll(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Deleting..");
        db.deleteAll(new ObSong(1,"path", table));

    }

    public void deleteTable(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Deleting..");
        db.deleteTable(new ObSong(1,"path", table));
    }

    public void getTables(){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        db.getTables();
    }
}
