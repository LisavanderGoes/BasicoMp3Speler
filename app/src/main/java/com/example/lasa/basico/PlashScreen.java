package com.example.lasa.basico;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    private static String[] mediaProjection = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE
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

                try {
                    insertData(tableAlbum, album);
                    insertData(tableArtist, artist);
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


        startActivity(new Intent(this, MainActivity.class));
        getTables();

        startActivity(new Intent(this, SongLayoutActivity.class));

        finish();
    }

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
