package com.example.lasa.basico.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lasa on 12-3-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Playlists.db";
    public static final String TABLE_NAME = "playlists";
    public static final String KEY_ID = "ID";
    public static final String PATH = "SONG_PATH";
    public static final String DATA = "data";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PATH + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //create
    public void create(ObSong obSong){
        SQLiteDatabase db = this.getWritableDatabase();

        String table = obSong.getTable();

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + table + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PATH + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    // add new obSong
    public void addSong(ObSong obSong){
        SQLiteDatabase db = this.getWritableDatabase();

        String table = obSong.getTable();

        ContentValues values = new ContentValues();
        values.put(PATH, obSong.getPath()); //path

        //insert Row
        db.insert(table, null, values);
        db.close(); //close db
    }

    //one obSong
    public Cursor getSong(ObSong obSong) {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = obSong.getTable();
        int id = obSong.getId();

        String query = "SELECT " + DATA +
                " FROM " + table +
                " WHERE " + KEY_ID + " = ?;";

        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(id) });

        // return shop
        return cursor;


    }

    public Cursor getData(ObSong som){
        List<ObSong> obSongList = new ArrayList<ObSong>();
        String table = som.getTable();
        int id = som.getId();
        //Select all query
        String selectQuery = "SELECT "+DATA+" FROM " + table + " WHERE " + KEY_ID + " = ?;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                ObSong obSong = new ObSong();
                obSong.setID(Integer.parseInt(cursor.getString(id)));
                obSong.setTable(table);
                // adding
                obSongList.add(obSong);
            }while (cursor.moveToNext());
        }
        //return list
        return cursor;
    }

    //all songs
    public Cursor getAllSongs(ObSong som){
        List<ObSong> obSongList = new ArrayList<ObSong>();
        String table = som.getTable();
        //Select all query
        String selectQuery = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                ObSong obSong = new ObSong();
                obSong.setID(Integer.parseInt(cursor.getString(0)));
                obSong.setPath(cursor.getString(1));
                obSong.setTable(table);
                // adding
                obSongList.add(obSong);
            }while (cursor.moveToNext());
        }
        //return list
        return cursor;
    }

    //get obSong count
    public int getSongCount(ObSong obSong){
        String table = obSong.getTable();
        String countQuery = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getInt(cursor.getCount());
    }

    //update obSong
    public int updateSong(ObSong obSong){
        String table = obSong.getTable();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATH, obSong.getPath());

        return db.update(table, values, KEY_ID + " = ?", new String[]{String.valueOf(obSong.getId())});
    }

    //delete obSong
    public void deleteSong(ObSong obSong){
        SQLiteDatabase db = this.getWritableDatabase();

        String table = obSong.getTable();

        db.delete(table, KEY_ID + " = ?", new String[]{ String.valueOf(obSong.getId())});
        db.close();
    }

    //delete all songs
    public void deleteAll(ObSong obSong) {
        String table = obSong.getTable();

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,null,null);
        db.execSQL("delete from "+ table);
        db.close();
    }

    public void deleteTable(ObSong obSong){
        String table = obSong.getTable();
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public Cursor getTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",
                null);

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                String table = cursor.getString(0);
                    Log.d("TABLES: : ", table);
                    cursor.moveToNext();

            }
        }

        return cursor;
    }

    public void addDataSingle(ObSong obSong){
        SQLiteDatabase db = this.getWritableDatabase();

        String table = obSong.getTable();
        String genre = obSong.getPath();

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + table + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DATA + " TEXT," +
                "UNIQUE("+DATA +")" +
                ");";
        db.execSQL(CREATE_TABLE);

        db.close(); //close db
    }

    public void addData(ObSong obSong){
        SQLiteDatabase db = this.getWritableDatabase();

        String table = obSong.getTable();
        String data = obSong.getPath();

        Log.d("ObSong: : ", data);

        ContentValues values = new ContentValues();
        values.put(DATA, data); //path

        db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);


        //String INSERT_DATA = "INSERT OR IGNORE INTO Album(data) VALUES("+data+")";

        //insert Row
        //db.execSQL(INSERT_DATA);
        db.close(); //close db
    }


}
