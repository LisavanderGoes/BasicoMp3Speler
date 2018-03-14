package com.example.lasa.basico;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class xSimpleRawMusicPlayer extends AppCompatActivity {

    /*//ObObject obObject = new ObObject();

    //playlist
    //dropdown
      //details
    //back
    //next
    //mute
    //shuffle
    //wachtrij
    //blacklist
    //favorite

    static ImageButton playBtn;
    ImageButton repeat;
    SeekBar doorspoelen;
    TextView timecurrent;
    TextView timelast;
    MediaPlayer mp;
    int totalTime;
    boolean loopison = false;
    ImageView albumpicmain;
    TextView titlemain;
    TextView albummain;
    TextView artistmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        playBtn = (ImageButton) findViewById(R.id.playmainknop);
        timecurrent = (TextView) findViewById(R.id.timecurrent);
        timelast = (TextView) findViewById(R.id.timelast);
        repeat = (ImageButton) findViewById(R.id.repeatmainknop);
        albumpicmain = (ImageView) findViewById(R.id.albumpicmain);
        titlemain = (TextView) findViewById(R.id.titlemain);
        albummain = (TextView) findViewById(R.id.albummain);
        artistmain = (TextView) findViewById(R.id.artistmain);

        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(false); //repeat knop
        mp.seekTo(0); //doorspoelen bar (begin)
        mp.setVolume(0.5f, 0.5f);

        totalTime = mp.getDuration();

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

        //doorspoelen
    /*
        doorspoelen = (SeekBar) findViewById(R.id.doorspoelen);
        doorspoelen.setMax(totalTime);
        doorspoelen.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                        if(fromUser){
                            mp.seekTo(progress);
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

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

            }
        });


// Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();


    }

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

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    } //werkt nog niet

    public void playBtnClick (View view){

        if (!mp.isPlaying()){
            mp.start();
            playBtn.setImageResource(R.drawable.pause);
        }else{
            mp.pause();
            playBtn.setImageResource(R.drawable.play);
        }

    }

    public void repeatBtnClick (View view){

        if(!loopison) {
            loopison = true;
            mp.setLooping(true);
            repeat.setImageResource(R.drawable.repeat1);
        }else{
            loopison = false;
            mp.setLooping(false);
            repeat.setImageResource(R.drawable.repeat);
        }

    }*/

}
