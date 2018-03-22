package com.example.lasa.basico;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    DatabaseHandler myDb;
    ObObject obObject = new ObObject();
    static ImageButton play;
    static ImageButton next;
    static TextView titleView;
    static TextView artistView;
    static ImageView albumPicMain;
    Cursor cursor;
    static MainActivity instance;
    boolean state = false;

    String title;
    String artist;
    String album;
    String album_id;
    String data;
    boolean obs;

    MyService mService;
    boolean mBound = false;

    private MyFragmentAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHandler(this);
        instance = this;

        play = (ImageButton) findViewById(R.id.playmainknop);
        next = (ImageButton) findViewById(R.id.nextmainknop);
        titleView = (TextView) findViewById(R.id.titlemain);
        artistView = (TextView) findViewById(R.id.artistmain);
        albumPicMain = (ImageView) findViewById(R.id.albumpicmain);
        contextOfApplication = getApplicationContext();

        isReadStoragePermissionGranted();


        mSectionsPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.info);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    //region [actionbar]
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;

            case R.id.action_zoeken:

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    //endregion

    public void startsonglayout(){
        Intent intent = new Intent(getApplicationContext(), SongLayoutActivity.class);
        if(state) {
            title = mService.title;
            artist = mService.artist;
            album = mService.album;
            album_id = mService.album_id;
            data = mService.sdata;
            intent.putExtra("title", title);
            intent.putExtra("artist", artist);
            intent.putExtra("album", album);
            intent.putExtra("album_id", album_id);
            intent.putExtra("data", data);
            intent.putExtra("obs", obs);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void startalbumactivity(int data){
        Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
            int d = data;
            intent.putExtra("data", d);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void startgenreactivity(int data){
        Intent intent = new Intent(getApplicationContext(), GenreActivity.class);
        int d = data;
        intent.putExtra("data", d);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public ArrayList<ObWachtrij> shuffle(ArrayList<ObWachtrij> wachtrij, ListView list){
        obs = false;
        return mService.shuffle(wachtrij, this, list);
    }

    public void send(int pos, ListView list, ArrayList<ObWachtrij> curSongList, boolean ob){
        obs = ob;
        ListView listView = list;
        int currentSongIndex = pos;
        mService.send(currentSongIndex, listView, this, ob);
        state = true;
        obObject.setWachtrij(curSongList);
    }

    public void playBtnClick (View view){
        Toast.makeText(MainActivity.this, "Geen liedje geselecteerd!", Toast.LENGTH_SHORT).show();
    }

    public void songLayoutBtnClick (View view){
        startsonglayout();
    }

    public void nextBtnClick (View view){
            mService.nextSong(this, obs);

    }

    public void backBtnClick (View view){

            mService.previousSong(this, obs);

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

    // region [Stuff for Fragments]
    // a static variable to get a reference of our application context

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    //endregion

    //region [Toestemming]

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) { //checks if permission is already given
                Toast.makeText(this, "Permission 1!", Toast.LENGTH_SHORT).show();
                //Permission is given
                //Do stuff
                //doStuff();

                return true;
            } else {
                //calls on request permission
                Toast.makeText(this, "Permission not 2!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission 3!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Toast.makeText(this, "External storage2", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0]+" ", Toast.LENGTH_SHORT).show();
                    //test();
                }else{
                    // test();
                }
                break;

            case 3:
                Toast.makeText(this, "External storage1", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0]+" ", Toast.LENGTH_SHORT).show();
                    // test();
                }else{
                    // test();
                }
                break;
        }
    }

    //endregion



}

