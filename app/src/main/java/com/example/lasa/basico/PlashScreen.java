package com.example.lasa.basico;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by Lasa on 7-3-2018.
 */

public class PlashScreen extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent myIntent = new Intent(getBaseContext(), MyService.class);
        startService(myIntent);


        startActivity(new Intent(this, MainActivity.class));
        getTables();

        startActivity(new Intent(this, SongLayoutActivity.class));

        finish();
    }


    public void createTable(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Insert:", "Creating...");
        db.create(new ObSong(1,"path", table));
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

    public void readingSong(String table, int id){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all songs..");
        ObSong path = db.getSong(new ObSong(id, "//path/test1", table));

        Log.d("ObSong: : ", path.getPath());
    } //werkt nie

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
