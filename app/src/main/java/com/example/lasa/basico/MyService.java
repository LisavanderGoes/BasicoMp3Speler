package com.example.lasa.basico;

import android.app.FragmentManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener,

        AudioManager.OnAudioFocusChangeListener  {

    public MediaPlayer mediaPlayer = new MediaPlayer();
    ObObject obObject = new ObObject();
    //path to the audio file
    private String mediaFile;
    //state of mp
    private String status;
    //Used to pause/resume MediaPlayer
    private int resumePosition;
    MainActivity mainActivity = new MainActivity();
    boolean isPlaying = false;
    boolean loopison = false;
    boolean ismute = false;

    Context context;
    String title;
    String artist;
    String album;
    String album_id;
    String sdata;
    private int currentSongIndex;
    ListView listView;

    ArrayList<ObWachtrij> wachtrij = obObject.wachtrij;



    //test
    private final Random mGenerator = new Random();



    // Binder given to clients
    private final IBinder iBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    //The system calls this method when an activity, requests the service be started
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //An audio file is passed to the service through putExtra();
            mediaFile = intent.getExtras().getString("media");
            Toast.makeText(getApplicationContext(), mediaFile, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            stopSelf();
        }


        if (mediaFile != null && mediaFile != "") {
            initMediaPlayer();
        }


        return START_STICKY;
    }

    /** test */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    @Override
    public void onAudioFocusChange(int i) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
        }
        //removeAudioFocus();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        /*MainActivity activity = MainActivity.instance;

        if (activity != null) {
            activity.next();
            //activity.startsonglayout();
        }*/

        /*nextSong(this);
        MainActivity activity = MainActivity.instance;

        if (activity != null) {
            activity.startsonglayout();
        }*/

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
    }

    private void initMediaPlayer() {

        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setLooping(false);

        try{
            mediaPlayer.setDataSource(mediaFile);
            mediaPlayer.prepare();
            playMedia();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        /*mediaPlayer = new MediaPlayer();
        //Set up MediaPlayer event listeners
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer.reset();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaFile location
            mediaPlayer.setDataSource(mediaFile);
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();*/

    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            //region [main activity play]
            MainActivity.play.setImageResource(R.drawable.pause);
            MainActivity.play.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    pauseMedia();
                }
            });
            //endregion
            isPlaying = true;
        }
    }

    public void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
            //region [main activity play]
            MainActivity.play.setImageResource(R.drawable.play);
        MainActivity.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resumeMedia();
            }
        });
        //endregion
            isPlaying = false;
    }
    }

    public void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
            //region [main activity play]
            MainActivity.play.setImageResource(R.drawable.pause);
            MainActivity.play.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    pauseMedia();
                }
            });
            //endregion
            isPlaying = true;
        }
    }

    public void loopingtrue(){
        mediaPlayer.setLooping(true);
        loopison = true;
    }

    public void loopingfalse(){
        mediaPlayer.setLooping(false);
        loopison = false;
    }

    public void mutefalse(){
        mediaPlayer.setVolume(1f, 1f);
        ismute = false;
    }

    public void mutetrue(){
        mediaPlayer.setVolume(0f, 0f);
        ismute = true;
    }

    public ArrayList<ObWachtrij> shuffle( ArrayList<ObWachtrij> curSongList, Context context, ListView list) {
        listView = list;
        wachtrij = curSongList;
        Collections.shuffle(wachtrij);
        ObWachtrij curItem = wachtrij.get(0);
        currentSongIndex = 0;
        Log.d("Reading: ", curItem.getTitle());
        ContentResolver contentResolver = context.getContentResolver();

        String media = curItem.getPath();
        Intent playerIntent = new Intent(context, MyService.class);
        playerIntent.putExtra("media", media);
        context.startService(playerIntent);

        title = curItem.getTitle();
        artist = curItem.getArtist();
        album = curItem.getAlbum();
        album_id = curItem.getId();

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        getArtistImage(album_id, context);

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
        return wachtrij;
    }

    public void send(int pos, ListView list, Context context, boolean obs){
        listView = list;
        currentSongIndex = pos;
        if(obs) {
            playAudio(pos, context);
        }else if (!obs) {
            playAudio2(pos, context);
        }
    }

    public void nextSong(Context context, boolean obs){
        int nextSongIndex = currentSongIndex + 1;
        currentSongIndex = nextSongIndex;
        if(obs) {
            playAudio(currentSongIndex, context);
        }else if (!obs){
            playAudio2(currentSongIndex, context);

        }
    }

    public void previousSong(Context context, boolean obs){
        int nextSongIndex = currentSongIndex - 1;
        currentSongIndex = nextSongIndex;
        if(obs) {
            playAudio(currentSongIndex, context);
        }else if (!obs){
            playAudio2(currentSongIndex, context);

        }
    }

    public void playAudio(int pos, Context context) {
        ObSonglist curItem = (ObSonglist) listView.getItemAtPosition(pos);
        String media = curItem.getPath();
        Toast.makeText(context.getApplicationContext(), media, Toast.LENGTH_LONG).show();

        ContentResolver contentResolver = context.getContentResolver();

        Intent playerIntent = new Intent(context, MyService.class);
        playerIntent.putExtra("media", media);
        context.startService(playerIntent);

        title = curItem.getTitle();
        artist = curItem.getArtist();
        album = curItem.getAlbum();
        album_id = curItem.getId();

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        getArtistImage(album_id, context);

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

        //region [stuff]
        /*int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        String media = cursor.getString(data);
        Toast.makeText(context.getApplicationContext(),media, Toast.LENGTH_LONG).show();

        ContentResolver contentResolver = context.getContentResolver();

        int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        title = cursor.getString(songTitle);
        artist = cursor.getString(songArtist);
        album = cursor.getString(songAlbum);
        sdata = cursor.getString(songData);

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        getArtistImage(album_id, context);

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

        //region [send to service]
        Intent playerIntent = new Intent(context, MyService.class);
        playerIntent.putExtra("media", media);
        context.startService(playerIntent);*/
        //endregion
    }

    public void playAudio2(int pos, Context context) {
        ObWachtrij curItem = (ObWachtrij) listView.getItemAtPosition(pos);
        String media = curItem.getPath();
        Toast.makeText(context.getApplicationContext(), media, Toast.LENGTH_LONG).show();

        ContentResolver contentResolver = context.getContentResolver();

        Intent playerIntent = new Intent(context, MyService.class);
        playerIntent.putExtra("media", media);
        context.startService(playerIntent);

        title = curItem.getTitle();
        artist = curItem.getArtist();
        album = curItem.getAlbum();
        album_id = curItem.getId();

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        getArtistImage(album_id, context);

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

        //region [stuff]
        /*int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        String media = cursor.getString(data);
        Toast.makeText(context.getApplicationContext(),media, Toast.LENGTH_LONG).show();

        ContentResolver contentResolver = context.getContentResolver();

        int songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

        title = cursor.getString(songTitle);
        artist = cursor.getString(songArtist);
        album = cursor.getString(songAlbum);
        sdata = cursor.getString(songData);

        MainActivity.titleView.setText(title);
        MainActivity.artistView.setText(artist);

        //region [get album art]
        album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        getArtistImage(album_id, context);

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

        //region [send to service]
        Intent playerIntent = new Intent(context, MyService.class);
        playerIntent.putExtra("media", media);
        context.startService(playerIntent);*/
        //endregion
    }

    private Bitmap getArtistImage(String albumid, Context context) {
        Bitmap artwork = null;
        ContentResolver contentResolver = context.getContentResolver();
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

    public void getcurrentposition(){
        int currentPosition = mediaPlayer.getCurrentPosition();
    }

    //region [stuff]
    //Als oortjes eruit
    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();
        }
    };

    private void registerBecomingNoisyReceiver() {
        //register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);
    }

    //endregion

    public String getTitle(){
        return title;
    }

}
