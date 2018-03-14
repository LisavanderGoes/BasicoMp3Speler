package com.example.lasa.basico;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class SongLayoutActivity extends AppCompatActivity {
    ObObject obObject = new ObObject();
    DataBase db = new DataBase(this);

    //playlist
    //dropdown
      //details
    //shuffle
    //wachtrij
    //blacklist
    //favorite
    //doorspoelen met onClompletion en next/back en info
        //omdat het niet opniew start (SongLayoutActivity)
        //start nu opniew maar is niet zo netjes
        //als je niet in SongLayoutActivity zit geeft ie error
    //doet super raar bij sommige songs?? nu nie meer let op : doet het soms
        //hij selt er meer bij op bij de pos +1
        //miss bij oncompletion?? in service


    static ImageButton playBtn;
    ImageButton repeat;
    ImageButton muteBtn;
    SeekBar doorspoelen;
    TextView timecurrent;
    TextView timelast;
    MediaPlayer mp;
    int totalTime;
    ImageView albumpicmain;
    public TextView titlemain;
    TextView albummain;
    TextView artistmain;

    MyService mService;
    boolean mBound = false;
    public MediaPlayer mediaPlayer;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_layout);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        playBtn = (ImageButton) findViewById(R.id.playmainknop);
        timecurrent = (TextView) findViewById(R.id.timecurrent);
        timelast = (TextView) findViewById(R.id.timelast);
        repeat = (ImageButton) findViewById(R.id.repeatmainknop);
        albumpicmain = (ImageView) findViewById(R.id.albumpicmain);
        titlemain = (TextView) findViewById(R.id.titlemain);
        albummain = (TextView) findViewById(R.id.albummain);
        artistmain = (TextView) findViewById(R.id.artistmain);
        muteBtn = (ImageButton) findViewById(R.id.muteknop);

        //Toast.makeText(SongLayoutActivity.this, obObject.title, Toast.LENGTH_SHORT).show();

        Bundle passedArg = getIntent().getExtras();
        if(passedArg != null) {
            String title = passedArg.getString("title");
            String artist = passedArg.getString("artist");
            String album = passedArg.getString("album");
            String album_id = passedArg.getString("album_id");
            data= passedArg.getString("data");
            titlemain.setText(title);
            artistmain.setText(artist);
            albummain.setText(album);

            //region [get album art]
            Bitmap artwork = null;
            try {
                Uri sArtworkUri = Uri
                        .parse("content://media/external/audio/albumart");
                Uri uri = ContentUris.withAppendedId(sArtworkUri,
                        Long.valueOf(album_id));
                InputStream in = getContentResolver().openInputStream(uri);
                artwork = BitmapFactory.decodeStream(in);

                albumpicmain.setImageBitmap(artwork);
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
            //endregion

            albumpicmain.setImageBitmap(artwork);
        }


        //region [stuff dat niet werkt]
        /* werkt niet!!!!!!!!!

        try {
            MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
            metaRetriver.setDataSource(R.raw.music);
            try {
                byte[] art = metaRetriver.getEmbeddedPicture();
                Bitmap songImage = BitmapFactory
                        .decodeByteArray(art, 0, art.length);
                albumpicmain.setImageBitmap(songImage);
                albummain.setText(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                artistmain.setText(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                titlemain.setText(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            } catch (Exception e) {
                albumpicmain.setBackgroundColor(Color.GRAY);
                albummain.setText("");
                artistmain.setText("");
                titlemain.setText("");
            }
        }catch (Exception e){

        }*/
        //endregion

    }


    //region [service connection + button states + thread]

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

            //region [button states]
            if(mService.isPlaying){
                playBtn.setImageResource(R.drawable.pause);
            } else if(!mService.isPlaying){
                playBtn.setImageResource(R.drawable.play);
            }
            if(mService.loopison){
                repeat.setImageResource(R.drawable.repeat1);
            } else if(!mService.loopison){
                repeat.setImageResource(R.drawable.repeat);
            }
            //endregion

            mediaPlayer = mService.mediaPlayer;

            //region [thread]
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null) {
                        try {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                    }
                }
            }).start();

            totalTime = mediaPlayer.getDuration();
            doorspoelen();
            //endregion
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    //endregion

    //region [actionbar]
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_songlayout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_stuff:

                return true;

            case R.id.action_addplaylist:

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    //endregion

    //region [handler]

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            doorspoelen.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            timecurrent.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime);
            timelast.setText(remainingTime);
        }
    };

    //endregion

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public void playBtnClick (View view){

        if(mBound) {
            if (mService.isPlaying) {
                playBtn.setImageResource(R.drawable.play);
                mService.pauseMedia();
            } else if (!mService.isPlaying) {
                playBtn.setImageResource(R.drawable.pause);
                mService.resumeMedia();
            }else{
                Toast.makeText(SongLayoutActivity.this, "Geen liedje geselecteerd!", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void repeatBtnClick (View view){

        if(mBound) {
            if(!mService.loopison) {
                mService.loopingtrue();
                repeat.setImageResource(R.drawable.repeat1);
            }else if(mService.loopison){
                mService.loopingfalse();
                repeat.setImageResource(R.drawable.repeat);
            }
        }
    }

    public void backBtnClick (View view){
        mService.previousSong(this);
        MainActivity activity = MainActivity.instance;

        if (activity != null) {
            activity.startsonglayout();
        }
    }

    public void nextBtnClick (View view){
        mService.nextSong(this);
        MainActivity activity = MainActivity.instance;

        if (activity != null) {
            activity.startsonglayout();
        }
    }

    public void muteBtnClick (View view){
        if(mBound) {
            if (mService.ismute) {
                muteBtn.setImageResource(R.drawable.speaker);
                mService.mutefalse();
            } else if (!mService.ismute) {
                muteBtn.setImageResource(R.drawable.mute);
                mService.mutetrue();
            }
        }
    }

    public void shuffelBtnClick (View view){

    }

    public void favoriteBtnClick (View view){

    }

    public void playlistBtnClick (View view){
        String table = "table";
        db.insertSong(table, data);
    }

    public void wachtrijBtnClick (View view){

    }

    public void blacklistBtnClick (View view){

    }

    public void testes(String string){
        Toast.makeText(SongLayoutActivity.this, string, Toast.LENGTH_SHORT).show();

    }

    public void doorspoelen(){
        doorspoelen = (SeekBar) findViewById(R.id.doorspoelen);
        doorspoelen.setMax(totalTime);
        doorspoelen.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                        if(fromUser){
                            mService.mediaPlayer.seekTo(progress);
                            doorspoelen.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekbar){

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekbar){

                    }

                }
        );
    }

}
