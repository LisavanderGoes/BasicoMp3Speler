package com.example.lasa.basico;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class DataBase extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);


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

    public void readingSongs(String table){
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all songs..");
        //List<ObSong> songs2 = db.getAllSongs(new ObSong(1,"path", table));

        /*for (ObSong obSong : songs2) {
            String log = "Id: " + obSong.getId() + " ,Name: " + obSong.getPath() + " ,Table: " + obSong.getTable();
            // Writing shops to log
            Log.d("ObSong: : ", log);
        }*/
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
        //Cursor cursor = db.getTables();


    }

}
