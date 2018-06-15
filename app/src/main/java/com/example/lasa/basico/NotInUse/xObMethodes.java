package com.example.lasa.basico.NotInUse;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Lasa on 11-3-2018.
 */

public class xObMethodes {

    @Deprecated
    Context context;
    String title;
    String artist;
    String album;
    String album_id;
    private int currentSongIndex;
    ListView listView;

    public xObMethodes(Context context){
        this.context=context;
    }



}
