package com.example.lasa.basico;

import android.*;
import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
    DatabaseHandler myDb;
    DatabaseHandler db = new DatabaseHandler(this);
    ObObject obObject = new ObObject();
    Uri myUri = obObject.songUri;
    static TextView titleView;
    static TextView artistView;
    ListView listview;
    Cursor cursor;
    static AlbumActivity instance;
    String table = "Album";
    MyService mService;
    boolean mBound = false;
    MyPlaylistAdapter mAdapter;
    int pos;
    int newpos;
    Cursor songCursor;
    ArrayList<ObPlaylist> list2 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        myDb = new DatabaseHandler(this);
        instance = this;
        listview = (ListView) findViewById(R.id.PlayListView);

        Bundle passedArg = getIntent().getExtras();
        if(passedArg != null) {
            pos = passedArg.getInt("data");
        }

        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = myUri;
        songCursor = contentResolver.query(songUri,
                null,
                null,
                null,
                null);


        ArrayList<ObPlaylist> list = new ArrayList<>();
        Log.d("Reading: ", "DATABASE!!!!!!!!!!!!!!11");
        Log.d("Reading: ", "Reading all tables..");
        Log.d("Reading: ", "DE GOEDE!!!!!!!!!!!!!");
        newpos = pos+1;
        Cursor cursor2 = db.getSong(new ObSong(newpos,"path", table));
        Log.d("TABLES: : ", new Integer(newpos).toString());

        if (cursor2.moveToFirst()){
            do{
                String t = cursor2.getString(0);
                Log.d("TABLES: : ", t);
                list.add(new ObPlaylist(t));
                //String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                //list.add(new ObPlaylist(album));
                getSongs(t);
            }
            while(cursor2.moveToNext());
        }cursor2.close();

        mAdapter = new MyPlaylistAdapter(this, list2);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });




    }

    public void getSongs(String album){
        if (songCursor.moveToFirst()) {
            Log.d("TABLES: : ", album);

            while(!songCursor.isAfterLast()){

                String a = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String s = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                Log.d("TABLES: : ", a);

                if(a == album) {
                    try {
                        list2.add(new ObPlaylist(s));
                        Log.d("TABLES: : ", s);
                    } catch (Exception e) {

                    }
                }

                songCursor.moveToNext();
            }
        }
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

}
