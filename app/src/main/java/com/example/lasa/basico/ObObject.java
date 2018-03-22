package com.example.lasa.basico;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ObObject {
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Uri genreUri = android.provider.MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    final ArrayList<ObSonglist> songList = PlashScreen.songList;
    ArrayList<ObWachtrij> wachtrij = PlashScreen.wachtrij;

    public void setWachtrij(ArrayList<ObWachtrij> list){
        wachtrij = list;
    }

}


